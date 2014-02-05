package mdrive.page.lift;

import mdrive.business.dao.GoBidDao;
import mdrive.business.model.GoBidBean;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * User: andrey.osipov
 */
public class GoBidDetachableModel extends LoadableDetachableModel<GoBidBean> {

    @SpringBean
    GoBidDao goBidDao;

    {
        Injector.get().inject(this);
    }

    private long id;

    public GoBidDetachableModel(GoBidBean object) {
        super(object);
        this.id = object.getId();
    }

    @Override
    protected GoBidBean load() {
        return goBidDao.findOne(id);
    }
}