package mdrive.page.signin.link;

import mdrive.oauth.OAuthServiceFactory;
import org.scribe.oauth.OAuthService;

/**
 * Created by IntelliJ IDEA.
 * User: Elena
 * Date: 28.03.12
 * Time: 9:03
 * To change this template use File | Settings | File Templates.
 */
public class LoginWithGoogleLink extends LoginWithOauthLink {

    public LoginWithGoogleLink(String id) {
        super(id);
    }

    @Override
    public OAuthService getService() {
        return OAuthServiceFactory.getGoogleOAuthService(getCallBackUrl());
    }

    @Override
    public String getLabelName() {
        return "Google";
    }
}
