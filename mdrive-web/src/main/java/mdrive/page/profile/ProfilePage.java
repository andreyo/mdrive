package mdrive.page.profile;

import mdrive.app.MSession;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * User: andrey.osipov
 * Date: 3/26/12
 * Time: 7:59 PM
 */
public class ProfilePage extends WebPage {

    public static final String OAUTH_VERIFIER_PARAMETER = "oauth_verifier";
    public static final String OAUTH_TOKEN_PARAMETER = "oauth_token";

    public static final String MOUNT_URL = "profile";

    public ProfilePage() {
        init();
    }

    public ProfilePage(PageParameters parameters) {
        super(parameters);
        org.apache.wicket.util.string.StringValue verifier = parameters.get(OAUTH_VERIFIER_PARAMETER);
        org.apache.wicket.util.string.StringValue token = parameters.get(OAUTH_TOKEN_PARAMETER);
        if (verifier != null) {
            MSession.get().setAttribute(OAUTH_VERIFIER_PARAMETER, verifier);
        }
        if (token != null) {
            MSession.get().setAttribute(OAUTH_TOKEN_PARAMETER, token);
        }
        init();
    }

    public void init() {
        add(new ProfilePanel("profilePanel"));
    }
}
