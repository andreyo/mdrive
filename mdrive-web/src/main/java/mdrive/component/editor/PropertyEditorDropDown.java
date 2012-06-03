package mdrive.component.editor;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.IModel;

/**
 * Created by IntelliJ IDEA.
 * User: Elena
 * Date: 15.04.12
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */
public class PropertyEditorDropDown<T> extends PropertyEditor<T> {

    public PropertyEditorDropDown(String id, IModel<T> model) {
        super(id, model);
    }

    @Override
    public FormComponent<T> getInputControl() {
        return new DropDownChoice<T>(INPUT_CONTROL_ID);
    }
}
