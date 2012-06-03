package mdrive.page.settings;

import mdrive.app.MApplication;
import mdrive.business.logic.bots.PassengerBot;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.ComponentFeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

/**
 * Created by IntelliJ IDEA.
 * User: Elena
 * Date: 12.03.11
 * Time: 9:40
 * To change this template use File | Settings | File Templates.
 */
//@AuthorizeInstantiation("ADMIN")
public abstract class DeveloperSettingsPanel extends Panel {

    public DeveloperSettingsPanel(final String id) {
        super(id);
        add(new InputForm("inputForm"));
    }

    private class InputForm extends Form {

        private PassengerBotLink passengerBotLink;

        InputForm(String id) {
            super(id);
            add(new ComponentFeedbackPanel("feedback", DeveloperSettingsPanel.this));
            add(new LoadTestDataLink("loadTestDataLink"));
            passengerBotLink = new PassengerBotLink("passengerBotLink");
            passengerBotLink.add(initSettingsLinkLabel("passengerBotLinkLabel"));
            passengerBotLink.setOutputMarkupId(true);
            add(passengerBotLink);
        }

        //TODO: add animation, it's not clear when it finishes
        private class LoadTestDataLink extends AjaxFallbackLink {
            LoadTestDataLink(String id) {
                super(id);
            }

            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                if (ajaxRequestTarget != null) {
                    doLoadTestData();
                    info("Test data loaded!");
                    ajaxRequestTarget.add(DeveloperSettingsPanel.this);
                }
            }
        }

        private Label initSettingsLinkLabel(String componentId) {
            Label label = new Label(componentId, new Model<String>() {
                @Override
                public String getObject() {
                    if (getPassengerBot().isActive()) {
                        return getString("passengerBotLinkStopLabel");
                    } else {
                        return getString("passengerBotLinkStartLabel");
                    }
                }
            });
            return label;
        }

        class PassengerBotLink extends AjaxFallbackLink {
            PassengerBotLink(String id) {
                super(id);
            }

            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                getPassengerBot().setActive(!getPassengerBot().isActive());
                if (ajaxRequestTarget != null) {
                    ajaxRequestTarget.add(passengerBotLink);
                }
            }

        }
    }

    public abstract void doLoadTestData();

    private PassengerBot getPassengerBot() {
        return MApplication.get().getPassengerBot();
    }

}
