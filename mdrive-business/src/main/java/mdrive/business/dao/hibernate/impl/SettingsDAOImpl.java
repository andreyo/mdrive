package mdrive.business.dao.hibernate.impl;

import mdrive.business.bean.SettingsBean;
import mdrive.business.bean.nullobject.NullSettingsBean;
import mdrive.business.dao.hibernate.SettingsDAO;
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
public class SettingsDAOImpl extends GenericDAOHibernateImpl<SettingsBean, Long> implements SettingsDAO {

    @Override
    public SettingsBean getNullObject() {
        return NullSettingsBean.instance;
    }
}