package mdrive.business.model.nullobject;

import mdrive.business.model.CoordinatesBean;
import mdrive.business.model.GeoObjectBean;
import mdrive.business.model.GeoObjectTypeBean;
import mdrive.business.model.I18NameBean;

/**
 * User: andrey.osipov
 * Date: 2/14/12
 * Time: 1:03 PM
 */
public class NullGeoObjectBean extends GeoObjectBean {

    public static final NullGeoObjectBean instance = new NullGeoObjectBean();

    private NullGeoObjectBean() {
    }

    @Override
    public CoordinatesBean getCoordinatesBean() {
        return NullCoordinatesBean.instance;
    }

    @Override
    public GeoObjectTypeBean getGeoObjectTypeBean() {
        return NullGeoObjectTypeBean.instance;
    }

    @Override
    public I18NameBean getName() {
        return NullI18NameBean.instance;
    }

    @Override
    public GeoObjectBean getParentGeoObjectBean() {
        return instance;
    }
}