package mdrive.business.dao.hibernate.impl;

import mdrive.business.bean.CoordinatesBean;
import mdrive.business.bean.nullobject.NullCoordinatesBean;
import mdrive.business.dao.hibernate.CoordinatesDAO;
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
public class CoordinatesDAOImpl extends GenericDAOHibernateImpl<CoordinatesBean, Long> implements CoordinatesDAO {

    @Override
    public CoordinatesBean getNullObject() {
        return NullCoordinatesBean.instance;
    }
}
