package mdrive.page;

import mdrive.page.go.GoBidPanel;
import mdrive.page.lift.LiftPanel;
import org.apache.wicket.extensions.breadcrumb.IBreadCrumbModel;
import org.apache.wicket.extensions.breadcrumb.panel.BreadCrumbPanel;
import org.apache.wicket.extensions.breadcrumb.panel.BreadCrumbPanelLink;
import org.apache.wicket.model.StringResourceModel;

/**
 * User: andrey.osipov
 */
public class HomePanel extends BreadCrumbPanel {

    /**
     * Construct.
     *
     * @param id
     * @param breadCrumbModel
     */
    public HomePanel(final String id, final IBreadCrumbModel breadCrumbModel) {
        super(id, breadCrumbModel);

        add(new BreadCrumbPanelLink("goLink", this, GoBidPanel.class));
        add(new BreadCrumbPanelLink("liftLink", this, LiftPanel.class));
        
    }

    /**
     * @see org.apache.wicket.extensions.breadcrumb.IBreadCrumbParticipant#getTitle()
     */
    public String getTitle() {
        return getString("title");
    }
}