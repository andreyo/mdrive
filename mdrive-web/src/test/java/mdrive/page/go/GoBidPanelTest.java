package mdrive.page.go;

import mdrive.component.link.TogglableAjaxLink;
import mdrive.config.TestWebConfig;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * User: andrey.osipov
 * Date: 1/19/12
 * Time: 4:50 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestWebConfig.class)
public class GoBidPanelTest {

    @Autowired
    private WicketTester tester;


    //TODO: implement ajax tests as here AjaxLinkTest
    //

    @Test
    public void testRenderMyPage() {
        tester.startPanel(GoBidPanel.class);
        System.out.println(tester.getLastResponseAsString());
        tester.assertComponent("form:toggle2ndAddressLink", TogglableAjaxLink.class);
        tester.assertComponent("form:toggleParametersLink", TogglableAjaxLink.class);
        System.out.println("\n\n\n");
        //TODO: verify that rendered: secondAddress, remove2ndAddressLink
        System.out.println(tester.getLastResponseAsString());
    }
}