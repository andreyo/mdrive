package mdrive.page.lift;

import mdrive.app.MSession;
import mdrive.business.dao.GeoObjectDao;
import mdrive.business.model.GeoObjectBean;
import mdrive.component.autocompletegeo.AutoCompleteStreetBuildingPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.markup.html.panel.ComponentFeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * User: Elena
 * <p/>
 * 1) Select location by Street + Building
 * 2) Select location using map
 * 3) Get coordinates automatically by javascript-browser
 * <p/>
 * By default MyLocationPanel is ajax-editable Label
 * "your location is: Unknown"
 * "your location is: Kikvidze 11"
 * <p/>
 * location must be always represented as "Street Building" (coordinates will be translated
 * to nearest building)
 * <p/>
 * if javascript-browser location works, location will be automatically recalculated
 */
public class MyLocationPanel extends FormComponentPanel {
    public static final String LOCATION_LINK_ID = "locationLink";
    public static final String LOCATION_SELECTION_PANEL_ID = "locationSelectionPanel";
    public static final String LOCATION_UNKNOWN_PROPERTY = "locationUnknown";
    private WebMarkupContainer locationSelectionPanel;
    private AutoCompleteStreetBuildingPanel autoCompleteStreetBuildingPanel;
    
    @SpringBean
    GeoObjectDao geoObjectDao;

    public MyLocationPanel(String id) {
        super(id);
        init();
    }

    public MyLocationPanel(String id, IModel iModel) {
        super(id, iModel);
        init();
    }

    private void init() {
        setOutputMarkupId(true);
        add(new ComponentFeedbackPanel("feedback", MyLocationPanel.this));
        locationSelectionPanel = new WebMarkupContainer(LOCATION_SELECTION_PANEL_ID);
        locationSelectionPanel.setOutputMarkupId(true);

        final AjaxFallbackLink locationLink = new AjaxFallbackLink(LOCATION_LINK_ID) {
            @Override
            public void onClick(AjaxRequestTarget target) {
                locationSelectionPanel.setVisible(!locationSelectionPanel.isVisible());
                target.add(MyLocationPanel.this);
            }
        };
        locationLink.setOutputMarkupId(true);
        Label locationLinkLabel = new Label("locationLinkLabel", new PropertyModel(this, "currentLocationLinkLabel"));
        locationLink.add(locationLinkLabel);
        add(locationLink);
        //
        locationSelectionPanel.setVisible(false);
        add(locationSelectionPanel);
        autoCompleteStreetBuildingPanel = new AutoCompleteStreetBuildingPanel("currentAddress") {

            @Override
            public void onStreetUpdated(AjaxRequestTarget target) {
                MSession.get().setUserLocationStreetId(this.getSelectedStreetId());
                target.add(locationLink);
            }

            @Override
            public void onBuildingUpdated(AjaxRequestTarget target) {
                MSession.get().setUserLocationBuildingId(this.getSelectedBuildingId());
                target.add(locationLink);
            }

        };
        locationSelectionPanel.add(autoCompleteStreetBuildingPanel);
    }

    public String getCurrentLocationLinkLabel() {
        GeoObjectBean streetBean = geoObjectDao
                .getFullGeoObjectBeanById(MSession.get().getUserLocationStreetId());
        String streetName = streetBean.getObjectI18Name().getValue();

        GeoObjectBean buildingBean = geoObjectDao
                .getFullGeoObjectBeanById(MSession.get().getUserLocationBuildingId());
        String buildingName = buildingBean.getObjectI18Name().getValue();

        if (streetName == null && buildingName == null) {
            return getString(LOCATION_UNKNOWN_PROPERTY);
        }
        return streetName + " " + buildingName;
    }
}