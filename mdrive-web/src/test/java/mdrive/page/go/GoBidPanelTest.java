package mdrive.page.go;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

/**
 * User: andrey.osipov
 * Date: 1/19/12
 * Time: 4:50 PM
 */
public class GoBidPanelTest {

    private WicketTester tester;

    @Before
    public void setUp() {
        tester = new WicketTester();
    }

    //TODO: implement ajax tests as here AjaxLinkTest
    //

    @Test
    public void testRenderMyPage() {
        tester.startPanel(GoBidPanel.class);
        System.out.println(tester.getLastResponseAsString());
        tester.getComponentFromLastRenderedPage("inputForm:mapFromLink");
        tester.clickLink("inputForm:add2ndAddressLink");
        System.out.println("\n\n\n");
        //TODO: verify that rendered: secondAddress, remove2ndAddressLink
        System.out.println(tester.getLastResponseAsString());
    }
}