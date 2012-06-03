package mdrive.business.dao.hibernate;

import mdrive.business.bean.GeoObjectBean;
import mdrive.business.bean.GoBidBean;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Elena
 * Date: 11.01.2011
 * Time: 8:23:42
 * To change this template use File | Settings | File Templates.
 */
public interface GoBidDAO extends GenericDAO<GoBidBean, Long> {

    List<GoBidBean> getBidsByGeoObjectCoordinates(GeoObjectBean geoObjectBean) throws DataAccessException;
}