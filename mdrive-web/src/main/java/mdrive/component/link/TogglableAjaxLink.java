package mdrive.component.link;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

/**
 * Created by IntelliJ IDEA.
 * User: Elena
 * Date: 15.04.12
 * Time: 13:12
 * To change this template use File | Settings | File Templates.
 */
public class TogglableAjaxLink extends Panel {
    //multi-threaded
    private volatile boolean isOn;
    private boolean initialized;
    private IModel<String> onLabelResourceModel;
    private IModel<String> offLabelResourceModel;

    public TogglableAjaxLink(String id, IModel<String> onLabelResourceModel, IModel<String> offLabelResourceModel,
                             boolean isOnByDefault) {
        super(id);
        this.onLabelResourceModel = onLabelResourceModel;
        this.offLabelResourceModel = offLabelResourceModel;
        this.isOn = isOnByDefault;
    }

    public IModel<String> getLabel() {
        if (isOn) {
            return onLabelResourceModel;
        }
        return offLabelResourceModel;
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

    private void init() {
        final Label linkLabel = new Label("label", TogglableAjaxLink.this.getLabel());
        linkLabel.setOutputMarkupId(true);

        AjaxLink link = new AjaxLink("link") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                isOn = !isOn;
                linkLabel.setDefaultModel(TogglableAjaxLink.this.getLabel());
                target.add(linkLabel);
                TogglableAjaxLink.this.onClick(target);
            }
        };
        link.add(linkLabel);
        add(link);
    }

    //for overriding
    public void onClick(AjaxRequestTarget target) {
    }

    public boolean isOn() {
        return isOn;
    }
}
