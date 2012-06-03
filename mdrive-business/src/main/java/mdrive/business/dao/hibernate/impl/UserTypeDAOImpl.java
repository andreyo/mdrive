package mdrive.business.dao.hibernate.impl;

import mdrive.business.bean.UserTypeBean;
import mdrive.business.bean.nullobject.NullUserTypeBean;
import mdrive.business.dao.hibernate.UserTypeDAO;
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
public class UserTypeDAOImpl extends GenericDAOHibernateImpl<UserTypeBean, Long> implements UserTypeDAO {

    public static final long PASSENGER_TYPE_CODE = 1L;
    public static final long DRIVER_TYPE_CODE = 2L;

    @Override
    public UserTypeBean getNullObject() {
        return NullUserTypeBean.instance;
    }

    @Override
    public UserTypeBean getPassengerUserTypeBean() {
        return findById(PASSENGER_TYPE_CODE);
    }

    @Override
    public UserTypeBean getDriverUserTypeBean() {
        return findById(DRIVER_TYPE_CODE);
    }
}