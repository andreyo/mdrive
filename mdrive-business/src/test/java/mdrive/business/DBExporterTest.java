package mdrive.business;

import mdrive.business.config.JpaTestConfig;
import mdrive.business.dao.GeoObjectDao;
import mdrive.business.dao.GeoObjectTypeDao;
import mdrive.business.model.CoordinatesBean;
import mdrive.business.model.GeoObjectBean;
import mdrive.business.model.I18NameBean;
import mdrive.business.service.DBUnitDataExporter;
import mdrive.business.type.GeoObjectTypeCode;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

/**
 * User: andrey.osipov
 * Date: 6/10/11
 * Time: 4:39 PM
 */
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JpaTestConfig.class)
@Transactional
public class DBExporterTest {
    private static final String[] TABLE_NAMES = {"I18NAME", "GEO_OBJECT", "GO_BID", "GO_REPLY"};


    @Autowired
    GeoObjectDao geoObjectDao;

    @Autowired
    GeoObjectTypeDao geoObjectTypeDao;

    @Autowired
    DBUnitDataExporter dbUnitDataExporter;

    @Test
    public void testGenerate() throws Exception {
//        exportTables(dataSource, TABLE_NAMES, "dbunit_export.xml", Charset.forName("UTF-8"));
        dbUnitDataExporter.exportTables("dbunit_export.xml");
    }

    public void addDataIntoDB() {
        //STREETS:
        GeoObjectBean geoObjectBean = new GeoObjectBean();
        geoObjectBean.setGeoObjectTypeBean(geoObjectTypeDao.findByTypeCode(GeoObjectTypeCode.STREET));
        CoordinatesBean coordinatesBean = new CoordinatesBean();
        coordinatesBean.setGeoObjectBean(geoObjectBean);
        geoObjectBean.setCoordinatesBean(coordinatesBean);
        geoObjectBean.setObjectI18Name(new I18NameBean("new_street", "новая_улица", "nova_vulitsa"));
        geoObjectBean.setParentGeoObjectBean(null);
        geoObjectDao.persist(geoObjectBean);
    }
}