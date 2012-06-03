package mdrive.business;

import org.junit.Test;

import static org.junit.Assert.fail;

/**
 * Created by IntelliJ IDEA.
 * User: Elena
 * Date: 01.04.12
 * Time: 8:53
 * To change this template use File | Settings | File Templates.
 */
public class DummyTest {

    @Test
    public void autoUnboxing() {
        Integer param = null;
        try {
            methodWithAutoUnboxing(param);
        } catch (NullPointerException e) {
            return;//OK
        }
        fail("no exception thrown");
    }

    public void methodWithAutoUnboxing(int a) {

    }
}
