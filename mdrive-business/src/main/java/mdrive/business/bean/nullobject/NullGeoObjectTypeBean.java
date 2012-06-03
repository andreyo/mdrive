package mdrive.business.bean.nullobject;

import mdrive.business.bean.GeoObjectTypeBean;

/**
 * User: andrey.osipov
 * Date: 2/14/12
 * Time: 1:50 PM
 */
public class NullGeoObjectTypeBean extends GeoObjectTypeBean {
    public static final NullGeoObjectTypeBean instance = new NullGeoObjectTypeBean();

    private NullGeoObjectTypeBean() {
    }
}