package mdrive.business.dao;

import mdrive.business.model.GeoObjectBean;
import mdrive.business.model.GeoObjectBean_;
import mdrive.business.model.GeoObjectTypeBean_;
import mdrive.business.model.I18NameBean;
import mdrive.business.model.nullobject.NullGeoObjectBean;
import mdrive.business.type.Constants;
import mdrive.business.type.GeoObjectTypeCode;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.hibernate.Hibernate;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 * User: Elena
 * Date: 11.01.2011
 * Time: 8:25:52
 * To change this template use File | Settings | File Templates.
 */
@Transactional
@Component
public class GeoObjectDao extends GenericDao<GeoObjectBean> {

    public List<GeoObjectBean> getStreetGeoObjectsStartingWith(String prefix, int rowLimit) throws DataAccessException {
        return getStreetGeoObjectsStartingWith(prefix, Constants.DEFAULT_LOCALE, rowLimit);
    }


    public List<GeoObjectBean> getStreetGeoObjectsStartingWith(String prefix, Locale locale, int rowLimit)
            throws DataAccessException {
        return getGeoObjectsByTypeAndParentAndPrefix(GeoObjectTypeCode.STREET, null, prefix, locale, rowLimit);
    }


    public List<GeoObjectBean> getBuildingGeoObjectsStartingWith(String prefix, Locale locale, Long parentId,
                                                                 int rowLimit) throws DataAccessException {
        return getGeoObjectsByTypeAndParentAndPrefix(GeoObjectTypeCode.BUILDING, parentId, prefix, locale, rowLimit);
    }

    public List<GeoObjectBean> getGeoObjectsByTypeAndParentAndPrefix(GeoObjectTypeCode typeCode, Long parentId,
                                                                     String prefix, Locale locale,
                                                                     int rowLimit) throws DataAccessException {
        if (typeCode == null || prefix == null || locale == null) {
            throw new RuntimeException("typeCode, prefix or locale is null: " + typeCode + prefix + locale);
        }
        final String language = WordUtils.capitalize(locale.getLanguage());

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<GeoObjectBean> cq = cb.createQuery(GeoObjectBean.class);
        Root<GeoObjectBean> root = cq.from(GeoObjectBean.class);

        //predicates
        Predicate typeCodePredicate = cb.equal(root.get(GeoObjectBean_.geoObjectTypeBean).get(GeoObjectTypeBean_.typeCode), typeCode);

        Join<GeoObjectBean, I18NameBean> join = root.join(GeoObjectBean_.name);
        Predicate prefixPredicate = cb.like(
                cb.upper(join.<String>get("value" + language)),
                StringUtils.upperCase(prefix + "%")
        );

        if (parentId != null) {
            Predicate parentIdPredicate = cb.equal(root.get(GeoObjectBean_.parentGeoObjectBean).get(GeoObjectBean_.id), parentId);
            cq.where(typeCodePredicate, prefixPredicate, parentIdPredicate);
        } else {
            cq.where(typeCodePredicate, prefixPredicate);
        }

        TypedQuery<GeoObjectBean> query = entityManager.createQuery(cq);
        query.setMaxResults(rowLimit);
        return query.getResultList();
    }


    public List<GeoObjectBean> getBuildingsByStreetId(Long streetId) {
        return entityManager.createNamedQuery(GeoObjectBean.GET_BUILDINGS_BY_STREET_ID).setParameter("streetId", streetId).getResultList();
    }


    public Long getTotalUnresolvedBuildingsLeft() {
        return (Long) entityManager.createNamedQuery(GeoObjectBean.GET_TOTAL_UNRESOLVED_BUILDINGS_LEFT).getSingleResult();
    }

    @Transactional(readOnly = true)
    public GeoObjectBean getFullGeoObjectBeanById(Long id) throws DataAccessException {
        if (id == null) {
            return NullGeoObjectBean.instance;
        }
        GeoObjectBean geoObjectBean = findOne(id);
        if (geoObjectBean != null) {
            Hibernate.initialize(geoObjectBean.getName());
            Hibernate.initialize(geoObjectBean.getParentGeoObjectBean());
            Hibernate.initialize(geoObjectBean.getCoordinatesBean());
            Hibernate.initialize(geoObjectBean.getGeoObjectTypeBean());
        }
        return geoObjectBean;
    }


    //TODO: 3 convet Radius from Grad to Km
    @Transactional(readOnly = true)
    public GeoObjectBean getOneGeoObjectsByLocationAndRadius(Float latitude, Float longitude, Float radius,
                                                             GeoObjectTypeCode typeCode) throws DataAccessException {
        List<GeoObjectBean> geoObjectsByLocationAndRadius = getGeoObjectsByLocationAndRadius(latitude, longitude, radius, typeCode, 1);
        if (geoObjectsByLocationAndRadius == null || geoObjectsByLocationAndRadius.size() == 0) {
            return NullGeoObjectBean.instance;
        } else {
            return geoObjectsByLocationAndRadius.iterator().next();
        }
    }

    @Transactional(readOnly = true)
    public List<GeoObjectBean> getGeoObjectsByLocationAndRadius(Float latitude, Float longitude, Float radius,
                                                                GeoObjectTypeCode typeCode, int maxResults) throws DataAccessException {
        final Float latitudeMin = latitude - radius;
        final Float latitudeMax = latitude + radius;
        final Float longitudeMin = longitude - radius;
        final Float longitudeMax = longitude + radius;

        String hql =
                "select cor.geoObjectBean from CoordinatesBean cor where cor.center.latitude between :latitudeMin and :latitudeMax "
                        + " and cor.center.longitude between :longitudeMin and :longitudeMax and cor.geoObjectBean.geoObjectTypeBean.typeCode = :typeCode";
        return entityManager.createQuery(hql)
                .setParameter("latitudeMin", latitudeMin)
                .setParameter("latitudeMax", latitudeMax)
                .setParameter("longitudeMin", longitudeMin)
                .setParameter("longitudeMax", longitudeMax)
                .setParameter("typeCode", typeCode)
                .setMaxResults(maxResults)
                .getResultList();
    }
}
