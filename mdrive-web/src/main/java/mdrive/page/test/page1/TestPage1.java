package mdrive.page.test.page1;

import org.apache.wicket.extensions.markup.html.form.select.IOptionRenderer;
import org.apache.wicket.extensions.markup.html.form.select.Select;
import org.apache.wicket.extensions.markup.html.form.select.SelectOptions;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.ListModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Multi-Select with ListModel
 * <p/>
 * ListView
 */
public class TestPage1 extends WebPage {

    public static final List<String> choices = Arrays.asList("one", "two", "three", "four", "five");

    private List<String> model = new ArrayList<String>();

    public TestPage1() {
        Form form = new Form("form");
        add(form);
        Select select = new Select("select", new ListModel(model));
        SelectOptions<String> options = new SelectOptions<String>("options", choices, new IOptionRenderer<String>() {
            @Override
            public String getDisplayValue(String object) {
                return object;
            }

            @Override
            public IModel<String> getModel(String value) {
                return Model.of(value);
            }
        });
        select.add(options);
        form.add(select);

        ListView<String> listView = new ListView<String>("listView", model) {
            @Override
            protected void populateItem(ListItem listItem) {
                listItem.add(new Label("value1", (String) listItem.getModelObject()));
            }
        };
        add(listView);
    }
}
