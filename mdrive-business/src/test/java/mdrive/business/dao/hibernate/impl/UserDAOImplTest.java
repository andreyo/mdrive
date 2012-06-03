package mdrive.business.dao.hibernate.impl;

import mdrive.business.HsqldbJUnit4SpringContextTests;
import mdrive.business.bean.GoBidBean;
import mdrive.business.bean.GoReplyBean;
import mdrive.business.dao.hibernate.I18NameDAO;
import mdrive.business.dao.hibernate.UserDAO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Elena
 * Date: 13.05.12
 * Time: 11:41
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testApplicationContext.xml"})
public class UserDAOImplTest extends HsqldbJUnit4SpringContextTests {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private I18NameDAO i18NameDAO;

    @Before
    public void init() throws Exception {
        initTestDataXml();
    }

    @Test
    public void getGoBids() {
        List<GoBidBean> goBids = userDAO.findById(1L).getGoBids();
        for (GoBidBean goBid : goBids) {
            System.out.println(goBid);
        }
    }

    @Test
    public void getGoReplies() {
        List<GoReplyBean> goReplies = userDAO.findById(2L).getGoReplies();
        for (GoReplyBean goReply : goReplies) {
            System.out.println(goReply);
        }
    }
}
