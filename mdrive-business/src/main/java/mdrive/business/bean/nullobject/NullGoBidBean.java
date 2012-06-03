package mdrive.business.bean.nullobject;

import mdrive.business.bean.GoBidBean;

/**
 * User: andrey.osipov
 * Date: 2/14/12
 * Time: 3:03 PM
 */
public class NullGoBidBean extends GoBidBean {
    public static final NullGoBidBean instance = new NullGoBidBean();

    private NullGoBidBean() {
    }
}