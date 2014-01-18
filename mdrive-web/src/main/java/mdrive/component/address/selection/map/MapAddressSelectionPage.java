package mdrive.component.address.selection.map;

/**
 * Created by IntelliJ IDEA.
 * User: Elena
 * Date: 01.04.12
 * Time: 22:22
 * To change this template use File | Settings | File Templates.
 */

import mdrive.business.model.GeoObjectBean;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.HiddenField;
import org.apache.wicket.model.Model;

/**
 * Page is created, because maps javascript, didn't work in panel
 */
public class MapAddressSelectionPage extends WebPage {
    String hiddenModel;

    public MapAddressSelectionPage() {
        add(new MapAddressSelectionPanel("panel", new Model<GeoObjectBean>()));
        final HiddenField myHidden = new HiddenField<String>("myHidden", new Model<String>() {
            @Override
            public String getObject() {
                return hiddenModel;
            }

            @Override
            public void setObject(String object) {
                hiddenModel = object;
            }
        });
        myHidden.add(new AjaxFormComponentUpdatingBehavior("onchange") {

            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                target.appendJavaScript(" alert('" + hiddenModel + "');");
                //TODO: show buildings in radius
                //target.appendJavaScript(" alert('" + gson + "');");
            }

        });
        add(myHidden);
    }
}
