package mdrive.page.test.page2;

import org.apache.wicket.model.IPropertyReflectionAwareModel;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by IntelliJ IDEA.
 * User: Elena
 * Date: 01.04.12
 * Time: 9:44
 * To change this template use File | Settings | File Templates.
 */
public class MyPropertyModel<T> implements IPropertyReflectionAwareModel<T> {

    public MyPropertyModel(T modelObject) {
        if (modelObject == null) {
            return;
        }
        Method[] methods = modelObject.getClass().getMethods();
        for (Method method : methods) {
            System.out.println(method);
        }
    }

    @Override
    public Field getPropertyField() {
        return null;
    }

    @Override
    public Method getPropertyGetter() {
        return null;
    }

    @Override
    public Method getPropertySetter() {
        return null;
    }

    @Override
    public T getObject() {
        return null;
    }

    @Override
    public void setObject(T object) {
    }

    @Override
    public void detach() {
    }
}
