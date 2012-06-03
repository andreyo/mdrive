package mdrive.page.signin.link;

import mdrive.oauth.OAuthServiceFactory;
import org.scribe.oauth.OAuthService;

/**
 * Created by IntelliJ IDEA.
 * User: Elena
 * Date: 28.03.12
 * Time: 9:26
 * To change this template use File | Settings | File Templates.
 */
public class LoginWithYahooLink extends LoginWithOauthLink {

    public LoginWithYahooLink(String id) {
        super(id);
    }

    @Override
    public OAuthService getService() {
        return OAuthServiceFactory.getYahooOAuthService(getCallBackUrl());
    }

    @Override
    public String getLabelName() {
        return "Yahoo!";
    }
}
