package mdrive.page.test.page3;

import mdrive.component.link.TogglableAjaxLink;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.AbstractReadOnlyModel;

/**
 * Created by IntelliJ IDEA.
 * User: Elena
 * Date: 01.04.12
 * Time: 9:22
 * To change this template use File | Settings | File Templates.
 */
public class TestPage3 extends WebPage {
    boolean initialized;
    Label discountsLabel;

    public TestPage3() {
        setOutputMarkupId(true);
    }

    @Override
    protected void onBeforeRender() {
        super.onBeforeRender();
        if (initialized) {
            return;
        }
        initialized = true;

        TogglableAjaxLink togglableAjaxLink = new TogglableAjaxLink("toggleLink", new AbstractReadOnlyModel<String>() {
            @Override
            public String getObject() {
                return "Switch Me Off";
            }
        }, new AbstractReadOnlyModel<String>() {
            @Override
            public String getObject() {
                return "Switch Me On";
            }
        }, true
        ) {
            @Override
            public void onClick(AjaxRequestTarget target) {
                discountsLabel.setVisible(!discountsLabel.isVisible());
                target.add(TestPage3.this);
            }
        };

        add(togglableAjaxLink);

        discountsLabel = new Label("discounts", "Discounts Label");

        add(discountsLabel);
    }
}