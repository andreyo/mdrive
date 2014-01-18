package mdrive.business.model.nullobject;

import mdrive.business.model.GoReplyBean;

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
