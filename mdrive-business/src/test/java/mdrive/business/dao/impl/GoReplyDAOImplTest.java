package mdrive.business.dao.impl;

import mdrive.business.config.JpaTestConfig;
import mdrive.business.dao.GoBidDAO;
import mdrive.business.dao.GoReplyDAO;
import mdrive.business.model.GoReplyBean;
import mdrive.business.service.DBUnitDataLoader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.assertEquals;

/**
 * Created by IntelliJ IDEA.
 * User: Elena
 * Date: 11.01.2011
 * Time: 21:57:30
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JpaTestConfig.class)
@Transactional
public class GoReplyDAOImplTest {

    @Autowired
    private GoReplyDAO goReplyDAO;

    @Autowired
    private GoBidDAO goBidDAO;

    @Autowired
    private DBUnitDataLoader dbUnitDataLoader;

    @Before
    public void init() throws Exception {
        dbUnitDataLoader.initTestData();
    }

    @Test
    public void testFindById() throws Exception {
        GoReplyBean goReplyBean1 = goReplyDAO.findAll().get(2);
        GoReplyBean goReplyBean2 = goReplyDAO.findOne(Long.valueOf(goReplyBean1.getId()));
        assertEquals(goReplyBean1, goReplyBean2);
    }

    @Test
    public void testFindAll() throws Exception {
        for (GoReplyBean goReplyBean : goReplyDAO.findAll()) {
            System.out.println(goReplyBean);
        }
    }

    @Test
    public void getGoBid() throws Exception {
        GoReplyBean goReplyBean = goReplyDAO.findOne(1000002L);
        assertEquals(goBidDAO.findOne(1000001L), goReplyBean.getGoBidBean());
    }

    @Test
    public void getUser() throws Exception {
        GoReplyBean goReplyBean = goReplyDAO.findOne(1000002L);
        System.out.println(goReplyBean.getUserBean());
    }
}