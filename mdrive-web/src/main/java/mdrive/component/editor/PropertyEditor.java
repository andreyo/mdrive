package mdrive.component.editor;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.markup.html.panel.ComponentFeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.string.Strings;

/**
 * Created by IntelliJ IDEA.
 * User: Elena
 * Date: 15.04.12
 * Time: 0:00
 */
public abstract class PropertyEditor<T> extends FormComponentPanel<T> {
    static final String INPUT_CONTROL_ID = "inputControl";
    public static final String LABEL_ID = "label";
    public static final String FEEDBACK_ID = "feedback";

    private boolean initialized;

    public PropertyEditor(String id, IModel<T> model) {
        super(id, model);
    }

    @Override
    protected void onBeforeRender() {
        super.onBeforeRender();
        if (initialized) {
            return;
        }
        initialized = true;
        IModel<String> labelModel = getLabel();
        if (labelModel == null || Strings.isEmpty(labelModel.getObject())) {
            labelModel = new Model<String>("empty-label");
        }
        add(new Label(LABEL_ID, labelModel));
        FormComponent inputControl = getInputControl();
        add(inputControl);
        add(new ComponentFeedbackPanel(FEEDBACK_ID, PropertyEditor.this));
    }

    public abstract FormComponent<T> getInputControl();
}
