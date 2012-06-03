package mdrive.page.go.replies;

import mdrive.WicketSpringTester;
import org.apache.wicket.Component;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;

/**
 * User: andrey.osipov
 * Date: 1/27/12
 * Time: 2:16 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:testApplicationContext.xml")
public class GoRepliesPanelTest extends WicketSpringTester {

    @Test
    public void testPanel() {
        tester.startPanel(GoRepliesPanel.class);
        Component component = tester.getComponentFromLastRenderedPage("inputForm:selectAll");
        assertNotNull(component);
        System.out.println(component);
    }
}