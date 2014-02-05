package mdrive.business.dao.impl;

import mdrive.business.config.JpaTestConfig;
import mdrive.business.dao.GeoObjectDao;
import mdrive.business.dao.GoBidDao;
import mdrive.business.dao.UserDao;
import mdrive.business.dao.UserTypeDao;
import mdrive.business.model.GoBidBean;
import mdrive.business.model.GoReplyBean;
import mdrive.business.model.UserBean;
import mdrive.business.service.DBUnitDataExporter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * User: andrey.osipov
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JpaTestConfig.class)
@Transactional
public class GoBidDaoImplTest {

    @Autowired
    private GoBidDao goBidDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserTypeDao userTypeDao;

    @Autowired
    private GeoObjectDao geoObjectDao;

    @Autowired
    private DBUnitDataExporter dbUnitDataExporter;

    @PersistenceContext
    EntityManager entityManager;

    @Test
    public void getGoReplies() throws Exception {
        GoBidBean goBidBean = goBidDao.findOne(1000001L);
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
        userBean.setUserTypeBean(userTypeDao.getPassengerUserTypeBean());
        userBean.setUserName("goga");
        userDao.persist(userBean);
        goBidBean.setUserBean(userBean);
        goBidBean.setFromGeoObjectBean(geoObjectDao.findOne(5L));
        goBidBean.setToGeoObjectBean(geoObjectDao.findOne(5L));
        goBidDao.persist(goBidBean);
    }

    @Test
    @Rollback(false)
    public void test1() throws Exception {
        UserBean userBean = new UserBean();
        userBean.setUserTypeBean(userTypeDao.getPassengerUserTypeBean());
        userBean.setUserName("goga");
        userDao.persist(userBean);

        GoBidBean goBidBean1 = new GoBidBean();
        goBidBean1.setUserBean(userBean);
        goBidBean1.setFromGeoObjectBean(geoObjectDao.findOne(5L));
        goBidBean1.setToGeoObjectBean(geoObjectDao.findOne(5L));

        GoBidBean goBidBean2 = new GoBidBean();
        goBidBean2.setUserBean(userBean);
        goBidBean2.setFromGeoObjectBean(geoObjectDao.findOne(5L));
        goBidBean2.setToGeoObjectBean(geoObjectDao.findOne(5L));


        goBidDao.persist(goBidBean1);
        goBidDao.persist(goBidBean2);
        goBidDao.delete(goBidBean2);
        entityManager.flush();

        dbUnitDataExporter.exportTables("");
    }

    @Test
    public void export() throws Exception {
        //exports not to this file (uses default location)
        dbUnitDataExporter.exportTables("");
    }
}