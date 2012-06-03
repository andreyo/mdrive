package mdrive.business.dao.hibernate.impl;

import mdrive.business.bean.I18NameBean;
import mdrive.business.bean.nullobject.NullI18NameBean;
import mdrive.business.dao.hibernate.I18NameDAO;
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
public class I18NameDAOImpl extends GenericDAOHibernateImpl<I18NameBean, Long> implements I18NameDAO {

    @Override
    public I18NameBean getNullObject() {
        return NullI18NameBean.instance;
    }
}