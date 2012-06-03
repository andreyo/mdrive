package mdrive.business.bean.nullobject;

import mdrive.business.bean.SettingsBean;

/**
 * User: andrey.osipov
 * Date: 2/14/12
 * Time: 3:04 PM
 */
public class NullSettingsBean extends SettingsBean {
    public static final NullSettingsBean instance = new NullSettingsBean();

    private NullSettingsBean() {
    }
}
