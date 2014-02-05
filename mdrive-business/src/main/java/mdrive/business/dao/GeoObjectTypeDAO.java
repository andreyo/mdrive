package mdrive.business.dao;

import mdrive.business.model.GeoObjectTypeBean;
import mdrive.business.type.GeoObjectTypeCode;
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
public class GeoObjectTypeDao extends GenericDao<GeoObjectTypeBean> {


    public GeoObjectTypeBean findByTypeCode(GeoObjectTypeCode geoObjectTypeCode) {
        String hql = "from GeoObjectTypeBean got where got.typeCode = :typeCode";
        return (GeoObjectTypeBean) entityManager.createQuery(hql)
                .setParameter("typeCode", geoObjectTypeCode).getSingleResult();
    }
}