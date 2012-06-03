package mdrive.component.address.selection;

import mdrive.component.address.selection.map.MapAddressSelectionPage;
import mdrive.component.autocompletegeo.AutoCompleteStreetBuildingPanel;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.markup.html.panel.ComponentFeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * Created by IntelliJ IDEA.
 * User: Elena
 * Date: 01.04.12
 * Time: 18:45
 * To change this template use File | Settings | File Templates.
 */
public class AddressSelectionComponent extends FormComponentPanel<Long> {

    private ModalWindow modalWindow;
    private boolean initialized;

    private AutoCompleteStreetBuildingPanel streetBuildingPanel;

    public AddressSelectionComponent(String id) {
        this(id, new Model<Long>());
    }

    public AddressSelectionComponent(String id, IModel<Long> buildingIdModel) {
        super(id, buildingIdModel);
    }

    @Override
    protected void onBeforeRender() {
        super.onBeforeRender();
        if (initialized) {
            //if model was changed -> propagate it
            streetBuildingPanel.setModelObject(getModelObject());
            return;
        }
        initialized = true;
        init();
    }

    private void init() {
        add(new Label("panelLabel", getLabel()));
        add(new MapAjaxLink("mapLink"));
        add(modalWindow = new MapModalWindow("mapModalWindow"));
        streetBuildingPanel = new AutoCompleteStreetBuildingPanel("addressAutoCompletePanel");
        add(streetBuildingPanel);
        add(new ComponentFeedbackPanel("feedback", AddressSelectionComponent.this));
    }

    @Override
    protected void convertInput() {
        setConvertedInput(streetBuildingPanel.getConvertedInput());
    }

    class MapModalWindow extends ModalWindow {
        MapModalWindow(String id) {
            super(id);
            setPageCreator(new ModalWindow.PageCreator() {
                @Override
                public Page createPage() {
                    //TODO: set parameters (city, etc..)
                    return new MapAddressSelectionPage();
                }
            });
            setCookieName(getId());

            setCloseButtonCallback(new ModalWindow.CloseButtonCallback() {
                public boolean onCloseButtonClicked(AjaxRequestTarget target) {
                    return true;
                }
            });

            setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {
                public void onClose(AjaxRequestTarget target) {
//                    target.add(result);
                }
            });
        }
    }

    class MapAjaxLink extends AjaxFallbackLink {
        MapAjaxLink(String id) {
            super(id);
        }

        @Override
        public void onClick(AjaxRequestTarget ajaxRequestTarget) {
            if (ajaxRequestTarget != null) {
                modalWindow.show(ajaxRequestTarget);
            }
        }
    }
}
