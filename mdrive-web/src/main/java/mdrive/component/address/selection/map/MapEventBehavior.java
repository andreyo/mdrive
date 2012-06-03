package mdrive.component.address.selection.map;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderResponse;

/**
 * User: andrey.osipov
 * Date: 4/11/12
 * Time: 11:51 AM
 */

/**
 * Receives ajax calls from Map
 */
public class MapEventBehavior extends AjaxEventBehavior {

    public MapEventBehavior(String event) {
        super(event);
    }

    @Override
    public void renderHead(Component component, IHeaderResponse response) {
        super.renderHead(component, response);
    }

    @Override
    protected void onEvent(AjaxRequestTarget target) {
    }
}
