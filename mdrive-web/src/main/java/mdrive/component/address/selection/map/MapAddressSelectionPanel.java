package mdrive.component.address.selection.map;

import mdrive.business.dao.GeoObjectDao;
import mdrive.business.model.GeoObjectBean;
import mdrive.business.type.GeoObjectTypeCode;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.markup.html.internal.HtmlHeaderContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Elena
 * Date: 01.04.12
 * Time: 18:37
 * To change this template use File | Settings | File Templates.
 */
public class MapAddressSelectionPanel extends FormComponentPanel<GeoObjectBean> {

    @SpringBean
    GeoObjectDao geoObjectDao;

    Form form;
    AjaxCheckBox showHideTestBuildingsCheckBox;
    boolean showHideTestBuildings;

    @Override
    public void renderHead(HtmlHeaderContainer container) {
        super.renderHead(container);
    }

    public MapAddressSelectionPanel(String id, IModel<GeoObjectBean> geoObjectBeanIModel) {
        super(id, geoObjectBeanIModel);
    }

    @Override
    protected void onComponentTag(ComponentTag tag) {
        super.onComponentTag(tag);

        //TODO:  write to html 100 (not too much) buildings which we have in DB
        Float latitude = Float.valueOf("50.45351");
        Float longtitude = Float.valueOf("30.516489");
        Float radius = Float.valueOf(2);
        final List<GeoObjectBean> geoObjectsList = geoObjectDao
                .getGeoObjectsByLocation(latitude, longtitude, radius, GeoObjectTypeCode.BUILDING);
    }

    @Override
    protected void onBeforeRender() {
        super.onBeforeRender();

        form = new Form("form");
        add(form);
        //check-box is only for tests/development
        showHideTestBuildingsCheckBox = new AjaxCheckBox("showHideTestBuildingsCheckbox", new Model<Boolean>() {
            @Override
            public Boolean getObject() {
                return showHideTestBuildings;
            }

            @Override
            public void setObject(Boolean object) {
                showHideTestBuildings = object;
            }
        }) {
            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                //show/hide buildings
                if (showHideTestBuildings) {
                    target.appendJavaScript(" showBuildingsOnTheMap();");
                } else {
                    target.appendJavaScript(" hideBuildingsOnTheMap();");
                }
            }
        };
        add(showHideTestBuildingsCheckBox);
    }
}
