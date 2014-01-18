package mdrive.page;

import mdrive.app.MApplication;
import mdrive.app.MSession;
import mdrive.business.service.DBUnitDataLoader;
import mdrive.page.settings.DeveloperSettingsPanel;
import mdrive.page.settings.SettingsPanel;
import mdrive.page.signin.CustomSignInPanel;
import org.apache.log4j.Logger;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.extensions.breadcrumb.BreadCrumbBar;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.ComponentFeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * User: andrey.osipov
 */
public class HomePage extends WebPage {

    private static final long serialVersionUID = 1L;
    private static final String APPLICATION_PROPERTIES = "app.properties";
    private static final String VERSION_PROPERTY = "app.version";
    public static final String HOME_PANEL_ID = "homePanel";

    private String APP_VERSION;

    private static final Logger LOG = Logger.getLogger(HomePage.class);

    private Panel homePanel;

    private SettingsAjaxLink settingsLink;
    private SettingsPanel settingsPanel;
    private static boolean isSettingsPanelVisible;

    private SignInAjaxLink signInAjaxLink;
    private SignOutAjaxLink signOutAjaxLink;

    private DeveloperSettingsAjaxLink developerSettingsLink;
    private DeveloperSettingsPanel developerSettingsPanel;
    private static boolean isDeveloperSettingsPanelVisible;

    private boolean initialized;

    @SpringBean
    DBUnitDataLoader dbUnitDataLoader;

    public HomePage() {
    }

    @Override
    protected void onBeforeRender() {
        super.onBeforeRender();
        if (initialized) {
            return;
        }
        initialized = true;
        setOutputMarkupId(true);
        add(new Label("pageTitle", getString("pageTitle")));

        BreadCrumbBar breadCrumbBar = new BreadCrumbBar("breadCrumbBar");
        add(breadCrumbBar);

        add(new ComponentFeedbackPanel("feedback", HomePage.this));

        homePanel = new HomePanel(HOME_PANEL_ID, breadCrumbBar);
        homePanel.setOutputMarkupId(true);
        add(homePanel);
        //Settings
        settingsLink = new SettingsAjaxLink("settingsLink");
        settingsLink.add(initSettingsLinkLabel("settingsLinkLabel"));
        settingsLink.setOutputMarkupId(true);
        add(settingsLink);
        settingsPanel = initSettingsPanel("settingsPanel");
        settingsPanel.setOutputMarkupPlaceholderTag(true);
        add(settingsPanel);

        //SignIn SignOut links
        signInAjaxLink = new SignInAjaxLink("signInLink");
        add(signInAjaxLink);
        Label loggedInUserLabel = new Label("loggedInUserLabel", new Model<String>() {
            @Override
            public String getObject() {
                return MSession.get().getUser();
            }
        }) {

            @Override
            public boolean isVisible() {
                return isSignedIn();
            }
        };
        loggedInUserLabel.setOutputMarkupId(true);
        add(loggedInUserLabel);
        signOutAjaxLink = new SignOutAjaxLink("signOutLink");
        add(signOutAjaxLink);

        //DeveloperSettings
        developerSettingsLink = new DeveloperSettingsAjaxLink("developerSettingsLink");
        developerSettingsLink.add(initDeveloperSettingsLinkLabel("developerSettingsLinkLabel"));
        developerSettingsLink.setOutputMarkupId(true);
        add(developerSettingsLink);
        developerSettingsPanel = initDeveloperSettingsPanel("developerSettingsPanel");
        developerSettingsPanel.setOutputMarkupPlaceholderTag(true);
        add(developerSettingsPanel);

        add(new Label("appVersion", APP_VERSION = getAppVersion()));
        breadCrumbBar.setActive((HomePanel) homePanel);
    }

    private Label initSettingsLinkLabel(String componentId) {
        Label label = new Label(componentId, new Model<String>() {
            @Override
            public String getObject() {
                if (isSettingsPanelVisible) {
                    return getString("settingsLinkCloseLabel");
                } else {
                    return getString("settingsLinkLabel");
                }
            }
        });
        return label;
    }

