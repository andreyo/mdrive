package mdrive.page.go;

import mdrive.app.MSession;
import mdrive.business.dao.GeoObjectDAO;
import mdrive.business.dao.GoBidDAO;
import mdrive.business.dao.UserTypeDAO;
import mdrive.business.model.GoBidBean;
import mdrive.business.model.UserBean;
import mdrive.component.address.selection.AddressSelectionComponent;
import mdrive.component.editor.PropertyEditorDropDown;
import mdrive.component.editor.PropertyEditorTextArea;
import mdrive.component.editor.PropertyEditorTextField;
import mdrive.component.link.TogglableAjaxLink;
import mdrive.page.go.replies.GoRepliesPanel;
import org.apache.log4j.Logger;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.breadcrumb.IBreadCrumbModel;
import org.apache.wicket.extensions.breadcrumb.panel.BreadCrumbPanel;
import org.apache.wicket.extensions.breadcrumb.panel.IBreadCrumbPanelFactory;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.ComponentFeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.LinkedList;
import java.util.List;

/**
 * User: andrey.osipov
 */
public class GoBidPanel extends BreadCrumbPanel {
    private static final Logger log = Logger.getLogger(GoBidPanel.class);
    public static final String INPUT_FORM_ID = "form";
    public static final String EDITOR_ID = "editor";

    @SpringBean
    UserTypeDAO userTypeDAO;

    @SpringBean
    GeoObjectDAO geoObjectDAO;

    @SpringBean
    GoBidDAO goBidDAO;

    private boolean initialized;
    private Form form;
    private AddressSelectionComponent fromAddressSelectionComponent;
    private AddressSelectionComponent toAddressSelectionComponent;
    private AddressSelectionComponent secondAddressSelectionComponent;

    /**
     * for unit test
     */
    public GoBidPanel(String id) {
        this(id, null);
    }

    /**
     * Construct.
     *
     * @param id
     * @param breadCrumbModel
     */
    public GoBidPanel(final String id, final IBreadCrumbModel breadCrumbModel) {
        super(id, breadCrumbModel);
    }

    @Override
    protected void onBeforeRender() {
        super.onBeforeRender();
        if (initialized) {
            return;
        }
        initialized = true;
        add(new ComponentFeedbackPanel("feedback", GoBidPanel.this));
        form = new Form(INPUT_FORM_ID);
        form.setOutputMarkupId(true);
        add(form);

        // from
        fromAddressSelectionComponent = new AddressSelectionComponent("fromAddress");
        fromAddressSelectionComponent.setLabel(new ResourceModel("from"));
        fromAddressSelectionComponent.setRequired(true);
        form.add(fromAddressSelectionComponent);
        //to
        toAddressSelectionComponent = new AddressSelectionComponent("toAddress");
        toAddressSelectionComponent.setLabel(new ResourceModel("to"));
        toAddressSelectionComponent.setRequired(true);
        form.add(toAddressSelectionComponent);
        //second
        secondAddressSelectionComponent = new AddressSelectionComponent("secondAddress");
        secondAddressSelectionComponent.setLabel(new ResourceModel("secondTo"));
        secondAddressSelectionComponent.setOutputMarkupId(true);
        secondAddressSelectionComponent.setVisible(false);
        form.add(secondAddressSelectionComponent);

        form.add(new TogglableAjaxLink("toggle2ndAddressLink",
                new StringResourceModel("hide2ndAddress", GoBidPanel.this, null, null),
                new StringResourceModel("show2ndAddress", GoBidPanel.this, null, null),
                false) {
            @Override
            public void onClick(AjaxRequestTarget target) {
                secondAddressSelectionComponent.setVisible(this.isOn());
                target.add(form);
            }
        });
        final TogglableAjaxLink toggleParametersLink = new TogglableAjaxLink("toggleParametersLink",
                new StringResourceModel("hideParameters", GoBidPanel.this, null, null),
                new StringResourceModel("showParameters", GoBidPanel.this, null, null),
                false) {
            @Override
            public void onClick(AjaxRequestTarget target) {
                target.add(form);
            }
        };
        form.add(toggleParametersLink);

        //TODO: do time field as drop-donw, first 3 items are : "nearest", "within 1 hour", "within 2 hours", "specify time"
        final PropertyEditorTextField<Integer> timeField = new PropertyEditorTextField<Integer>(EDITOR_ID,
                new Model<Integer>());
        timeField.setLabel(new ResourceModel("time"));
        final PropertyEditorTextField<Integer> priceField = new PropertyEditorTextField<Integer>(EDITOR_ID,
                new Model<Integer>());
        priceField.setLabel(new ResourceModel("price"));
        final PropertyEditorDropDown<Integer> numberOfPeople = new PropertyEditorDropDown<Integer>(EDITOR_ID,
                new Model<Integer>());
        numberOfPeople.setLabel(new ResourceModel("numberOfPeople"));
        final PropertyEditorDropDown<String> baggage = new PropertyEditorDropDown<String>(EDITOR_ID,
                new Model<String>());
        baggage.setLabel(new ResourceModel("baggage"));
        final PropertyEditorTextArea<String> comment = new PropertyEditorTextArea<String>(EDITOR_ID,
                new Model<String>());
        comment.setLabel(new ResourceModel("comment"));

        List<FormComponent> parametersControlsList = new LinkedList<FormComponent>() {{
            add(timeField);
            add(priceField);
            add(numberOfPeople);
            add(baggage);
            add(comment);
        }};

        ListView<FormComponent> parametersListView = new ListView<FormComponent>("parametersListView",
                parametersControlsList) {
            @Override
            protected void populateItem(ListItem<FormComponent> listItem) {
                FormComponent component = listItem.getModelObject();
                component.setOutputMarkupId(true);
                component.setVisible(toggleParametersLink.isOn());
                listItem.add(component);
            }
        };
        form.add(parametersListView);
        form.add(new SubmitGoBidButton("submitButton"));
    }

    /**
     * @see org.apache.wicket.extensions.breadcrumb.IBreadCrumbParticipant#getTitle()
     */
    public String getTitle() {
        return getString("title");
    }

    class SubmitGoBidButton extends Button {

        SubmitGoBidButton(String id) {
            super(id);
        }

        @Override
        public void onSubmit() {
            GoBidBean goBidBean = new GoBidBean();

            UserBean userBean = new UserBean();
            userBean.setUserName(MSession.get().getUser());
            userBean.setUserTypeBean(userTypeDAO.getPassengerUserTypeBean());
            goBidBean.setUserBean(userBean);

            goBidBean.setFromGeoObjectBean(geoObjectDAO
                    .findOne(fromAddressSelectionComponent.getModelObject()));
            goBidBean.setToGeoObjectBean(geoObjectDAO
                    .findOne(toAddressSelectionComponent.getModelObject()));

            goBidDAO.persist(goBidBean);
            activate(new IBreadCrumbPanelFactory() {
                public BreadCrumbPanel create(String componentId, IBreadCrumbModel breadCrumbModel) {
                    return new GoRepliesPanel(componentId, breadCrumbModel);
                }
            });
        }
    }
}