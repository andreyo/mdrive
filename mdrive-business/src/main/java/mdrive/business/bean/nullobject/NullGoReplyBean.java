package mdrive.business.bean.nullobject;

import mdrive.business.bean.GoReplyBean;

/**
 * User: andrey.osipov
 * Date: 2/14/12
 * Time: 3:03 PM
 */
public class NullGoReplyBean extends GoReplyBean {
    public static final NullGoReplyBean instance = new NullGoReplyBean();

    private NullGoReplyBean() {
    }
}
