package mdrive.page.lift;

import mdrive.app.MApplication;
import mdrive.business.bean.GoBidBean;
import org.apache.wicket.model.LoadableDetachableModel;

/**
 * User: andrey.osipov
 */
public class GoBidDetachableModel extends LoadableDetachableModel<GoBidBean> {

    private long id;

    public GoBidDetachableModel(GoBidBean object) {
        super(object);
        this.id = object.getId();
    }

    @Override
    protected GoBidBean load() {
        return MApplication.get().getGoBidDAO().findById(id);
    }
}