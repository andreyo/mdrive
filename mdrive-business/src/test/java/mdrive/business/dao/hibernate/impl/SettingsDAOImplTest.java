package mdrive.business.dao.hibernate.impl;

import mdrive.business.HsqldbJUnit4SpringContextTests;
import mdrive.business.bean.SettingsBean;
import mdrive.business.bean.UserBean;
import mdrive.business.dao.hibernate.SettingsDAO;
import mdrive.business.dao.hibernate.UserDAO;
import mdrive.business.service.DBUnitDataExporter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

/**
 * Created by IntelliJ IDEA.
 * User: Elena
 * Date: 09.05.12
 * Time: 9:18
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testApplicationContext.xml"})
public class SettingsDAOImplTest extends HsqldbJUnit4SpringContextTests {

    @Autowired
    private SettingsDAO settingsDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private DBUnitDataExporter dbUnitDataExporter;

    @Before
    public void init() throws Exception {
        //initialize each test manually
    }

    @Test
    @Rollback(false)
    public void addElementCollection() throws Exception {
        initTestData();

        SettingsBean settingsBean = new SettingsBean();
        UserBean userBean = userDAO.findById(1L);
        settingsBean.setUserBean(userBean);

        settingsBean.getRecentLocations().add("florida");
        settingsBean.getRecentLocations().add("washington");
        settingsBean.getRecentLocations().add("detroit");

        settingsBean.getStatistics().put(1, "50");
        settingsBean.getStatistics().put(2, "80");
        settingsBean.getStatistics().put(3, "70");
        settingsDAO.create(settingsBean);
//        userDAO.update(userBean);
    }

    @Test
    public void checkUserBeanSettingsBeanMapping() {
        UserBean userBean = userDAO.findById(1L);
        assertEquals(userBean, userBean.getSettingsBean().getUserBean());
    }

    @Test
    public void export() throws Exception {
        //exports not to this file (uses default location)
        dbUnitDataExporter.exportTables("settings_export.xml");
    }
}
