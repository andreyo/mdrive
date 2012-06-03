package mdrive.page.signin.link;

import mdrive.oauth.OAuthServiceFactory;
import org.scribe.oauth.OAuthService;

/**
 * User: andrey.osipov
 * Date: 3/29/12
 * Time: 3:30 PM
 */
public class LoginWithTwitterLink extends LoginWithOauthLink {

    public LoginWithTwitterLink(String id) {
        super(id);
    }

    @Override
    public OAuthService getService() {
        return OAuthServiceFactory.getTwitterOAuthService(getCallBackUrl());
    }

    @Override
    public String getLabelName() {
        return "twitter";
    }
}
