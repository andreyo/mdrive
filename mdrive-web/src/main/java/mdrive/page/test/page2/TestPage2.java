package mdrive.page.test.page2;

import mdrive.business.model.UserBean;
import mdrive.component.date.CompoundDateTimeField;
import org.apache.wicket.extensions.yui.calendar.DateTimeField;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.PropertyModel;

import java.util.Date;

//import mdrive.component.date.CompoundDateTimeField;

/**
 * User: andrey.osipov
 * Date: 3/29/12
 * Time: 3:04 PM
 */
public class TestPage2 extends WebPage {

    public static Date date1;
    public static Date date2;

    private UserBean user;

    public TestPage2() {
        /*add(new DropDownChoice<UserBean>("userDropDown",
                new PropertyModel<UserBean>(this, "user"),
                new ArrayList<UserBean>()));*/
        Form form = new Form("form");
        add(form);
        form.add(new CompoundDateTimeField("date1", new PropertyModel(TestPage2.this, "date1")));
        form.add(new Label("label1", new AbstractReadOnlyModel<Date>() {
            @Override
            public Date getObject() {
                return TestPage2.this.date1;
            }
        }));
        form.add(new DateTimeField("date2", new PropertyModel<Date>(TestPage2.this, "date2")));
        form.add(new Label("label2", new PropertyModel(TestPage2.this, "date2")));
    }
}
