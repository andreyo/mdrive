package mdrive.page.settings;

import mdrive.app.MSession;
import mdrive.business.type.Constants;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import java.util.Locale;

/**
 * User: andrey.osipov
 */
//@AuthorizeInstantiation("ADMIN")
//@SignedInUsers
public abstract class SettingsPanel extends Panel {
    private boolean initialized;

    public SettingsPanel(final String id) {
        super(id);
    }

    @Override
    protected void onBeforeRender() {
        super.onBeforeRender();
        if (initialized) {
            return;
        }
        initialized = true;
        init();
    }

    /**
     * @see org.apache.wicket.extensions.breadcrumb.IBreadCrumbParticipant#getTitle()
     */
    public String getTitle() {
        return getString("title");
    }

    private void init() {
        Form form = new Form("form");
        add(form);
        DropDownChoice<Locale> localeDropDownChoice = new DropDownChoice<Locale>("locale", new Model<Locale>() {
            @Override
            public Locale getObject() {
                return MSession.get().getLocale();
            }

            @Override
            public void setObject(Locale object) {
                MSession.get().setLocale(object);
            }
        }, Constants.LOCALES);
        localeDropDownChoice.add(new AjaxFormComponentUpdatingBehavior("onchange") {
            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                doAjaxRefreshAfterLocaleUpdated(target);
            }
        });
        form.add(localeDropDownChoice);
        form.add(new AjaxLink("resetToDefaultLink") {
            @Override
            public void onClick(AjaxRequestTarget target) {
            }
        });
    }

    public abstract void doAjaxRefreshAfterLocaleUpdated(AjaxRequestTarget target);
}