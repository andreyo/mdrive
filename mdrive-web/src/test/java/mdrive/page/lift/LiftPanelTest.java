package mdrive.page.lift;

import mdrive.WicketSpringTester;
import mdrive.app.MSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * User: andrey.osipov
 * Date: 1/25/12
 * Time: 4:45 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:testApplicationContext.xml")
public class LiftPanelTest extends WicketSpringTester {

    @Test
    public void testPanel() {
        ((MSession) tester.getSession()).signIn("a", "a");
        tester.startPanel(LiftPanel.class);
        tester.assertContains("No Records Found");
//        System.out.println(tester.getLastResponseAsString());
    }
}