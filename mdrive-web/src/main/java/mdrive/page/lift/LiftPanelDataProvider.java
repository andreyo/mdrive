package mdrive.page.lift;

import mdrive.business.dao.GoBidDAO;
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

    private GoBidDAO goBidDAO;
    private GeoObjectBean geoObjectBean;

    public LiftPanelDataProvider(GoBidDAO goBidDAO, GeoObjectBean geoObjectBean) {
        this.goBidDAO = goBidDAO;
        this.geoObjectBean = geoObjectBean;
    }

    @Override
    public Iterator<GoBidBean> iterator(int first, int count) {
        SortParam sp = getSort();
        //TODO: Add sort parameter, fist, count
        return goBidDAO
                .getBidsByGeoObjectCoordinates(geoObjectBean).iterator();
    }

    @Override
    public int size() {
        return goBidDAO.findAll().size();
    }

    @Override
    public IModel<GoBidBean> model(GoBidBean object) {
        return new GoBidDetachableModel(object);
    }
}