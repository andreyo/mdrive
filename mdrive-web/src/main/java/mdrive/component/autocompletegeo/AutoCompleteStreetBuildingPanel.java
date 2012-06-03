package mdrive.component.autocompletegeo;

import mdrive.app.MApplication;
import mdrive.business.bean.GeoObjectBean;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Elena
 * Date: 14.01.12
 * Time: 14:27
 * To change this template use File | Settings | File Templates.
 */
public class AutoCompleteStreetBuildingPanel extends FormComponentPanel<Long> {
    private static Integer AUTOCOMPLETE_ITEMS_LIMIT = 10;

    private AutoCompleteGeoObjectTextField streetAutoCompleteGeoObjectTextField;
    private AutoCompleteGeoObjectTextField buildingAutoCompleteGeoObjectTextField;

    private boolean initialized;

    public AutoCompleteStreetBuildingPanel(String id) {
        this(id, new Model<Long>());
    }

    /**
     * Model - is building Id
     *
     * @param id
     * @param buildingIdModel
     */
    public AutoCompleteStreetBuildingPanel(String id, IModel<Long> buildingIdModel) {
        super(id, buildingIdModel);
    }

    @Override
    protected void onBeforeRender() {
        super.onBeforeRender();
        if (initialized) {
            //if model was changed - update controls
            Long newBuildingId = getModelObject();
            if (newBuildingId != null && !newBuildingId
                    .equals(buildingAutoCompleteGeoObjectTextField.getSelectedObjectId())) {
                GeoObjectBean buildingGeoObject = MApplication.get().getGeoObjectDAO()
                        .getFullGeoObjectBeanById(newBuildingId);
                if (buildingGeoObject == null) {
                    error("invalid building id: " + newBuildingId);
                    return;
                }
                buildingAutoCompleteGeoObjectTextField
                        .setSearchText(buildingGeoObject.getObjectI18Name().getValue(getLocale()));
                buildingAutoCompleteGeoObjectTextField.setSelectedObjectId(newBuildingId);
                //if building has another street - change it as well
                Long newStreetId = buildingGeoObject.getParentGeoObjectBean().getId();
                if (!newStreetId.equals(streetAutoCompleteGeoObjectTextField.getSelectedObjectId())) {
                    GeoObjectBean streetGeoObject = MApplication.get().getGeoObjectDAO()
                            .getFullGeoObjectBeanById(newStreetId);
                    if (streetGeoObject == null) {
                        error("invalid street id: " + newStreetId);
                        return;
                    }
                    streetAutoCompleteGeoObjectTextField
                            .setSearchText(streetGeoObject.getObjectI18Name().getValue(getLocale()));
                    streetAutoCompleteGeoObjectTextField.setSelectedObjectId(newStreetId);
                }
            }
            return;
        }
        initialized = true;
        init();
    }

    private void init() {
        //TODO: add gray hints inside input field (when property "hint" exists, then display)
        streetAutoCompleteGeoObjectTextField = new AutoCompleteGeoObjectTextField("street") {
            @Override
            public Iterator<GeoObjectBean> getChoices(String input) {
                List<GeoObjectBean> choices = MApplication.get().getGeoObjectDAO()
                        .getStreetGeoObjectsStartingWith(input, getLocale(), AUTOCOMPLETE_ITEMS_LIMIT);
                return choices.iterator();
            }

            @Override
            public void onUpdate(AjaxRequestTarget target) {
                AutoCompleteStreetBuildingPanel.this.onStreetUpdated(target);
            }
        };
        add(streetAutoCompleteGeoObjectTextField);

        buildingAutoCompleteGeoObjectTextField = new AutoCompleteGeoObjectTextField("building") {
            @Override
            public Iterator<GeoObjectBean> getChoices(String input) {
                List<GeoObjectBean> choices = MApplication.get().getGeoObjectDAO().getBuildingGeoObjectsStartingWith(
                        input,
                        getLocale(),
                        getSelectedStreetId(),
                        AUTOCOMPLETE_ITEMS_LIMIT);
                return choices.iterator();
            }

            @Override
            public void onUpdate(AjaxRequestTarget target) {
                AutoCompleteStreetBuildingPanel.this.onBuildingUpdated(target);
            }
        };
        add(buildingAutoCompleteGeoObjectTextField);
    }

    @Override
    protected void convertInput() {
        setConvertedInput(getSelectedBuildingId());
    }

    public void onStreetUpdated(AjaxRequestTarget target) {
        //onUpdate call-back can be added here
    }

    public void onBuildingUpdated(AjaxRequestTarget target) {
        //onUpdate call-back can be added here
    }

    public final Long getSelectedStreetId() {
        return streetAutoCompleteGeoObjectTextField.getSelectedObjectId();
    }

    public final Long getSelectedBuildingId() {
        return buildingAutoCompleteGeoObjectTextField.getSelectedObjectId();
    }

}