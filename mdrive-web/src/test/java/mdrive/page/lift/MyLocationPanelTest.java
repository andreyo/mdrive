package mdrive.page.lift;

import mdrive.config.TestWebConfig;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * User: andrey.osipov
 * Date: 2/14/12
 * Time: 10:28 AM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestWebConfig.class)
public class MyLocationPanelTest {

    @Autowired
    WicketTester tester;

    @Test
    public void testPanel() throws Exception {
        tester.startComponentInPage(MyLocationPanel.class);
        tester.assertContains("Не определено");
        tester.clickLink(MyLocationPanel.LOCATION_LINK_ID);
        tester.assertComponent(MyLocationPanel.LOCATION_SELECTION_PANEL_ID, WebMarkupContainer.class);
        System.out.println(tester.getLastResponseAsString());
    }
}