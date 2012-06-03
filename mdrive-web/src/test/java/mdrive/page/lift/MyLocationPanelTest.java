package mdrive.page.lift;

import mdrive.WicketSpringTester;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * User: andrey.osipov
 * Date: 2/14/12
 * Time: 10:28 AM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:testApplicationContext.xml")
public class MyLocationPanelTest extends WicketSpringTester {

    @Test
    public void testPanel() throws Exception {
        tester.startComponentInPage(MyLocationPanel.class);
        tester.assertContains("Unknown");
        tester.clickLink(MyLocationPanel.LOCATION_LINK_ID);
        tester.assertComponent(MyLocationPanel.LOCATION_SELECTION_PANEL_ID, WebMarkupContainer.class);
        System.out.println(tester.getLastResponseAsString());
    }
}