package mdrive.business.dao.impl;

import mdrive.business.config.JpaTestConfig;
import mdrive.business.dao.SettingsDAO;
import mdrive.business.dao.UserDAO;
import mdrive.business.model.SettingsBean;
import mdrive.business.model.UserBean;
import mdrive.business.service.DBUnitDataExporter;
import mdrive.business.service.DBUnitDataLoader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.assertEquals;

/**
 * Created by IntelliJ IDEA.
 * User: Elena
 * Date: 09.05.12
 * Time: 9:18
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JpaTestConfig.class)
@Transactional
//@TransactionConfiguration(defaultRollback = false)
public class SettingsDAOImplTest {

    @Autowired
    private SettingsDAO settingsDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private DBUnitDataLoader dbUnitDataLoader;

    @Autowired
    private DBUnitDataExporter dbUnitDataExporter;

    @Before
    public void init() throws Exception {
        //initialize each test manually
    }

    public void addElementCollection() throws Exception {
        dbUnitDataLoader.initTestData();

        SettingsBean settingsBean = new SettingsBean();
        settingsBean.getRecentLocations().add("florida");
        settingsBean.getRecentLocations().add("washington");
        settingsBean.getRecentLocations().add("detroit");

        settingsBean.getStatistics().put(1, "50");
        settingsBean.getStatistics().put(2, "80");
        settingsBean.getStatistics().put(3, "70");

        //set user
        UserBean userBean = userDAO.findOne(1L);
        userBean.setSettingsBean(settingsBean);
        settingsBean.setUserBean(userBean);

        userDAO.persist(userBean);
    }

    @Test
    public void checkUserBeanSettingsBeanMapping() throws Exception {
        addElementCollection();

        dbUnitDataExporter.exportTables("settings_export.xml");

        UserBean userBean = userDAO.findOne(1L);
        assertEquals(userBean.getId(), userBean.getSettingsBean().getUserBean().getId());
    }

}
