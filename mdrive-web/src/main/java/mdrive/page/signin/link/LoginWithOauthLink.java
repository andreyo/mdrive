package mdrive.page.signin.link;

import mdrive.page.profile.ProfilePage;
import org.apache.commons.lang.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.html.pages.RedirectPage;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Url;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

/**
 * Created by IntelliJ IDEA.
 * User: Elena
 * Date: 28.03.12
 * Time: 8:59
 * To change this template use File | Settings | File Templates.
 */
public abstract class LoginWithOauthLink extends AjaxFallbackLink {

    private static String callBackUrl;

    public LoginWithOauthLink(String id) {
        super(id);
    }

    @Override
    public void onClick(AjaxRequestTarget target) {
        getRequestCycle().setResponsePage(new RedirectPage(getAuthorizationUrl()));
    }

    protected String getAuthorizationUrl() {
        Token requestToken = getService().getRequestToken();
        return getService().getAuthorizationUrl(requestToken);
    }

    public abstract OAuthService getService();

    public abstract String getLabelName();

    public synchronized String getCallBackUrl() {
        if (callBackUrl != null) {
            return callBackUrl;
        }
        Request request = getRequest();
        Url url = request.getClientUrl();
        String hostName = url.getHost();
        int port = url.getPort();
        String protocol = url.getProtocol();
        String context = request.getContextPath();
        callBackUrl = protocol + "://" + hostName + ":" + port + "/";
        if (!StringUtils.isEmpty(context)) {
            callBackUrl += context + "/";
        }
        return callBackUrl + ProfilePage.MOUNT_URL;
    }
}
