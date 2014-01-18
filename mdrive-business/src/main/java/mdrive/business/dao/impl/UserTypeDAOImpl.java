package mdrive.business.dao.impl;

import mdrive.business.model.UserTypeBean;
import mdrive.business.dao.UserTypeDAO;
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
public class UserTypeDAOImpl extends GenericDaoImpl<UserTypeBean> implements UserTypeDAO {

    public static final long PASSENGER_TYPE_CODE = 1L;
    public static final long DRIVER_TYPE_CODE = 2L;

    @Override
    public UserTypeBean getPassengerUserTypeBean() {
        return findOne(PASSENGER_TYPE_CODE);
    }

    @Override
    public UserTypeBean getDriverUserTypeBean() {
        return findOne(DRIVER_TYPE_CODE);
    }
}