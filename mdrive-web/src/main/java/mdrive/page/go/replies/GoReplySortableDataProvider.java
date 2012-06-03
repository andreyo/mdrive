package mdrive.page.go.replies;

import mdrive.app.MApplication;
import mdrive.business.bean.GoReplyBean;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;

import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: Elena
 * Date: 11.01.2011
 * Time: 8:02:56
 * To change this template use File | Settings | File Templates.
 */
public class GoReplySortableDataProvider extends SortableDataProvider<GoReplyBean> {

    public GoReplySortableDataProvider() {
    }

    @Override
    public Iterator<GoReplyBean> iterator(int first, int count) {
        SortParam sp = getSort();
        return MApplication.get().getGoReplyDAO().findAll(first, count).iterator();
    }

    @Override
    public int size() {
        return MApplication.get().getGoReplyDAO().findAll().size();
    }

    @Override
    public IModel<GoReplyBean> model(GoReplyBean object) {
        return new GoReplyDetachableModel(object);
    }
}