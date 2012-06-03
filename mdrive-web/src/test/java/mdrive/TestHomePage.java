package mdrive;

import mdrive.page.HomePage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:testApplicationContext.xml")
public class TestHomePage extends WicketSpringTester {

    @Test
    public void testRenderMyPage() {
        tester.startPage(HomePage.class);

        tester.assertRenderedPage(HomePage.class);
        tester.assertContains("Version");
    }
}