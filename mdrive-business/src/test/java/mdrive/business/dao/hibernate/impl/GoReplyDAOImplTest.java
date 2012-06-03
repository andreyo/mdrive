package mdrive.business.dao.hibernate.impl;

import mdrive.business.HsqldbJUnit4SpringContextTests;
import mdrive.business.bean.GoReplyBean;
import mdrive.business.dao.hibernate.GoBidDAO;
import mdrive.business.dao.hibernate.GoReplyDAO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

/**
 * Created by IntelliJ IDEA.
 * User: Elena
 * Date: 11.01.2011
 * Time: 21:57:30
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testApplicationContext.xml"})
public class GoReplyDAOImplTest extends HsqldbJUnit4SpringContextTests {

    @Autowired
    private GoReplyDAO goReplyDAO;

    @Autowired
    private GoBidDAO goBidDAO;

    @Before
    public void init() throws Exception {
        initTestData();
    }

    @Test
    public void testFindById() throws Exception {
        GoReplyBean goReplyBean1 = goReplyDAO.findAll().get(2);
        GoReplyBean goReplyBean2 = goReplyDAO.findById(Long.valueOf(goReplyBean1.getId()));
        assertEquals(goReplyBean1, goReplyBean2);
    }

    @Test
    public void testFindAll() throws Exception {
        for (GoReplyBean goReplyBean : goReplyDAO.findAll()) {
            System.out.println(goReplyBean);
        }
    }

    @Test
    public void testFindByExample() throws Exception {
        for (GoReplyBean goReplyBean : goReplyDAO.findByExample(new GoReplyBean())) {
            System.out.println(goReplyBean);
        }
    }

    @Test
    public void getGoBid() throws Exception {
        GoReplyBean goReplyBean = goReplyDAO.findById(1000002L);
        assertEquals(goBidDAO.findById(1000001L), goReplyBean.getGoBidBean());
    }

    @Test
    public void getUser() throws Exception {
        GoReplyBean goReplyBean = goReplyDAO.findById(1000002L);
        System.out.println(goReplyBean.getUserBean());
    }
}