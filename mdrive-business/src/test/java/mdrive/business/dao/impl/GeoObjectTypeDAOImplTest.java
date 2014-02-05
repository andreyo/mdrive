package mdrive.business.dao.impl;

import mdrive.business.config.JpaTestConfig;
import mdrive.business.dao.GeoObjectTypeDao;
import mdrive.business.model.GeoObjectTypeBean;
import mdrive.business.type.GeoObjectTypeCode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * GeoObjectTypeDao Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>05/18/2011</pre>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JpaTestConfig.class)
@Transactional
public class GeoObjectTypeDaoImplTest {

    @Autowired
    GeoObjectTypeDao geoObjectTypeDao;

    @Test
    public void get() {
        List<GeoObjectTypeBean> geoObjectTypeBeanList = geoObjectTypeDao.findAll();
        for (GeoObjectTypeBean geoObjectTypeBean : geoObjectTypeBeanList) {
            System.out.println(geoObjectTypeBean);
        }
    }

    @Test
    public void findByTypeCode() {
        GeoObjectTypeBean geoObjectTypeBean = geoObjectTypeDao.findByTypeCode(GeoObjectTypeCode.STREET);
        assertEquals(GeoObjectTypeCode.STREET, geoObjectTypeBean.getTypeCode());
    }
}