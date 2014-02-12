package mdrive.page.lift;

import mdrive.business.dao.GoBidDao;
import mdrive.business.model.GeoObjectBean;
import mdrive.business.model.GoBidBean;
import mdrive.page.lift.filter.SelectFilterPanel;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackDefaultDataTable;
import org.apache.wicket.extensions.breadcrumb.IBreadCrumbModel;
import org.apache.wicket.extensions.breadcrumb.panel.BreadCrumbPanel;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.ComponentFeedbackPanel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.ArrayList;
import java.util.List;

/**
 * User: andrey.osipov
 */
public class LiftPanel extends BreadCrumbPanel {
    private static final int ITEMS_PER_PAGE = 8;

    @SpringBean
    private GoBidDao goBidDao;

    private boolean initialized;

    private InputForm inputForm;

    //WicketTester constructor
    public LiftPanel(String id) {
        this(id, null);
    }

    //TODO: implement ObjectDispatcher (as in  http://www.kartadomov.com) to view Passengers on map
    // http://api.yandex.ru/maps/jsapi/doc/dg/concepts/objects_manager.xml

    /**
     * Construct.
     *
     * @param id
     * @param breadCrumbModel
     */
    public LiftPanel(final String id, final IBreadCrumbModel breadCrumbModel) {
        super(id, breadCrumbModel);
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

    private void init() {
        setOutputMarkupId(true);
        add(new ComponentFeedbackPanel("feedback", LiftPanel.this));
        inputForm = new InputForm("inputForm");
        inputForm.setOutputMarkupId(true);
        add(inputForm);
        inputForm.add(new SelectFilterPanel("filters"));
        inputForm.add(new MyLocationPanel("locationPanel"));
    }

    /**
     * @see org.apache.wicket.extensions.breadcrumb.IBreadCrumbParticipant#getTitle()
     */
    public String getTitle() {
        return getString("title");
    }


    //Components Classes
    //TODO: add filters: 1) by radius 2) by tariff 3) by time
    private class InputForm extends Form {
        //Search bids in coordinates rectangle of searchInGeoObjectBean
        private GeoObjectBean searchInGeoObjectBean = createSearchInGeoObjectBean();

        InputForm(String id) {
            super(id);
            add(initGoBidDataTable("goBidTable"));
        }

        private AjaxFallbackDefaultDataTable<GoBidBean> initGoBidDataTable(String componentId) {
            List<IColumn<GoBidBean>> columns = new ArrayList<IColumn<GoBidBean>>();
            columns.add(new AbstractColumn<GoBidBean>(new ResourceModel("from")) {
                @Override
                public void populateItem(Item<ICellPopulator<GoBidBean>> cellItem, String componentId,
                                         IModel<GoBidBean> rowModel) {
                    cellItem.add(new Label(componentId, getFromAddressLabel(rowModel.getObject())));
                }
            });
            columns.add(new AbstractColumn<GoBidBean>(new ResourceModel("to")) {
                @Override
                public void populateItem(Item<ICellPopulator<GoBidBean>> cellItem, String componentId,
                                         IModel<GoBidBean> rowModel) {
                    cellItem.add(new Label(componentId, getToAddressLabel(rowModel.getObject())));
                }
            });
            columns.add(new PropertyColumn<GoBidBean>(new ResourceModel("price"), "price"));
            columns.add(new PropertyColumn<GoBidBean>(new ResourceModel("time"), "time"));

            AjaxFallbackDefaultDataTable<GoBidBean> goBidDataTable = new AjaxFallbackDefaultDataTable<GoBidBean>(
                    componentId,
                    columns,
                    getDataProvider(),
                    ITEMS_PER_PAGE);
            return goBidDataTable;
        }

        private String getFromAddressLabel(GoBidBean goBidBean) {
            try {
                String streetName = goBidBean.getFromGeoObjectBean().getParentGeoObjectBean().getName()
                        .getValue(getLocale());
                String buildingName = goBidBean.getFromGeoObjectBean().getName().getValue(getLocale());
                return streetName + " " + buildingName;
            } catch (Exception e) {
                return "error";
            }
        }

        private String getToAddressLabel(GoBidBean goBidBean) {
            try {
                String streetName = goBidBean.getToGeoObjectBean().getParentGeoObjectBean().getName()
                        .getValue(getLocale());
                String buildingName = goBidBean.getToGeoObjectBean().getName().getValue(getLocale());
                return streetName + " " + buildingName;
            } catch (Exception e) {
                return "error";
            }
        }

        private LiftPanelDataProvider getDataProvider() {
            return new LiftPanelDataProvider(goBidDao, searchInGeoObjectBean);
        }
    }

    private GeoObjectBean createSearchInGeoObjectBean() {
        return new GeoObjectBean();
    }
}