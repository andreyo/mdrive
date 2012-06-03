package mdrive.page.signin;

import mdrive.page.signin.CustomSignInPanel;
import org.apache.wicket.markup.html.WebPage;

/**
 * Created by IntelliJ IDEA.
 * User: Elena
 * Date: 10.03.12
 * Time: 16:14
 * To change this template use File | Settings | File Templates.
 */
public class SignInPage extends WebPage {

    public SignInPage() {
        add(new CustomSignInPanel("signInPanel", false));
    }
}