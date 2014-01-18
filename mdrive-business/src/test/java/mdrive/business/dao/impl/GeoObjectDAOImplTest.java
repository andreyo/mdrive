package mdrive.business.dao.impl;

import mdrive.business.config.JpaTestConfig;
import mdrive.business.dao.GeoObjectDAO;
import mdrive.business.model.GeoObjectBean;
import mdrive.business.model.nullobject.NullGeoObjectBean;
import mdrive.business.service.DBUnitDataLoader;
import mdrive.business.type.Constants;
import mdrive.business.type.GeoObjectTypeCode;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by IntelliJ IDEA.
 * User: Elena
 * Date: 11.01.2011
 * Time: 21:57:30
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JpaTestConfig.class)
@Transactional
public class GeoObjectDAOImplTest {

    private static GeoObjectBean streetGO_from_testLazyInitializationException;

    @Autowired
    private GeoObjectDAO geoObjectDAO;

    @Autowired
    private DBUnitDataLoader dbUnitDataLoader;

    @Before
    public void init() throws Exception {
        dbUnitDataLoader.initTestDataCsv();
    }

    @Test
    public void getStreetsStartingWith() throws Exception {
        final String PREFIX = "K";
        List<GeoObjectBean> resultList = geoObjectDAO.getStreetGeoObjectsStartingWith(PREFIX, 10);
        assertEquals(1, resultList.size());
        for (GeoObjectBean geoObjectBean : resultList) {
            String value_en = geoObjectBean.getObjectI18Name().getValue();
            assertNotNull(value_en);
            value_en.startsWith(PREFIX);
            System.out.println(geoObjectBean);
        }
    }

    @Test
    public void getStreetsStartingWithPrefix_andLocale() throws Exception {
        final String PREFIX = "Льва";
        List<GeoObjectBean> resultList = geoObjectDAO.getStreetGeoObjectsStartingWith(PREFIX, Constants.LOCALE_RU, 10);
        assertEquals(1, resultList.size());
        for (GeoObjectBean geoObjectBean : resultList) {
            String value = geoObjectBean.getObjectI18Name().getValue(Constants.LOCALE_RU);
            assertNotNull(value);
            assertTrue(value.startsWith(PREFIX));
            System.out.println(geoObjectBean);
        }
    }

    @Test
    public void getGeoObjectsByTypeAndParentAndStartingWith() throws Exception {
        //building, search without parent
        List<GeoObjectBean> geoObjectBeans = geoObjectDAO
                .getGeoObjectsByTypeAndParentAndPrefix(GeoObjectTypeCode.STREET,
                        null,
                        "Avtozavodska",
                        Constants.LOCALE_EN,
                        20);
        assertNotNull(geoObjectBeans);
        assertEquals(1, geoObjectBeans.size());
        GeoObjectBean streetGO = geoObjectBeans.get(0);

        //building, search with parent
        geoObjectBeans = geoObjectDAO.getGeoObjectsByTypeAndParentAndPrefix(GeoObjectTypeCode.BUILDING,
                streetGO.getId(),
                "2A",
                Constants.LOCALE_EN,
                20);
        assertNotNull(geoObjectBeans);
        assertEquals(1, geoObjectBeans.size());
    }

    @Test
    public void testLazyInitializationExceptionPart1() throws Exception {
        //building, search without parent
        List<GeoObjectBean> geoObjectBeans = geoObjectDAO
                .getGeoObjectsByTypeAndParentAndPrefix(GeoObjectTypeCode.STREET,
                        null,
                        "Avtozavodska",
                        Constants.LOCALE_EN,
                        20);
        assertNotNull(geoObjectBeans);
        assertEquals(1, geoObjectBeans.size());
        streetGO_from_testLazyInitializationException = geoObjectBeans.get(0);
    }

    //read object properties after transaction is closed
    @Test
    public void testLazyInitializationExceptionPart2() throws Exception {
        assertEquals("Avtozavodska", streetGO_from_testLazyInitializationException.getObjectI18Name().getValue());
    }

    @Test
    public void testLoadWithPrefixA() throws Exception {
        //building, search without parent
        List<GeoObjectBean> geoObjectBeans = geoObjectDAO
                .getGeoObjectsByTypeAndParentAndPrefix(GeoObjectTypeCode.STREET, null, "A", Constants.LOCALE_EN, 20);
        assertNotNull(geoObjectBeans);
        assertTrue(geoObjectBeans.size() > 1);
    }

    @Test
    public void getBuildingsByStreetId() throws Exception {
        List<GeoObjectBean> list = geoObjectDAO.getBuildingsByStreetId(102L);
        assertNotNull(list);
        assertEquals(3, list.size());
    }

    @Test
    public void getTotalUnresolvedBuildingsLeft() throws Exception {
        Long buildingsLeft = geoObjectDAO.getTotalUnresolvedBuildingsLeft();
        assertEquals(Long.valueOf(1674), buildingsLeft);
    }

    @Test
    public void getFullGeoObjectBeanById() {
        GeoObjectBean geoObjectBean = geoObjectDAO.getFullGeoObjectBeanById(949L);
        assertNotNull(geoObjectBean);
        assertEquals("4A", geoObjectBean.getObjectI18Name().getValue(Constants.LOCALE_EN));
    }

    @Test
    public void getGeoObjectsByLocation() throws Exception {
        final List<GeoObjectBean> geoObjectsByLocationList = geoObjectDAO.getGeoObjectsByLocation(Float.valueOf(0),
                Float.valueOf(0),
                Float.valueOf(100),
                GeoObjectTypeCode.STREET);
        assertNotNull(geoObjectsByLocationList);
        assertTrue(geoObjectsByLocationList.size() > 0);
        for (GeoObjectBean geoObjectBean : geoObjectsByLocationList) {
            System.out.println(geoObjectBean);
        }
    }
}