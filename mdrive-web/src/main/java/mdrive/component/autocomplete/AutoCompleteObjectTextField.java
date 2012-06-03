package mdrive.component.autocomplete;

import org.apache.log4j.Logger;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteBehavior;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.IAutoCompleteRenderer;
import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.markup.html.form.HiddenField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: Elena
 * Date: 08.01.12
 * Time: 12:48
 * To change this template use File | Settings | File Templates.
 */

/**
 * Class AutoCompleteObjectTextField is one-stop tool to configure Objectified AutoCompleteTextField
 * Renderer and Behavior are incapsulated, and all necessary methods to configure the Component are delegated to AutoCompleteObjectTextField
 *
 * @param <T>
 */
public abstract class AutoCompleteObjectTextField<T> extends FormComponentPanel<T> {
    private static final long serialVersionUID = 1L;

    private static final Logger log = Logger.getLogger(AutoCompleteObjectTextField.class);
    private boolean initialized;

    private Long selectedObjectId = null;
    private String searchText;

    private AbstractAutoCompleteObjectAsTextRenderer<T> renderer;

    private AutoCompleteBehavior<T> autoCompleteBehavior;

    protected AutoCompleteObjectTextField(String id) {
        this(id, new Model());
    }

    protected AutoCompleteObjectTextField(String id, IModel<T> tiModel) {
        super(id, tiModel);
    }

    @Override
    protected void onBeforeRender() {
        super.onBeforeRender();
        if (initialized) {
            return;
        }
        initialized = true;
        init();
    }

    public void init() {
        setOutputMarkupId(true);
        final HiddenField<String> selectedObjectIdHiddenField = new HiddenField<String>("selectedObjectId",
                new Model<String>() {
                    @Override
                    public String getObject() {
                        return String.valueOf(selectedObjectId);
                    }

                    @Override
                    public void setObject(String object) {
                        selectedObjectId = Long.valueOf(object);
                    }
                });
        selectedObjectIdHiddenField.setOutputMarkupId(true);
        selectedObjectIdHiddenField.add(new AjaxFormComponentUpdatingBehavior("onchange") {
            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                log.debug("Hidden field changed by javascript: " + selectedObjectId);
            }
        });
        add(selectedObjectIdHiddenField);
        final TextField<String> searchTextField = new TextField<String>("searchText", new Model<String>() {
            @Override
            public String getObject() {
                return searchText;
            }

            @Override
            public void setObject(String object) {
                searchText = object;
            }
        });
        //selectedObjectIdHiddenField must be already initialized, before passing it's reference
        renderer = initRenderer(selectedObjectIdHiddenField);
        autoCompleteBehavior = initAutoCompleteBehavior(renderer);


        //make it Autocomplete
        searchTextField.add(autoCompleteBehavior);
        searchTextField.add(new AttributeModifier("autocomplete", "off"));
        //
        searchTextField.add(new AjaxFormComponentUpdatingBehavior("onchange") {
            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                //if input was deleted, update
                if (searchTextField.getModelObject() == null) {
                    selectedObjectId = null;
                }
                target.add(AutoCompleteObjectTextField.this);
                AutoCompleteObjectTextField.this.onUpdate(target);
            }
        });
        add(searchTextField);
    }

    private AbstractAutoCompleteObjectAsTextRenderer<T> initRenderer(Component selectedObjectIdField) {
        return new AbstractAutoCompleteObjectAsTextRenderer<T>(selectedObjectIdField) {
            @Override
            public String getIdValueForObject(T object) {
                return AutoCompleteObjectTextField.this.getIdValueForObject(object);
            }

            @Override
            protected String getTextValue(T object) {
                return AutoCompleteObjectTextField.this.getTextValue(object);
            }
        };
    }

    private AutoCompleteBehavior<T> initAutoCompleteBehavior(IAutoCompleteRenderer<T> renderer) {
        return new AutoCompleteBehavior<T>(renderer) {
            @Override
            protected Iterator<T> getChoices(String input) {
                return AutoCompleteObjectTextField.this.getChoices(input);
            }
        };
    }

    public Long getSelectedObjectId() {
        return selectedObjectId;
    }

    //to propagate changes from outside
    public void setSelectedObjectId(Long selectedObjectId) {
        this.selectedObjectId = selectedObjectId;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public void onUpdate(AjaxRequestTarget target) {
        //onUpdate call-back can be added here
    }

    public abstract Iterator<T> getChoices(String input);

    public abstract String getIdValueForObject(T object);

    public abstract String getTextValue(T object);
}