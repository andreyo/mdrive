package mdrive.page.go.replies;

import mdrive.app.MApplication;
import mdrive.business.bean.GoReplyBean;
import org.apache.wicket.model.LoadableDetachableModel;

/**
 * Created by IntelliJ IDEA.
 * User: Elena
 * Date: 14.01.2011
 * Time: 15:03:16
 * To change this template use File | Settings | File Templates.
 */
public class GoReplyDetachableModel extends LoadableDetachableModel<GoReplyBean> {

    private long id;

    private GoReplyDetachableModel() {
    }

    public GoReplyDetachableModel(GoReplyBean object) {
        super(object);
        this.id = object.getId();
    }

    @Override
    protected GoReplyBean load() {
        return MApplication.get().getGoReplyDAO().findById(id);
    }
}