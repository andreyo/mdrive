package mdrive.page.lift;

import mdrive.business.dao.GoBidDao;
import mdrive.business.model.GeoObjectBean;
import mdrive.business.model.GoBidBean;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;

import java.util.Iterator;

/**
 * User: andrey.osipov
 */
public class LiftPanelDataProvider extends SortableDataProvider<GoBidBean> {

    private GoBidDao goBidDao;
    private GeoObjectBean searchInGeoObjectBean;

    public LiftPanelDataProvider(GoBidDao goBidDao, GeoObjectBean searchInGeoObjectBean) {
        this.goBidDao = goBidDao;
        this.searchInGeoObjectBean = searchInGeoObjectBean;
    }

    @Override
    public Iterator<GoBidBean> iterator(int first, int count) {
        SortParam sp = getSort();
        //TODO: Add sort parameter, fist, count
        return goBidDao.getBidsWithinGeoObjectCoordinates(searchInGeoObjectBean).iterator();
    }

    @Override
    public int size() {
        return goBidDao.findAll().size();
    }

    @Override
    public IModel<GoBidBean> model(GoBidBean object) {
        return new GoBidDetachableModel(object);
    }
}