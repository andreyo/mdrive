package mdrive.page.profile;

import mdrive.app.MSession;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * User: andrey.osipov
 * Date: 3/26/12
 * Time: 7:53 PM
 */
public class ProfilePanel extends Panel {

    public ProfilePanel(String id) {
        super(id);
        org.apache.wicket.util.string.StringValue verifier = (org.apache.wicket.util.string.StringValue) MSession.get().getAttribute(ProfilePage.OAUTH_VERIFIER_PARAMETER);
        org.apache.wicket.util.string.StringValue token = (org.apache.wicket.util.string.StringValue) MSession.get().getAttribute(ProfilePage.OAUTH_TOKEN_PARAMETER);
        add(new MultiLineLabel("info", "" + verifier + token));
    }
}
