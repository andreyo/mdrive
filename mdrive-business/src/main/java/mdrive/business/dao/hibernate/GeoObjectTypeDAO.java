package mdrive.business.dao.hibernate;

import mdrive.business.bean.GeoObjectTypeBean;
import mdrive.business.type.GeoObjectTypeCode;

/**
 * Created by IntelliJ IDEA.
 * User: Elena
 * Date: 11.01.2011
 * Time: 8:23:42
 * To change this template use File | Settings | File Templates.
 */
public interface GeoObjectTypeDAO extends GenericDAO<GeoObjectTypeBean, Long> {

    GeoObjectTypeBean findByTypeCode(GeoObjectTypeCode geoObjectTypeCode);
}