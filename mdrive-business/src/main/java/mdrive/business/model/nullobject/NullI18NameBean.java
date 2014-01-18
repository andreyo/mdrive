package mdrive.business.model.nullobject;

import mdrive.business.model.I18NameBean;

/**
 * User: andrey.osipov
 * Date: 2/14/12
 * Time: 1:02 PM
 */
public class NullI18NameBean extends I18NameBean {
    public static final NullI18NameBean instance = new NullI18NameBean();

    private NullI18NameBean() {
    }
}