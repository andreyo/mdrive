package mdrive.business.dao.impl;

import mdrive.business.config.JpaTestConfig;
import mdrive.business.dao.GeoObjectDao;
import mdrive.business.model.GeoObjectBean;
import mdrive.business.type.Constants;
import mdrive.business.type.GeoObjectTypeCode;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
public class GeoObjectDaoImplTest {

    private static final Logger log = Logger.getLogger(GeoObjectDaoImplTest.class);
    private static GeoObjectBean streetGO_from_testLazyInitializationException;

    @Autowired
    private GeoObjectDao geoObjectDao;

    @PersistenceContext
    EntityManager entityManager;

    @Test
    public void getStreetsStartingWith() throws Exception {
        final String PREFIX = "K";
        List<GeoObjectBean> resultList = geoObjectDao.getStreetGeoObjectsStartingWith(PREFIX, 10);
        assertEquals(10, resultList.size());
    }

    @Test
    public void getStreetsStartingWithPrefix_andLocale() throws Exception {
        final String PREFIX = "Тол";
        List<GeoObjectBean> resultList = geoObjectDao.getStreetGeoObjectsStartingWith(PREFIX, Constants.LOCALE_RU, 10);
        assertEquals(1, resultList.size());
        for (GeoObjectBean geoObjectBean : resultList) {
            String value = geoObjectBean.getName().getValue(Constants.LOCALE_RU);
            assertNotNull(value);
            assertTrue(value.startsWith(PREFIX));
            log.info(geoObjectBean);
        }
    }

    @Test
    public void getGeoObjectsByTypeAndParentAndStartingWith() throws Exception {
        //building, search without parent
        List<GeoObjectBean> geoObjectBeans = geoObjectDao
                .getGeoObjectsByTypeAndParentAndPrefix(GeoObjectTypeCode.STREET,
                        null,
                        "Avtozavods'ka vul.",
                        Constants.LOCALE_EN,
                        20);
        assertNotNull(geoObjectBeans);
        assertEquals(1, geoObjectBeans.size());
        GeoObjectBean streetGO = geoObjectBeans.get(0);

        //building, search with parent
        geoObjectBeans = geoObjectDao.getGeoObjectsByTypeAndParentAndPrefix(GeoObjectTypeCode.BUILDING,
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
        List<GeoObjectBean> geoObjectBeans = geoObjectDao
                .getGeoObjectsByTypeAndParentAndPrefix(GeoObjectTypeCode.STREET,
                        null,
                        "Avtozavods",
                        Constants.LOCALE_EN,
                        20);
        assertNotNull(geoObjectBeans);
        assertEquals(2, geoObjectBeans.size());
        streetGO_from_testLazyInitializationException = geoObjectBeans.get(0);
    }

    @Test
    public void testLoadWithPrefixA() throws Exception {
        //building, search without parent
        List<GeoObjectBean> geoObjectBeans = geoObjectDao
                .getGeoObjectsByTypeAndParentAndPrefix(GeoObjectTypeCode.STREET, null, "A", Constants.LOCALE_EN, 20);
        assertNotNull(geoObjectBeans);
        assertTrue(geoObjectBeans.size() > 1);
    }


    @Test
    public void getTotalUnresolvedBuildingsLeft() throws Exception {
        Long buildingsLeft = geoObjectDao.getTotalUnresolvedBuildingsLeft();
        assertEquals(Long.valueOf(40173), buildingsLeft);
    }

    @Test
    public void getGeoObjectsByLocation() throws Exception {
        final List<GeoObjectBean> geoObjectsByLocationList = geoObjectDao.getGeoObjectsByLocationAndRadius(
                50f,
                30f,
                0.51f,
                GeoObjectTypeCode.BUILDING,
                10);
        assertNotNull(geoObjectsByLocationList);
        assertTrue(geoObjectsByLocationList.size() > 0);
        for (GeoObjectBean geoObjectBean : geoObjectsByLocationList) {
            System.out.println(geoObjectBean);
        }
    }
}