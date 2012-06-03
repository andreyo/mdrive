package mdrive.component.autocompletegeo;

import mdrive.WicketSpringTester;
import org.apache.wicket.markup.html.form.TextField;
import org.junit.Test;

/**
 * User: andrey.osipov
 * Date: 2/14/12
 * Time: 9:08 AM
 */
public class AutoCompleteStreetBuildingPanelTest extends WicketSpringTester {

    @Test
    public void testPanel() throws Exception {
        tester.startComponentInPage(AutoCompleteStreetBuildingPanel.class);
        tester.assertComponent("street:searchText", TextField.class);
        tester.assertComponent("building:searchText", TextField.class);
//        System.out.println(tester.getLastResponseAsString());
    }
}
