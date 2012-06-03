package mdrive.business.dao.hibernate.impl;

import mdrive.business.bean.GoReplyBean;
import mdrive.business.bean.nullobject.NullGoReplyBean;
import mdrive.business.dao.hibernate.GoReplyDAO;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by IntelliJ IDEA.
 * User: Elena
 * Date: 11.01.2011
 * Time: 8:25:52
 * To change this template use File | Settings | File Templates.
 */
@Transactional
@Component
public class GoReplyDAOImpl extends GenericDAOHibernateImpl<GoReplyBean, Long> implements GoReplyDAO {

    @Override
    public GoReplyBean getNullObject() {
        return NullGoReplyBean.instance;
    }
}