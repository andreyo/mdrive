package mdrive.business.dao.hibernate.impl;

import mdrive.business.bean.GeoObjectTypeBean;
import mdrive.business.bean.nullobject.NullGeoObjectTypeBean;
import mdrive.business.dao.hibernate.GeoObjectTypeDAO;
import mdrive.business.type.GeoObjectTypeCode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Elena
 * Date: 11.01.2011
 * Time: 8:25:52
 * To change this template use File | Settings | File Templates.
 */
@Transactional
@Component
public class GeoObjectTypeDAOImpl extends GenericDAOHibernateImpl<GeoObjectTypeBean, Long> implements GeoObjectTypeDAO {

    public GeoObjectTypeBean findByTypeCode(GeoObjectTypeCode geoObjectTypeCode) {
        String hql = "from GeoObjectTypeBean got where got.typeCode = :typeCode";
        List<GeoObjectTypeBean> beanList = getHibernateTemplate()
                .findByNamedParam(hql, new String[]{"typeCode"}, new Object[]{geoObjectTypeCode});
        Iterator<GeoObjectTypeBean> it = beanList.iterator();
        return it.next();
    }

    @Override
    public GeoObjectTypeBean getNullObject() {
        return NullGeoObjectTypeBean.instance;
    }
}