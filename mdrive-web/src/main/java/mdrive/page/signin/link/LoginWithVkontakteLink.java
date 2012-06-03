package mdrive.page.signin.link;

import mdrive.oauth.OAuthServiceFactory;
import org.scribe.oauth.OAuthService;

/**
 * User: andrey.osipov
 * Date: 3/29/12
 * Time: 3:31 PM
 */
public class LoginWithVkontakteLink extends LoginWithOauthLink {

    public LoginWithVkontakteLink(String id) {
        super(id);
    }

    @Override
    public OAuthService getService() {
        return OAuthServiceFactory.getVkontakteOAuthService(getCallBackUrl());
    }

    @Override
    public String getLabelName() {
        return "Vkontakte";
    }

    @Override
    protected String getAuthorizationUrl() {
//        Token requestToken = getService().getRequestToken();
        return getService().getAuthorizationUrl(OAuthServiceFactory.EMPTY_TOKEN);
    }
}
