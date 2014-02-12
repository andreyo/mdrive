package mdrive.business.dao.impl;

import mdrive.business.config.JpaTestConfig;
import mdrive.business.dao.I18NameDao;
import mdrive.business.dao.UserTypeDao;
import mdrive.business.model.I18NameBean;
import mdrive.business.model.UserTypeBean;
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
public class UserTypeDaoImplTest {

    @Autowired
    private UserTypeDao userTypeDao;

    @Autowired
    private I18NameDao i18NameDao;

    @Test
    public void findAll() {
        for (UserTypeBean userTypeBean : userTypeDao.findAll()) {
            System.out.println("typeName = " + userTypeBean.getName().getValue());
            System.out.println(userTypeBean);
        }
    }

    @Test
    public void create_save() {
        I18NameBean i18NameBean = new I18NameBean("egnlish name", "русское имя", "украинское имя");
        i18NameDao.persist(i18NameBean);
        UserTypeBean userTypeBean = new UserTypeBean();
        userTypeBean.setName(i18NameBean);
        userTypeDao.persist(userTypeBean);
        UserTypeBean retrievedUserTypeBean = userTypeDao.findOne(userTypeBean.getId());
        assertEquals(userTypeBean, retrievedUserTypeBean);
        System.out.println("retrievedUserTypeBean = " + retrievedUserTypeBean);
    }

    @Test
    public void getPassengerDriver() {
        UserTypeBean passengerUserType = userTypeDao.getPassengerUserTypeBean();
        assertNotNull(passengerUserType);
        UserTypeBean driverUserType = userTypeDao.getDriverUserTypeBean();
        assertNotNull(driverUserType);
    }
}