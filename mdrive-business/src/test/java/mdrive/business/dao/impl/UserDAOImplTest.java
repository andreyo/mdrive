package mdrive.business.dao.impl;

import mdrive.business.config.JpaTestConfig;
import mdrive.business.dao.I18NameDAO;
import mdrive.business.dao.UserDAO;
import mdrive.business.model.GoBidBean;
import mdrive.business.model.GoReplyBean;
import mdrive.business.service.DBUnitDataLoader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Elena
 * Date: 13.05.12
 * Time: 11:41
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JpaTestConfig.class)
@Transactional
public class UserDAOImplTest {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private I18NameDAO i18NameDAO;

    @Autowired
    private DBUnitDataLoader dbUnitDataLoader;

    @Before
    public void init() throws Exception {
        dbUnitDataLoader.initTestDataXml();
    }

    @Test
    public void getGoBids() {
        List<GoBidBean> goBids = userDAO.findOne(1L).getGoBids();
        for (GoBidBean goBid : goBids) {
            System.out.println(goBid);
        }
    }

    @Test
    public void getGoReplies() {
        List<GoReplyBean> goReplies = userDAO.findOne(2L).getGoReplies();
        for (GoReplyBean goReply : goReplies) {
            System.out.println(goReply);
        }
    }
}
