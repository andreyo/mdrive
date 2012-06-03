package mdrive.app;

import mdrive.business.dao.hibernate.GeoObjectDAO;
import mdrive.business.dao.hibernate.GoBidDAO;
import mdrive.business.dao.hibernate.GoReplyDAO;
import mdrive.business.dao.hibernate.I18NameDAO;
import mdrive.business.dao.hibernate.UserTypeDAO;
import mdrive.business.logic.bots.PassengerBot;
import mdrive.business.service.DBUnitDataLoader;
import mdrive.page.HomePage;
import mdrive.page.signin.SignInPage;
import mdrive.page.profile.ProfilePage;
import mdrive.page.test.page2.TestPage2;
import mdrive.page.test.page3.TestPage3;
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
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


public class MApplication extends AuthenticatedWebApplication implements ApplicationContextAware {

    @Autowired
    private transient GoBidDAO goBidDAO;

    @Autowired
    private transient GoReplyDAO goReplyDAO;

    @Autowired
    private transient GeoObjectDAO geoObjectDAO;

    @Autowired
    private transient PassengerBot passengerBot;

    @Autowired
    private transient DBUnitDataLoader dbUnitDataLoader;

    @Autowired
    private transient UserTypeDAO userTypeDAO;

    @Autowired
    private transient I18NameDAO i18NameDAO;

    private transient ApplicationContext applicationContext;

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

        // THIS LINE IS IMPORTANT - IT INSTALLS THE COMPONENT INJECTOR THAT WILL
        // INJECT NEWLY CREATED COMPONENTS WITH THEIR SPRING DEPENDENCIES
        getComponentInstantiationListeners().add(new SpringComponentInjector(this, applicationContext, true));

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


    /**
     * @see org.apache.wicket.Application#getHomePage()
     */
    public Class getHomePage() {
        return HomePage.class;
//        return TestPage3.class;
    }

    @Override
    public Session newSession(org.apache.wicket.request.Request request, org.apache.wicket.request.Response response) {
        return new MSession(request);
    }

    @Override
    protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
        return MSession.class;
    }

    // how to make WicketTester working with Spring described here:
    // http://blog.comsysto.com/2010/06/04/test-driven-development-with-apache-wicket-and-spring-framework/
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    protected Class<? extends WebPage> getSignInPageClass() {
        return SignInPage.class;
    }

    //Getters for Services(DAO) which work in UnitTests

    public GoReplyDAO getGoReplyDAO() {
        return goReplyDAO;
    }

    public GoBidDAO getGoBidDAO() {
        return goBidDAO;
    }

    public GeoObjectDAO getGeoObjectDAO() {
        return geoObjectDAO;
    }

    public PassengerBot getPassengerBot() {
        return passengerBot;
    }

    public DBUnitDataLoader getDbUnitDataLoader() {
        return dbUnitDataLoader;
    }

    public UserTypeDAO getUserTypeDAO() {
        return userTypeDAO;
    }

    public I18NameDAO getI18NameDAO() {
        return i18NameDAO;
    }
}