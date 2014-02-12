package mdrive.app;

import mdrive.app.config.WebConfig;
import mdrive.business.type.Constants;
import mdrive.page.HomePage;
import mdrive.page.profile.ProfilePage;
import mdrive.page.signin.SignInPage;
import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.Session;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AnnotationsRoleAuthorizationStrategy;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.component.IRequestableComponent;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class MApplication extends AuthenticatedWebApplication {

    private transient ApplicationContext springContext;

    /**
     * Constructor
     */
    public MApplication() {
    }

    public static MApplication get() {
        return (MApplication) WebApplication.get();
    }

    @Override
    public void init() {
        super.init();
        getDebugSettings().setAjaxDebugModeEnabled(true);
        getDebugSettings().setDevelopmentUtilitiesEnabled(true);
        springContext = new AnnotationConfigApplicationContext(WebConfig.class);
        getComponentInstantiationListeners().add(new SpringComponentInjector(this, springContext, true));

        getSecuritySettings().setAuthorizationStrategy(new AnnotationsRoleAuthorizationStrategy(this) {

            @Override
            public <T extends IRequestableComponent> boolean isInstantiationAuthorized(Class<T> componentClass) {
                boolean instantiationAuthorized = super.isInstantiationAuthorized(componentClass);
                if (instantiationAuthorized) {
                    return true;
                } else {
                    throw new RestartResponseAtInterceptPageException(HomePage.class);
                }
            }

            @Override
            public boolean isActionAuthorized(Component component, Action action) {
                if (component.getClass().getAnnotation(SignedInUsers.class) != null) {
                    if (Action.ENABLE.equals(action) && !MSession.get().isSignedIn()) {
                        return false;
                    }
                }
                return true;
            }
        });
        mountPage(ProfilePage.MOUNT_URL, ProfilePage.class);
    }

    public ApplicationContext getSpringContext() {
        return springContext;
    }

    /**
     * @see org.apache.wicket.Application#getHomePage()
     */
    public Class getHomePage() {
        return HomePage.class;
//        return TestPage3.class;
    }

    @Override
    public Session newSession(org.apache.wicket.request.Request request, org.apache.wicket.request.Response response) {
        MSession mSession = new MSession(request);
        mSession.setLocale(Constants.LOCALE_EN);
        return mSession;
    }

    @Override
    protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
        return MSession.class;
    }

    @Override
    protected Class<? extends WebPage> getSignInPageClass() {
        return SignInPage.class;
    }
}