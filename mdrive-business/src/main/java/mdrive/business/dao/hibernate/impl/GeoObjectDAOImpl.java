package mdrive.business.dao.hibernate.impl;

import mdrive.business.bean.GeoObjectBean;
import mdrive.business.bean.nullobject.NullGeoObjectBean;
import mdrive.business.dao.hibernate.GeoObjectDAO;
import mdrive.business.type.Constants;
import mdrive.business.type.GeoObjectTypeCode;
import org.apache.commons.lang.WordUtils;
import org.hibernate.Hibernate;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
public class GeoObjectDAOImpl extends GenericDAOHibernateImpl<GeoObjectBean, Long> implements GeoObjectDAO {

    @Override
    public List<GeoObjectBean> getStreetGeoObjectsStartingWith(String prefix, int rowLimit) throws DataAccessException {
        return getStreetGeoObjectsStartingWith(prefix, Constants.DEFAULT_LOCALE, rowLimit);
    }

    @Override
    public List<GeoObjectBean> getStreetGeoObjectsStartingWith(String prefix, Locale locale,
                                                               int rowLimit) throws DataAccessException {
        return getGeoObjectsByTypeAndParentAndPrefix(GeoObjectTypeCode.STREET, null, prefix, locale, rowLimit);
    }

    @Override
    public List<GeoObjectBean> getBuildingGeoObjectsStartingWith(String prefix, Locale locale, Long parentId,
                                                                 int rowLimit) throws DataAccessException {
        if (parentId == null) {
            return Collections.emptyList();
        }
        return getGeoObjectsByTypeAndParentAndPrefix(GeoObjectTypeCode.BUILDING, parentId, prefix, locale, rowLimit);
    }

    @Override
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
        getHibernateTemplate().setMaxResults(rowLimit);
        if (parentId == null) {
            return this.getHibernateTemplate().findByNamedParam(hql_type_prefix,
                    new String[]{"typeCode", "streetPrefix"},
                    new Object[]{typeCode, prefix + "%"});
        } else {
            return this.getHibernateTemplate().findByNamedParam(hql_type_parent_prefix,
                    new String[]{"typeCode", "parentId", "streetPrefix"},
                    new Object[]{typeCode, parentId, prefix + "%"});
        }
    }

    @Override
    public List<GeoObjectBean> getBuildingsByStreetId(Long streetId) {
        return getHibernateTemplate().findByNamedQueryAndNamedParam("getBuildingsByStreetId", "streetId", streetId);
    }

    @Override
    public Long getTotalUnresolvedBuildingsLeft() {
        List resultList = getHibernateTemplate().findByNamedQuery("getTotalUnresolvedBuildingsLeft");
        return (Long) resultList.get(0);
    }

    @Transactional(readOnly = true)
    @Override
    public GeoObjectBean getFullGeoObjectBeanById(Long id) throws DataAccessException {
        GeoObjectBean geoObjectBean = findById(id);
        if (geoObjectBean != null) {
            Hibernate.initialize(geoObjectBean.getObjectI18Name());
            Hibernate.initialize(geoObjectBean.getParentGeoObjectBean());
            Hibernate.initialize(geoObjectBean.getCoordinatesBean());
            Hibernate.initialize(geoObjectBean.getGeoObjectTypeBean());
        }
        return geoObjectBean;
    }

    @Transactional(readOnly = true)
    @Override
    public List<GeoObjectBean> getGeoObjectsByLocation(Float latitude, Float longitude, Float radius,
                                                       GeoObjectTypeCode typeCode) throws DataAccessException {
        final Float latitudeMin = latitude - radius;
        final Float latitudeMax = latitude + radius;
        final Float longitudeMin = longitude - radius;
        final Float longitudeMax = longitude + radius;

        String hql =
                "select cor.geoObjectBean from CoordinatesBean cor where cor.center.latitude between :latitudeMin and :latitudeMax "
                        + " and cor.center.longitude between :longitudeMin and :longitudeMax and cor.geoObjectBean.geoObjectTypeBean.typeCode = :typeCode";
        return this.getHibernateTemplate().findByNamedParam(hql,
                new String[]{"latitudeMin", "latitudeMax", "longitudeMin", "longitudeMax", "typeCode"},
                new Object[]{latitudeMin, latitudeMax, longitudeMin, longitudeMax, typeCode});
    }

    @Override
    public GeoObjectBean getNullObject() {
        return NullGeoObjectBean.instance;
    }
}