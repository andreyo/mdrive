package mdrive;

import mdrive.config.TestWebConfig;
import mdrive.page.HomePage;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestWebConfig.class)
public class TestHomePage {

    @Autowired
    WicketTester tester;

    @Test
    public void testRenderMyPage() {
        tester.startPage(HomePage.class);

        tester.assertRenderedPage(HomePage.class);
        tester.assertContains("Version");
    }
}