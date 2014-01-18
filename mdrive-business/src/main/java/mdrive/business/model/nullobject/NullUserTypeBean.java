package mdrive.business.model.nullobject;

import mdrive.business.model.UserTypeBean;

/**
 * User: andrey.osipov
 * Date: 2/14/12
 * Time: 3:04 PM
 */
public class NullUserTypeBean extends UserTypeBean {
    public static final NullUserTypeBean instance = new NullUserTypeBean();

    private NullUserTypeBean() {
    }
}
