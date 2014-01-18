package mdrive.business.dao.impl;

import mdrive.business.config.JpaTestConfig;
import mdrive.business.dao.GeoObjectDAO;
import mdrive.business.dao.GoBidDAO;
import mdrive.business.dao.UserDAO;
import mdrive.business.dao.UserTypeDAO;
import mdrive.business.model.GeoObjectBean;
import mdrive.business.model.GoBidBean;
import mdrive.business.model.GoReplyBean;
import mdrive.business.model.UserBean;
import mdrive.business.service.DBUnitDataExporter;
import mdrive.business.service.DBUnitDataLoader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.*;

/**
 * User: andrey.osipov
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JpaTestConfig.class)
@Transactional
public class GoBidDAOImplTest {

    @Autowired
    private GoBidDAO goBidDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserTypeDAO userTypeDAO;

    @Autowired
    private GeoObjectDAO geoObjectDAO;

    @Autowired
    private DBUnitDataLoader dbUnitDataLoader;

    @Autowired
    private DBUnitDataExporter dbUnitDataExporter;

    @Before
    public void init() throws Exception {
        dbUnitDataLoader.initTestDataXml();
    }

    @Test
    public void getBidsByGeoObjectCoordinates() throws Exception {
        for (GoBidBean goBidBean : goBidDAO.getBidsByGeoObjectCoordinates(new GeoObjectBean())) {
            //verify that initialized
            assertNotNull(goBidBean.getFromGeoObjectBean().getObjectI18Name());
            assertNotNull(goBidBean.getFromGeoObjectBean().getParentGeoObjectBean().getObjectI18Name());

            assertNotNull(goBidBean.getToGeoObjectBean().getObjectI18Name());
            assertNotNull(goBidBean.getToGeoObjectBean().getParentGeoObjectBean().getObjectI18Name());

            System.out.println(goBidBean);
        }
    }

    @Test
    public void getGoReplies() throws Exception {
        GoBidBean goBidBean = goBidDAO.findOne(1000001L);
        assertTrue(goBidBean.getGoReplies().size() > 0);
        for (GoReplyBean goReplyBean : goBidBean.getGoReplies()) {
            System.out.println(goReplyBean);
            assertEquals(goBidBean, goReplyBean.getGoBidBean());
        }
    }

    @Test
    public void createGoBidBean() throws Exception {
        GoBidBean goBidBean = new GoBidBean();
        UserBean userBean = new UserBean();
        userBean.setUserTypeBean(userTypeDAO.getPassengerUserTypeBean());
        userBean.setUserName("goga");
        userDAO.persist(userBean);
        goBidBean.setUserBean(userBean);
        goBidBean.setFromGeoObjectBean(geoObjectDAO.findOne(5L));
        goBidBean.setToGeoObjectBean(geoObjectDAO.findOne(5L));
        goBidDAO.persist(goBidBean);
    }

    @Test
    public void export() throws Exception {
        //exports not to this file (uses default location)
        dbUnitDataExporter.exportTables("");
    }
}