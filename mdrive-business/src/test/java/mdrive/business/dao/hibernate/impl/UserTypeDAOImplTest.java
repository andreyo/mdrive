package mdrive.business.dao.hibernate.impl;

import mdrive.business.HsqldbJUnit4SpringContextTests;
import mdrive.business.bean.I18NameBean;
import mdrive.business.bean.UserTypeBean;
import mdrive.business.dao.hibernate.I18NameDAO;
import mdrive.business.dao.hibernate.UserTypeDAO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
@ContextConfiguration(locations = {"classpath:testApplicationContext.xml"})
public class UserTypeDAOImplTest extends HsqldbJUnit4SpringContextTests {

    @Autowired
    private UserTypeDAO userTypeDAO;

    @Autowired
    private I18NameDAO i18NameDAO;

    @Before
    public void init() throws Exception {
        initTestData();
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
        i18NameDAO.create(i18NameBean);
        UserTypeBean userTypeBean = new UserTypeBean();
        userTypeBean.setTypeI18Name(i18NameBean);
        Long id = userTypeDAO.create(userTypeBean);
        UserTypeBean retrievedUserTypeBean = userTypeDAO.findById(id);
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