package mdrive.component.autocompletegeo;

import mdrive.business.model.GeoObjectBean;
import mdrive.component.autocomplete.AutoCompleteObjectTextField;

/**
 * User: andrey.osipov
 * Date: 1/12/12
 * Time: 6:02 PM
 */
public abstract class AutoCompleteGeoObjectTextField extends AutoCompleteObjectTextField<GeoObjectBean> {

    protected AutoCompleteGeoObjectTextField(String id) {
        super(id);
    }

    @Override
    public String getIdValueForObject(GeoObjectBean object) {
        return String.valueOf(object.getId());
    }

    @Override
    public String getTextValue(GeoObjectBean object) {
        return object.getName().getValue(getLocale());
    }
}