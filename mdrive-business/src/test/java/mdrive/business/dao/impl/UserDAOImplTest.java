package mdrive.business.dao.impl;

import mdrive.business.config.JpaTestConfig;
import mdrive.business.dao.I18NameDao;
import mdrive.business.dao.UserDao;
import mdrive.business.model.GoBidBean;
import mdrive.business.model.GoReplyBean;
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
public class UserDaoImplTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private I18NameDao i18NameDao;

    @Test
    public void getGoBids() {
        List<GoBidBean> goBids = userDao.findOne(1L).getGoBids();
        for (GoBidBean goBid : goBids) {
            System.out.println(goBid);
        }
    }

    @Test
    public void getGoReplies() {
        List<GoReplyBean> goReplies = userDao.findOne(2L).getGoReplies();
        for (GoReplyBean goReply : goReplies) {
            System.out.println(goReply);
        }
    }
}
