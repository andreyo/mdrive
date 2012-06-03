package mdrive.business.bean.nullobject;

import mdrive.business.bean.UserTypeBean;

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
