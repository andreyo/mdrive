package mdrive.component.editor;

import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.IModel;

/**
 * Created by IntelliJ IDEA.
 * User: Elena
 * Date: 15.04.12
 * Time: 12:45
 * To change this template use File | Settings | File Templates.
 */
public class PropertyEditorTextArea<T> extends PropertyEditor<T> {

    public PropertyEditorTextArea(String id, IModel<T> model) {
        super(id, model);
    }

    @Override
    public FormComponent<T> getInputControl() {
        return new TextArea<T>(INPUT_CONTROL_ID);
    }
}
