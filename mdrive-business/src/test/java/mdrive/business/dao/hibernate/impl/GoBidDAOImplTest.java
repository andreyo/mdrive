package mdrive.business.dao.hibernate.impl;

import mdrive.business.HsqldbJUnit4SpringContextTests;
import mdrive.business.bean.GeoObjectBean;
import mdrive.business.bean.GoBidBean;
import mdrive.business.bean.GoReplyBean;
import mdrive.business.bean.UserBean;
import mdrive.business.dao.hibernate.GeoObjectDAO;
import mdrive.business.dao.hibernate.GoBidDAO;
import mdrive.business.dao.hibernate.UserDAO;
import mdrive.business.dao.hibernate.UserTypeDAO;
import mdrive.business.service.DBUnitDataExporter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * User: andrey.osipov
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testApplicationContext.xml"})
public class GoBidDAOImplTest extends HsqldbJUnit4SpringContextTests {

    @Autowired
    private GoBidDAO goBidDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserTypeDAO userTypeDAO;

    @Autowired
    private GeoObjectDAO geoObjectDAO;

    @Autowired
    private DBUnitDataExporter dbUnitDataExporter;

    @Before
    public void init() throws Exception {
        initTestDataXml();
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
        GoBidBean goBidBean = goBidDAO.findById(1000001L);
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
        userDAO.create(userBean);
        goBidBean.setUserBean(userBean);
        goBidBean.setFromGeoObjectBean(geoObjectDAO.findById(5L));
        goBidBean.setToGeoObjectBean(geoObjectDAO.findById(5L));
        goBidDAO.create(goBidBean);
    }

    @Test
    public void export() throws Exception {
        //exports not to this file (uses default location)
        dbUnitDataExporter.exportTables("");
    }
}