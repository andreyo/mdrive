package mdrive.page.lift;

import mdrive.app.MSession;
import mdrive.config.TestWebConfig;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * User: andrey.osipov
 * Date: 1/25/12
 * Time: 4:45 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestWebConfig.class)
public class LiftPanelTest {

    @Autowired
    WicketTester tester;

    @Test
    public void testPanel() {
        ((MSession) tester.getSession()).signIn("a", "a");
        tester.startPanel(LiftPanel.class);
//        tester.assertContains("No Records Found");
        System.out.println(tester.getLastResponseAsString());
    }
}