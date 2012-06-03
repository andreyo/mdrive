package mdrive.page.lift;

import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.model.IModel;

/**
 * User: Elena
 * <p/>
 * This panel allows to search GoBids in specific area (MyLocationPanel will provide
 * actual location)
 * <p/>
 * 1) Search by radius
 * 2) Search in predefined city-areas
 * 3) Create own predefined areas (and save)
 * <p/>
 * in all cases a resulting map can be viewed
 */
public class LiftSearchPanel extends FormComponentPanel {

    public LiftSearchPanel(String id) {
        super(id);
    }

    public LiftSearchPanel(String id, IModel iModel) {
        super(id, iModel);
    }
}