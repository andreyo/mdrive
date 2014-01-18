package mdrive.page.go.replies;

import mdrive.business.dao.GoReplyDAO;
import mdrive.business.model.GoReplyBean;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: Elena
 * Date: 11.01.2011
 * Time: 8:02:56
 * To change this template use File | Settings | File Templates.
 */
public class GoReplySortableDataProvider extends SortableDataProvider<GoReplyBean> {

    @SpringBean
    GoReplyDAO goReplyDAO;

    public GoReplySortableDataProvider() {
    }

    @Override
    public Iterator<GoReplyBean> iterator(int first, int count) {
        SortParam sp = getSort();
        //TODO: fix
        return goReplyDAO.findAll().iterator();
    }

    @Override
    public int size() {
        return goReplyDAO.findAll().size();
    }

    @Override
    public IModel<GoReplyBean> model(GoReplyBean object) {
        return new GoReplyDetachableModel(object);
    }
}