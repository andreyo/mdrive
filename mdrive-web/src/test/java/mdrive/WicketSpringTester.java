package mdrive;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * User: Elena
 * <p/>
 * This tester creates WicketApplication with Spring injected dependencies
 * and passes it to WicketTester
 * so we shouldn't create mock objects for data providers, and write tests on real data
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:testApplicationContext.xml")
public class WicketSpringTester {

    @Autowired
    protected WicketTester tester;
}
