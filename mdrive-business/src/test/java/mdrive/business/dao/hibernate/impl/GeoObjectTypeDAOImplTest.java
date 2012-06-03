package mdrive.business.dao.hibernate.impl;

import mdrive.business.HsqldbJUnit4SpringContextTests;
import mdrive.business.bean.GeoObjectTypeBean;
import mdrive.business.dao.hibernate.GeoObjectTypeDAO;
import mdrive.business.type.GeoObjectTypeCode;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
@ContextConfiguration(locations = {"classpath:testApplicationContext.xml"})
public class GeoObjectTypeDAOImplTest extends HsqldbJUnit4SpringContextTests {

    @Autowired
    GeoObjectTypeDAO geoObjectTypeDAO;

    @Before
    public void init() throws Exception {
        initTestData();
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