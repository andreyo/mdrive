package mdrive.business.dao.impl;

import mdrive.business.model.UserBean;
import mdrive.business.dao.UserDAO;
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
public class UserDAOImpl extends GenericDaoImpl<UserBean> implements UserDAO {
}