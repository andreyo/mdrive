package mdrive.component.date;

import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.RangeValidator;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Elena
 * Date: 31.03.12
 * Time: 13:27
 * To change this template use File | Settings | File Templates.
 */
public class CompoundDateTimeField extends FormComponentPanel<Date> {

    private Date date;
    private Integer hours;
    private Integer minutes;

    private DateTextField dateField;
    private TextField<Integer> hoursField;
    private TextField<Integer> minutesField;

    public CompoundDateTimeField(String id, IModel<Date> model) {
        super(id, model);
        add(dateField = new DateTextField("date", new PropertyModel<Date>(this, "date")));
        DatePicker datePicker = new DatePicker();
        datePicker.setShowOnFieldClick(true);
        datePicker.setAutoHide(true);
        dateField.add(datePicker);
        add(hoursField = new TextField<Integer>("hours", new PropertyModel<Integer>(this, "hours")));
        hoursField.add(new RangeValidator<Integer>(0, 24));
        hoursField.setLabel(Model.of("hours"));
        add(minutesField = new TextField<Integer>("minutes", new PropertyModel<Integer>(this, "minutes")));
        minutesField.add(new RangeValidator<Integer>(0, 59));
        minutesField.setLabel(Model.of("minutes"));
    }

    /**
     * propagate from outside-change to nested compoments models
     */
    @Override
    protected void onBeforeRender() {
        super.onBeforeRender();
        date = getModelObject();
        if (date != null) {
            Calendar calendar = Calendar.getInstance(getLocale());
            calendar.setTime(date);
            hours = calendar.get(Calendar.HOUR_OF_DAY);
            minutes = calendar.get(Calendar.MINUTE);
        }
        dateField.setRequired(isRequired());
        hoursField.setRequired(isRequired());
        minutesField.setRequired(isRequired());
    }


    /**
     * from nested component inputs (models are not updated yet) to panel-input (model will be updated after validation)
     */
    @Override
    protected void convertInput() {
        if (dateField.getConvertedInput() != null) {
            Calendar calendar = Calendar.getInstance(getLocale());
            calendar.setTime(dateField.getConvertedInput());
            if (hoursField.getConvertedInput() != null) {
                calendar.set(Calendar.HOUR_OF_DAY, hoursField.getConvertedInput());
            }
            if (minutesField.getConvertedInput() != null) {
                calendar.set(Calendar.MINUTE, minutesField.getConvertedInput());
            }
            setConvertedInput(calendar.getTime());
        } else {
            setConvertedInput(null);
        }
    }

    /**
     * for correct error-reporting ${input} in messages
     *
     * @return
     */
    @Override
    public String getInput() {
        return dateField.getInput() + ":" + hoursField.getInput() + ":" + minutesField.getInput();
    }
}