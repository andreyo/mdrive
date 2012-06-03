package mdrive.business.dao.hibernate;

import mdrive.business.bean.GeoObjectBean;
import mdrive.business.type.GeoObjectTypeCode;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 * User: Elena
 * Date: 11.01.2011
 * Time: 8:23:42
 * To change this template use File | Settings | File Templates.
 */
public interface GeoObjectDAO extends GenericDAO<GeoObjectBean, Long> {

    List<GeoObjectBean> getStreetGeoObjectsStartingWith(String prefix, int rowLimit) throws DataAccessException;

    List<GeoObjectBean> getStreetGeoObjectsStartingWith(String prefix, Locale locale,
                                                        int rowLimit) throws DataAccessException;

    List<GeoObjectBean> getBuildingGeoObjectsStartingWith(String prefix, Locale locale, Long parentId,
                                                          int rowLimit) throws DataAccessException;

    List<GeoObjectBean> getGeoObjectsByTypeAndParentAndPrefix(GeoObjectTypeCode typeCode, Long parentId,
                                                                     String prefix, Locale locale, int rowLimit) throws DataAccessException;

    List<GeoObjectBean> getBuildingsByStreetId(Long streetId);

    Long getTotalUnresolvedBuildingsLeft();

    GeoObjectBean getFullGeoObjectBeanById(Long id) throws DataAccessException;

    List<GeoObjectBean> getGeoObjectsByLocation(Float latitude, Float longtitude, Float radius, GeoObjectTypeCode typeCode) throws DataAccessException;
}