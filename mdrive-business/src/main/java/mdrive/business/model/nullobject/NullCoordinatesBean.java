package mdrive.business.model.nullobject;

import mdrive.business.model.CoordinatesBean;

/**
 * User: andrey.osipov
 * Date: 2/14/12
 * Time: 1:49 PM
 */
public class NullCoordinatesBean extends CoordinatesBean {
    public static final NullCoordinatesBean instance = new NullCoordinatesBean();

    private NullCoordinatesBean() {
    }
}
