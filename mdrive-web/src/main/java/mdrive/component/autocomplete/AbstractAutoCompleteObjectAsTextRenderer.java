package mdrive.component.autocomplete;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AbstractAutoCompleteRenderer;
import org.apache.wicket.request.Response;

/**
 * Created by IntelliJ IDEA.
 * User: Elena
 * Date: 08.01.12
 * Time: 12:55
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractAutoCompleteObjectAsTextRenderer<T> extends AbstractAutoCompleteRenderer<T> {

    private Component selectedObjectIdFieldReference;

    public AbstractAutoCompleteObjectAsTextRenderer(Component selectedObjectIdField) {
        selectedObjectIdFieldReference = selectedObjectIdField;
    }

    @Override
    protected void renderChoice(T object, Response response, String criteria) {
        response.write(getTextValue(object));
    }

    @Override
    protected CharSequence getOnSelectJavaScriptExpression(final T item) {
        final StringBuilder js = new StringBuilder();
        js.append("var el = wicketGet('" + selectedObjectIdFieldReference.getMarkupId() + "'); el.value ='" + getIdValueForObject(item)+ "'; el.onchange();");
        js.append("input");
        return js.toString();
    }

    public abstract String getIdValueForObject(T object);
}