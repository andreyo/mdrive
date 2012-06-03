package mdrive.page.lift.filter;

import org.apache.wicket.extensions.ajax.markup.html.tabs.AjaxTabbedPanel;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.ResourceModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Elena
 * Date: 22.04.12
 * Time: 9:19
 * To change this template use File | Settings | File Templates.
 */
public class SelectFilterPanel extends Panel {
    public static final String BY_RADIUS_TAB_RESOURCE_ID = "byRadiusTab";
    public static final String BY_CITY_AREA_TAB_RESOURCE_ID = "byCityAreaTab";

    private boolean initialized;

    public SelectFilterPanel(String id) {
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

    private void init() {
        List<ITab> tabs = new ArrayList<ITab>();
        tabs.add(new AbstractTab(new ResourceModel(BY_RADIUS_TAB_RESOURCE_ID)) {
            @Override
            public WebMarkupContainer getPanel(String panelId) {
                return new ByRadiusFilterPanel(panelId);
            }
        });
        tabs.add(new AbstractTab(new ResourceModel(BY_CITY_AREA_TAB_RESOURCE_ID)) {
            @Override
            public WebMarkupContainer getPanel(String panelId) {
                return new ByCityAreaFilterPanel(panelId);
            }
        });
        add(new AjaxTabbedPanel("filterTabs", tabs));
    }
}
