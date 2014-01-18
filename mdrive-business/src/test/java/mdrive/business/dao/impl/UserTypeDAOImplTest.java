package mdrive.business.dao.impl;

import mdrive.business.config.JpaTestConfig;
import mdrive.business.dao.I18NameDAO;
import mdrive.business.dao.UserTypeDAO;
import mdrive.business.model.I18NameBean;
import mdrive.business.model.UserTypeBean;
import mdrive.business.service.DBUnitDataLoader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by IntelliJ IDEA.
 * User: Elena
 * Date: 14.04.11
 * Time: 23:58
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JpaTestConfig.class)
@Transactional
public class UserTypeDAOImplTest {

    @Autowired
    private UserTypeDAO userTypeDAO;

    @Autowired
    private I18NameDAO i18NameDAO;

    @Autowired
    private DBUnitDataLoader dbUnitDataLoader;

    @Before
    public void init() throws Exception {
        dbUnitDataLoader.initTestData();
    }

    @Test
    public void findAll() {
        for (UserTypeBean userTypeBean : userTypeDAO.findAll()) {
            System.out.println("typeName = " + userTypeBean.getTypeI18Name().getValue());
            System.out.println(userTypeBean);
        }
    }

    @Test
    public void create_save() {
        I18NameBean i18NameBean = new I18NameBean("egnlish name", "русское имя", "украинское имя");
        i18NameDAO.persist(i18NameBean);
        UserTypeBean userTypeBean = new UserTypeBean();
        userTypeBean.setTypeI18Name(i18NameBean);
        userTypeDAO.persist(userTypeBean);
        UserTypeBean retrievedUserTypeBean = userTypeDAO.findOne(userTypeBean.getId());
        assertEquals(userTypeBean, retrievedUserTypeBean);
        System.out.println("retrievedUserTypeBean = " + retrievedUserTypeBean);
    }

    @Test
    public void getPassengerDriver() {
        UserTypeBean passengerUserType = userTypeDAO.getPassengerUserTypeBean();
        assertNotNull(passengerUserType);
        UserTypeBean driverUserType = userTypeDAO.getDriverUserTypeBean();
        assertNotNull(driverUserType);
    }
}