package mdrive.business;

import mdrive.business.config.JpaTestConfig;
import mdrive.business.dao.GeoObjectDAO;
import mdrive.business.dao.GeoObjectTypeDAO;
import mdrive.business.model.CoordinatesBean;
import mdrive.business.model.GeoObjectBean;
import mdrive.business.model.I18NameBean;
import mdrive.business.service.DBUnitDataExporter;
import mdrive.business.service.DBUnitDataLoader;
import mdrive.business.type.GeoObjectTypeCode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * User: andrey.osipov
 * Date: 6/10/11
 * Time: 4:39 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JpaTestConfig.class)
public class DBExporterTest {
    private static final String[] TABLE_NAMES = {"I18NAME", "GEO_OBJECT", "GO_BID", "GO_REPLY"};


    @Autowired
    GeoObjectDAO geoObjectDAO;

    @Autowired
    GeoObjectTypeDAO geoObjectTypeDAO;

    @Autowired
    DBUnitDataLoader dbUnitDataLoader;

    @Autowired
    DBUnitDataExporter dbUnitDataExporter;

    @Test
    @Rollback(false)
    //do this in separate transaction, which is commited at the end, because exportTables will hang on not-commited table
    public void init() throws Exception {
        dbUnitDataLoader.initTestData();
//        initTestDataCsv();
//        addDataIntoDB();
    }
    //README: Transactions are started by SpringJUnit4ClassRunner + AbstractTransactionalJUnit4SpringContextTests
    // and marking methods in testClass @Transactional(propagation = Propagation.REQUIRES_NEW) doesnt have any effect
    // may be this is because test classes are not scanned by transactionManager

    @Test
    public void testGenerate() throws Exception {
//        exportTables(dataSource, TABLE_NAMES, "dbunit_export.xml", Charset.forName("UTF-8"));
        dbUnitDataExporter.exportTables("dbunit_export.xml");
    }

    public void addDataIntoDB() {
        //STREETS:
        GeoObjectBean geoObjectBean = new GeoObjectBean();
        geoObjectBean.setGeoObjectTypeBean(geoObjectTypeDAO.findByTypeCode(GeoObjectTypeCode.STREET));
        CoordinatesBean coordinatesBean = new CoordinatesBean();
        coordinatesBean.setGeoObjectBean(geoObjectBean);
        geoObjectBean.setCoordinatesBean(coordinatesBean);
        geoObjectBean.setObjectI18Name(new I18NameBean("new_street", "новая_улица", "nova_vulitsa"));
        geoObjectBean.setParentGeoObjectBean(null);
        geoObjectDAO.persist(geoObjectBean);
    }
}