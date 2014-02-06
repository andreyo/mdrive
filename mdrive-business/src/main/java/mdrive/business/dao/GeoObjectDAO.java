package mdrive.business.dao;

import mdrive.business.model.GeoObjectBean;
import mdrive.business.model.nullobject.NullGeoObjectBean;
import mdrive.business.type.Constants;
import mdrive.business.type.GeoObjectTypeCode;
import org.apache.commons.lang.WordUtils;
import org.hibernate.Hibernate;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.Collections;
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


    public List<GeoObjectBean> getStreetGeoObjectsStartingWith(String prefix, Locale locale,
                                                               int rowLimit) throws DataAccessException {
        return getGeoObjectsByTypeAndParentAndPrefix(GeoObjectTypeCode.STREET, null, prefix, locale, rowLimit);
    }


    public List<GeoObjectBean> getBuildingGeoObjectsStartingWith(String prefix, Locale locale, Long parentId,
                                                                 int rowLimit) throws DataAccessException {
        if (parentId == null) {
            return Collections.emptyList();
        }
        return getGeoObjectsByTypeAndParentAndPrefix(GeoObjectTypeCode.BUILDING, parentId, prefix, locale, rowLimit);
    }

    //TODO: create NamedQueries
    public List<GeoObjectBean> getGeoObjectsByTypeAndParentAndPrefix(GeoObjectTypeCode typeCode, Long parentId,
                                                                     String prefix, Locale locale,
                                                                     int rowLimit) throws DataAccessException {
        if (typeCode == null || prefix == null || locale == null) {
            throw new RuntimeException("typeCode, prefix or locale is null: " + typeCode + prefix + locale);
        }
        //TODO: reuse single hql
        final String language = WordUtils.capitalize(locale.getLanguage());
        String hql_type_prefix =
                "from GeoObjectBean go join fetch go.objectI18Name objectName where go.geoObjectTypeBean.typeCode = :typeCode and upper(objectName.value"
                        + language + ") like upper(:streetPrefix)";
        String hql_type_parent_prefix =
                "from GeoObjectBean go join fetch go.objectI18Name objectName where go.geoObjectTypeBean.typeCode = :typeCode and go.parentGeoObjectBean.id = :parentId and upper(objectName.value"
                        + language + ") like upper(:streetPrefix)";
        Query query;
        if (parentId == null) {
            query = entityManager.createQuery(hql_type_prefix)
                    .setParameter("typeCode", typeCode)
                    .setParameter("streetPrefix", prefix + "%");
        } else {
            query = entityManager.createQuery(hql_type_parent_prefix)
                    .setParameter("typeCode", typeCode)
                    .setParameter("parentId", parentId)
                    .setParameter("streetPrefix", prefix + "%");
        }
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
            Hibernate.initialize(geoObjectBean.getObjectI18Name());
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
