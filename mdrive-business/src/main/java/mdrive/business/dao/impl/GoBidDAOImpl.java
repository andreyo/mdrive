package mdrive.business.dao.impl;

import mdrive.business.model.GeoObjectBean;
import mdrive.business.model.GoBidBean;
import mdrive.business.dao.GoBidDAO;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
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
public class GoBidDAOImpl extends GenericDaoImpl<GoBidBean> implements GoBidDAO {

    public static final Logger log = Logger.getLogger(GoBidDAOImpl.class);


    /**
     * Every GeoObject has wrapping it rectangle, this function search Bids inside this rectangle
     *
     * @param geoObjectBean
     * @return
     * @throws DataAccessException
     */
    @Transactional(readOnly = true)
    public List<GoBidBean> getBidsByGeoObjectCoordinates(GeoObjectBean geoObjectBean) throws DataAccessException {
        String hql = "from GoBidBean";
        List<GoBidBean> goBidBeans = findAll();
        for (GoBidBean goBidBean : goBidBeans) {
            try {
                Hibernate.initialize(goBidBean.getFromGeoObjectBean().getObjectI18Name());
                Hibernate.initialize(goBidBean.getFromGeoObjectBean().getParentGeoObjectBean().getObjectI18Name());
                Hibernate.initialize(goBidBean.getToGeoObjectBean().getObjectI18Name());
                Hibernate.initialize(goBidBean.getToGeoObjectBean().getParentGeoObjectBean().getObjectI18Name());
            } catch (Exception e) {
                log.error(e);
            }
        }
        return goBidBeans;
    }
}