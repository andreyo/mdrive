package mdrive.page.go.replies;

import mdrive.business.dao.GoReplyDAO;
import mdrive.business.model.GoReplyBean;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * Created by IntelliJ IDEA.
 * User: Elena
 * Date: 14.01.2011
 * Time: 15:03:16
 * To change this template use File | Settings | File Templates.
 */
public class GoReplyDetachableModel extends LoadableDetachableModel<GoReplyBean> {

    @SpringBean
    GoReplyDAO goReplyDAO;

    private long id;

    private GoReplyDetachableModel() {
    }

    public GoReplyDetachableModel(GoReplyBean object) {
        super(object);
        this.id = object.getId();
    }

    @Override
    protected GoReplyBean load() {
        return goReplyDAO.findOne(id);
    }
}