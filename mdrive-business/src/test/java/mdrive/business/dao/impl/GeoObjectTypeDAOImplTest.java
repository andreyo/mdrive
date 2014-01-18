package mdrive.business.dao.impl;

import mdrive.business.config.JpaTestConfig;
import mdrive.business.dao.GeoObjectTypeDAO;
import mdrive.business.model.GeoObjectTypeBean;
import mdrive.business.service.DBUnitDataLoader;
import mdrive.business.type.GeoObjectTypeCode;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * GeoObjectTypeDAOImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>05/18/2011</pre>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JpaTestConfig.class)
@Transactional
public class GeoObjectTypeDAOImplTest {

    @Autowired
    GeoObjectTypeDAO geoObjectTypeDAO;

    @Autowired
    private DBUnitDataLoader dbUnitDataLoader;

    @Before
    public void init() throws Exception {
        dbUnitDataLoader.initTestData();
    }

    @Test
    public void get() {
        List<GeoObjectTypeBean> geoObjectTypeBeanList = geoObjectTypeDAO.findAll();
        for (GeoObjectTypeBean geoObjectTypeBean : geoObjectTypeBeanList) {
            System.out.println(geoObjectTypeBean);
        }
    }

    @Test
    public void findByTypeCode() {
        GeoObjectTypeBean geoObjectTypeBean = geoObjectTypeDAO.findByTypeCode(GeoObjectTypeCode.STREET);
        assertEquals(GeoObjectTypeCode.STREET, geoObjectTypeBean.getTypeCode());
    }
}