package mdrive.business.util;

import mdrive.business.model.GoBidBean;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * User: andrey.osipov
 * Date: 1/24/12
 * Time: 5:39 PM
 */
public class ToStringModelBeanHelperTest {

    @Test
    public void toStringTest() {
        GoBidBean goBidBean = new GoBidBean();
        goBidBean.setComment("few words");
        String result = ToStringModelBeanHelper.toString(goBidBean);
        assertTrue(result.contains("mdrive.business.model.GoBidBean"));
    }
}