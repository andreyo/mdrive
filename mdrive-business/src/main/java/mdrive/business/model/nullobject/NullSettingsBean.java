package mdrive.business.model.nullobject;

import mdrive.business.model.SettingsBean;

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
