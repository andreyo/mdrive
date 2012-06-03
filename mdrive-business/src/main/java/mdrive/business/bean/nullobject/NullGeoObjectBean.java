package mdrive.business.bean.nullobject;

import mdrive.business.bean.CoordinatesBean;
import mdrive.business.bean.GeoObjectBean;
import mdrive.business.bean.GeoObjectTypeBean;
import mdrive.business.bean.I18NameBean;

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
    public I18NameBean getObjectI18Name() {
        return NullI18NameBean.instance;
    }

    @Override
    public GeoObjectBean getParentGeoObjectBean() {
        return instance;
    }
}