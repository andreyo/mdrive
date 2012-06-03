package mdrive.page.go.replies;

import mdrive.business.bean.GoReplyBean;
import org.apache.wicket.extensions.breadcrumb.IBreadCrumbModel;
import org.apache.wicket.extensions.breadcrumb.panel.BreadCrumbPanel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.ComponentFeedbackPanel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;

/**
 * User: andrey.osipov
 */
public class GoRepliesPanel extends BreadCrumbPanel {

    private static final int ITEMS_PER_PAGE = 5;

    //WicketTester constructor
    public GoRepliesPanel(String id) {
        this(id, null);
    }

    /**
     * Construct.
     *
     * @param id
     * @param breadCrumbModel
     */
    public GoRepliesPanel(final String id, final IBreadCrumbModel breadCrumbModel) {
        super(id, breadCrumbModel);
        add(new ComponentFeedbackPanel("feedback", GoRepliesPanel.this));
        Form form = new Form("inputForm");
        form.add(new CheckBox("selectAll"));

        DataView<GoReplyBean> goReplyDataView = initGoReplyDataView("repeating");
        form.add(goReplyDataView);
        form.add(new PagingNavigator("navigator", goReplyDataView));
        add(form);
    }

    private DataView<GoReplyBean> initGoReplyDataView(String componentId) {

        DataView<GoReplyBean> dataView = new DataView<GoReplyBean>(componentId, getDataProvider(), ITEMS_PER_PAGE) {

            @Override
            protected void populateItem(Item<GoReplyBean> goReplyBeanItem) {
                GoReplyBean goReplyBean = goReplyBeanItem.getModelObject();
                goReplyBeanItem.add(new SelectReplyCheckBox("selectCheckBox"));
                goReplyBeanItem.add(new Label("car", goReplyBean.getCar()));
                goReplyBeanItem.add(new Label("price", goReplyBean.getPrice()));
                goReplyBeanItem.add(new Label("time", goReplyBean.getTime()));
            }
        };
        return dataView;
    }

    protected GoReplySortableDataProvider getDataProvider() {
        return new GoReplySortableDataProvider();
    }

    /**
     * @see org.apache.wicket.extensions.breadcrumb.IBreadCrumbParticipant#getTitle()
     */
    public String getTitle() {
        return getString("title");
    }

    private class SelectReplyCheckBox extends CheckBox {

        public SelectReplyCheckBox(String s) {
            super(s);
        }
    }
}