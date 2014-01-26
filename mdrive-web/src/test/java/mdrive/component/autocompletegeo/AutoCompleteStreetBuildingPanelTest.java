package mdrive.component.autocompletegeo;

import mdrive.config.TestWebConfig;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * User: andrey.osipov
 * Date: 2/14/12
 * Time: 9:08 AM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestWebConfig.class)
public class AutoCompleteStreetBuildingPanelTest {

    @Autowired
    WicketTester tester;

    @Test
    public void testPanel() throws Exception {
        tester.startComponentInPage(AutoCompleteStreetBuildingPanel.class);
        tester.assertComponent("street:searchText", TextField.class);
        tester.assertComponent("building:searchText", TextField.class);
//        System.out.println(tester.getLastResponseAsString());
    }
}
