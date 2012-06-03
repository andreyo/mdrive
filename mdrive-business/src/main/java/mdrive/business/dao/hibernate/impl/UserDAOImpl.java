package mdrive.business.dao.hibernate.impl;

import mdrive.business.bean.UserBean;
import mdrive.business.bean.nullobject.NullUserBean;
import mdrive.business.dao.hibernate.UserDAO;
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
public class UserDAOImpl extends GenericDAOHibernateImpl<UserBean, Long> implements UserDAO {

    @Override
    public UserBean getNullObject() {
        return NullUserBean.instance;
    }
}