    private SettingsPanel initSettingsPanel(String componentId) {
        SettingsPanel panel = new SettingsPanel(componentId) {
            @Override
            public boolean isVisible() {
                return isSettingsPanelVisible;
            }

            @Override
            public void doAjaxRefreshAfterLocaleUpdated(AjaxRequestTarget target) {
                target.add(HomePage.this);
            }
        };
        return panel;
    }

    private Label initDeveloperSettingsLinkLabel(String componentId) {
        Label label = new Label(componentId, new Model<String>() {
            @Override
            public String getObject() {
                if (isDeveloperSettingsPanelVisible) {
                    return getString("developerSettingsLinkCloseLabel");
                } else {
                    return getString("developerSettingsLinkLabel");
                }
            }
        });
        return label;
    }

    private DeveloperSettingsPanel initDeveloperSettingsPanel(String componentId) {
        DeveloperSettingsPanel panel = new DeveloperSettingsPanel(componentId) {
            @Override
            public boolean isVisible() {
                return isDeveloperSettingsPanelVisible;
            }

            @Override
            public void doLoadTestData() {
                try {
                    dbUnitDataLoader.initTestDataCsv();
                } catch (Exception e) {
                    error(e);
                }
            }
        };
        return panel;
    }

    class SettingsAjaxLink extends AjaxFallbackLink {
        SettingsAjaxLink(String id) {
            super(id);
        }

        @Override
        public void onClick(AjaxRequestTarget ajaxRequestTarget) {
            if (ajaxRequestTarget != null) {
                isSettingsPanelVisible = !isSettingsPanelVisible;
                ajaxRequestTarget.add(settingsLink);
                ajaxRequestTarget.add(settingsPanel);
            }
        }

    }

    class SignInAjaxLink extends AjaxFallbackLink {
        SignInAjaxLink(String id) {
            super(id);
            setOutputMarkupId(true);
        }

        @Override
        public void onClick(AjaxRequestTarget ajaxRequestTarget) {
            if (ajaxRequestTarget != null) {
                HomePage.this.remove(homePanel);
                homePanel = new CustomSignInPanel(HOME_PANEL_ID, false);
                HomePage.this.add(homePanel);
                ajaxRequestTarget.add(HomePage.this);
            }
        }

        @Override
        public boolean isVisible() {
            return !isSignedIn();
        }
    }

    class SignOutAjaxLink extends AjaxFallbackLink {
        SignOutAjaxLink(String id) {
            super(id);
            setOutputMarkupId(true);
        }

        @Override
        public void onClick(AjaxRequestTarget ajaxRequestTarget) {
            if (ajaxRequestTarget != null) {
                ((AuthenticatedWebSession) getSession()).invalidate();
                ajaxRequestTarget.add(HomePage.this);
            }
        }

        @Override
        public boolean isVisible() {
            return isSignedIn();
        }
    }

    class DeveloperSettingsAjaxLink extends AjaxFallbackLink {
        DeveloperSettingsAjaxLink(String id) {
            super(id);
        }

        @Override
        public void onClick(AjaxRequestTarget ajaxRequestTarget) {
            if (ajaxRequestTarget != null) {
                isDeveloperSettingsPanelVisible = !isDeveloperSettingsPanelVisible;
                ajaxRequestTarget.add(developerSettingsLink);
                ajaxRequestTarget.add(developerSettingsPanel);
            }
        }

    }

    public String getAppVersion() {
        Properties properties = new Properties();
        InputStream inputStream = HomePage.class.getClassLoader().getResourceAsStream(APPLICATION_PROPERTIES);
        try {
            properties.load(inputStream);
            return properties.getProperty(VERSION_PROPERTY);
        } catch (IOException e) {
            LOG.warn("can't read version");
        }
        return "n/a";
    }

    public boolean isSignedIn() {
        return ((AuthenticatedWebSession) getSession()).isSignedIn();
    }

}