package mdrive.page.signin;

import mdrive.page.signin.link.*;
import org.apache.wicket.authroles.authentication.panel.SignInPanel;
import org.apache.wicket.extensions.breadcrumb.panel.BreadCrumbPanel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

import java.util.Arrays;
import java.util.List;

/**
 * User: andrey.osipov
 * Date: 3/26/12
 * Time: 7:17 PM
 */
//TODO: make it BreadCrumb (can't go back)
public class CustomSignInPanel extends BreadCrumbPanel {

    public static final String LINK_ID = "linkId";

    List<LoginWithOauthLink> loginLinksList = Arrays.asList(new LoginWithFacebookLink(LINK_ID),
            new LoginWithGoogleLink(LINK_ID),
            new LoginWithTwitterLink(LINK_ID),
            new LoginWithVkontakteLink(LINK_ID),
            new LoginWithYahooLink(LINK_ID));

    @Override
    public String getTitle() {
        return "Login";
    }

    public CustomSignInPanel(String id, final boolean includeRememberMe) {
        super(id, null);
        add(new SignInPanel("regularSignInPanel", includeRememberMe));

        ListView<LoginWithOauthLink> loginLinksListView = new ListView<LoginWithOauthLink>("loginLinksListView",
                loginLinksList) {
            @Override
            protected void populateItem(ListItem<LoginWithOauthLink> item) {
                LoginWithOauthLink loginWithOauthLink = item.getModelObject();
                loginWithOauthLink.add(new Label("linkLabel", loginWithOauthLink.getLabelName()));
                item.add(loginWithOauthLink);
            }
        };
        add(loginLinksListView);
    }
}
