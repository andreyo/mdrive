package mdrive.page.lift;

import mdrive.app.MApplication;
import mdrive.business.bean.GeoObjectBean;
import mdrive.business.bean.GoBidBean;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;

import java.util.Iterator;

/**
 * User: andrey.osipov
 */
public class LiftPanelDataProvider extends SortableDataProvider<GoBidBean> {

    private GeoObjectBean geoObjectBean;

    public LiftPanelDataProvider(GeoObjectBean geoObjectBean) {
        this.geoObjectBean = geoObjectBean;
    }

    @Override
    public Iterator<GoBidBean> iterator(int first, int count) {
        SortParam sp = getSort();
        //TODO: Add sort parameter, fist, count
        return MApplication.get().getGoBidDAO()
                .getBidsByGeoObjectCoordinates(geoObjectBean).iterator();
    }

    @Override
    public int size() {
        return MApplication.get().getGoBidDAO().findAll().size();
    }

    @Override
    public IModel<GoBidBean> model(GoBidBean object) {
        return new GoBidDetachableModel(object);
    }
}