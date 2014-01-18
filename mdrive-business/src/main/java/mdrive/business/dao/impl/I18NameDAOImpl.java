package mdrive.business.dao.impl;

import mdrive.business.model.I18NameBean;
import mdrive.business.dao.I18NameDAO;
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
public class I18NameDAOImpl extends GenericDaoImpl<I18NameBean> implements I18NameDAO {
}