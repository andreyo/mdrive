package mdrive.business.model.nullobject;

import mdrive.business.model.UserBean;

/**
 * User: andrey.osipov
 * Date: 2/14/12
 * Time: 3:04 PM
 */
public class NullUserBean extends UserBean {
    public static final NullUserBean instance = new NullUserBean();

    private NullUserBean() {
    }
}
