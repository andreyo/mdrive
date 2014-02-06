package mdrive.business.dao;

import mdrive.business.model.GeoObjectBean;
import mdrive.business.model.GoBidBean;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Elena
 * Date: 11.01.2011
 * Time: 8:25:52
 * To change this template use File | Settings | File Templates.
 */
@Transactional
@Component
public class GoBidDao extends GenericDao<GoBidBean> {

    public static final Logger log = Logger.getLogger(GoBidDao.class);


    /**
     * Every GeoObject has wrapping it rectangle, this function search Bids inside this rectangle
     *
     * @param searchInGeoObjectBean
     * @return
     * @throws DataAccessException
     */
    @Transactional(readOnly = true)
    public List<GoBidBean> getBidsWithinGeoObjectCoordinates(GeoObjectBean searchInGeoObjectBean) throws DataAccessException {
        return findAll();
    }
}