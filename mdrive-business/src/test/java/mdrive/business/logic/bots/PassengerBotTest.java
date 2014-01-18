package mdrive.business.logic.bots;

import mdrive.business.config.JpaTestConfig;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.fail;

/**
 * EternalOnOffThread Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>03/26/2011</pre>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JpaTestConfig.class)
public class PassengerBotTest {

    private static final Logger log = Logger.getLogger(PassengerBotTest.class);

    @Autowired
    private PassengerBot passengerBot;

    @Before
    public void init() {
    }

    @Test
    public void testBot() {
        passengerBot.setActive(true);
        try {
            //wait
            Thread.sleep(6000);
            passengerBot.setActive(false);
            Thread.sleep(6000);
        } catch (Exception e) {
            fail(e.toString());
        }
    }
}