package mdrive.business.helper;

import mdrive.business.model.ModelBean;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.lang.reflect.Field;

/**
 * Created by IntelliJ IDEA.
 * User: Elena
 * Date: 31.05.11
 * Time: 9:05
 * To change this template use File | Settings | File Templates.
 */
public class ToStringModelBeanHelper {

    public static String toString(final ModelBean modelBean) {
        return new ReflectionToStringBuilder(modelBean) {
            @Override
            protected Object getValue(Field field) throws IllegalArgumentException, IllegalAccessException {
                //check if field implements ModelBean interface
                if (ModelBean.class.isAssignableFrom(field.getType())) {
                    //return ID only  (for lazy loading)
                    ModelBean fieldModelBean = (ModelBean) field.get(modelBean);
                    if (fieldModelBean != null) {
                        return fieldModelBean.getId();
                    } else {
                        return null;
                    }
                } else {
                    return super.getValue(field);
                }
            }
        }.toString();
    }
}
