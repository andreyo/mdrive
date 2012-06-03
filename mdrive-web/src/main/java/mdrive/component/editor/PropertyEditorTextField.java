package mdrive.component.editor;

import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;

/**
 * Created by IntelliJ IDEA.
 * User: Elena
 * Date: 15.04.12
 * Time: 0:19
 * To change this template use File | Settings | File Templates.
 */
public class PropertyEditorTextField<T> extends PropertyEditor<T> {

    public PropertyEditorTextField(String id, IModel<T> tiModel) {
        super(id, tiModel);
    }

    @Override
    public FormComponent<T> getInputControl() {
        return new TextField<T>(INPUT_CONTROL_ID);
    }
}
