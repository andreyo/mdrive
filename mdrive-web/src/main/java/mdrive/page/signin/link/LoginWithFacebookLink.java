package mdrive.page.signin.link;

import mdrive.oauth.OAuthServiceFactory;
import org.scribe.oauth.OAuthService;

/**
 * User: andrey.osipov
 * Date: 3/29/12
 * Time: 3:31 PM
 */
public class LoginWithFacebookLink extends LoginWithOauthLink {

    public LoginWithFacebookLink(String id) {
        super(id);
    }

    @Override
    public OAuthService getService() {
        return OAuthServiceFactory.getFacebookOAuthService(getCallBackUrl());
    }

    @Override
    public String getLabelName() {
        return "Facebook";
    }
}
