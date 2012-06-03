package mdrive.business;

import mdrive.business.bean.CoordinatesBean;
import mdrive.business.bean.GeoObjectBean;
import mdrive.business.bean.GeoObjectTypeBean;
import mdrive.business.bean.I18NameBean;
import mdrive.business.dao.hibernate.GeoObjectDAO;
import mdrive.business.dao.hibernate.GeoObjectTypeDAO;
import mdrive.business.service.DBUnitDataExporter;
import mdrive.business.type.GeoObjectTypeCode;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * User: andrey.osipov
 * Date: 7/25/11
 * Time: 4:19 PM
 */
public class ExcelToModelConverter extends HsqldbJUnit4SpringContextTests {
    private static final String[] TABLE_NAMES = {"I18NAME", "GEO_OBJECT"};

    private GeoObjectTypeBean streetGeoObjectTypeBean;
    private GeoObjectTypeBean buildingGeoObjectTypeBean;

    @Autowired
    GeoObjectDAO geoObjectDAO;

    @Autowired
    GeoObjectTypeDAO geoObjectTypeDAO;

    @Autowired
    DBUnitDataExporter dbUnitDataExporter;

    @Test
    //uncomment this to see results in export()
//    @Rollback(false)
    public void doReadGeoObjectsAndPersist2DB() throws Exception {
        geoObjectDAO.setAutoFlush(false);
        initTestDataXml("initialdb_dictionary.xml");//insert dictionaries
//        readGeoObjectsAndPersist2DB("streets.xls", 0, 2086);
        readGeoObjectsAndPersist2DB("streets.xls", 0, 3);
    }

    //export hangs on uncommited data, that's why we do it in 2 tests = transactions
    @Test
    public void export() throws Exception {
//        DBExporterTest.exportTables(dataSource, TABLE_NAMES, "excel_streets.xml");
        dbUnitDataExporter.exportTables("csv_initial_db");
    }

    /**
     * Reads rows from Excel document with streets and building numbers and persists in DB
     * Building geoObjectBeans are children of street beans. Coordinates are empty beans. Names of streets are in one language (Ukrainian)
     *
     * @param excelFile
     * @param startPos  - start from 0
     * @param endPos
     */
    private void readGeoObjectsAndPersist2DB(String excelFile, int startPos, int endPos) throws IOException {
        HSSFSheet sheet = getExcelSheet(excelFile);

        GeoObjectBean currentStreetGeoObject = null;
        for (int i = startPos; i < endPos; i++) {
            //create street GeoObject
            String streetName = sheet.getRow(i).getCell(0).toString();
            if (StringUtils.isEmpty(streetName)) {
                throw new RuntimeException("Street name in row " + i + " is empty");
            }
            currentStreetGeoObject = createStreetGeoObject(streetName);
            logger.debug(streetName);
            //create buildings GeoObjects
            List<GeoObjectBean> buildingGOList = new ArrayList<GeoObjectBean>();
            for (int j = 1; j < sheet.getRow(i).getLastCellNum(); j++) {
                String buildingName = sheet.getRow(i).getCell(j).toString();
                GeoObjectBean buildingGO = createBuildingGeoObject(buildingName, currentStreetGeoObject);
                buildingGOList.add(buildingGO);
                logger.debug(buildingName);
            }
            //do not skip strets without buildings
            if(buildingGOList.size() != 0) {
                geoObjectDAO.saveOrUpdateAll(buildingGOList);
            } else {
                geoObjectDAO.create(currentStreetGeoObject);
            }

        }
    }

    /**
     * Creates street GeoObjectBean, and persists to DB
     *
     * @param streetName
     * @return reference to created object
     */
    private GeoObjectBean createStreetGeoObject(String streetName) {
        GeoObjectBean streetGO = new GeoObjectBean();
        //to select once, instead of every time
        if (streetGeoObjectTypeBean == null || !geoObjectTypeDAO.sessionContains(streetGeoObjectTypeBean)) {
            streetGeoObjectTypeBean = geoObjectTypeDAO.findByTypeCode(GeoObjectTypeCode.STREET);
        }
        streetGO.setGeoObjectTypeBean(streetGeoObjectTypeBean);
        //empty coordinates, will be resolved later
        CoordinatesBean coordinatesBean = new CoordinatesBean();
        coordinatesBean.setGeoObjectBean(streetGO);
        streetGO.setCoordinatesBean(coordinatesBean);
        streetGO.setObjectI18Name(new I18NameBean("", "", streetName));
        //parent for street will be set later
        streetGO.setParentGeoObjectBean(null);
        return streetGO;
    }

    /**
     * Creates building GeoObjectBean and persists to DB
     *
     * @param buildingName
     * @param streetGeoObject - parent geoObject is street
     * @return
     */
    private GeoObjectBean createBuildingGeoObject(String buildingName, GeoObjectBean streetGeoObject) {
        GeoObjectBean buildingGO = new GeoObjectBean();
        //to select once, instead of every time
        if (buildingGeoObjectTypeBean == null || !geoObjectTypeDAO.sessionContains(buildingGeoObjectTypeBean)) {
            buildingGeoObjectTypeBean = geoObjectTypeDAO.findByTypeCode(GeoObjectTypeCode.BUILDING);
        }
        buildingGO.setGeoObjectTypeBean(buildingGeoObjectTypeBean);
        //empty coordinates, will be resolved later
        CoordinatesBean coordinatesBean = new CoordinatesBean();
        coordinatesBean.setGeoObjectBean(buildingGO);
        buildingGO.setCoordinatesBean(coordinatesBean);
        buildingGO.setObjectI18Name(new I18NameBean("", "", buildingName));
        //parent for building is street
        buildingGO.setParentGeoObjectBean(streetGeoObject);
        return buildingGO;
    }

    /**
     * Get number of rows in excel file
     * @param excelFile
     * @return
     * @throws IOException
     */
    private static int getRowNum(String excelFile) throws IOException {
        HSSFSheet sheet = getExcelSheet(excelFile);
        return sheet.getLastRowNum();
    }

    private static HSSFSheet getExcelSheet(String excelFile) throws IOException {
        InputStream fis = null;
        try {
            fis = ExcelToModelConverter.class.getClassLoader().getResourceAsStream(excelFile);

            HSSFWorkbook workbook = new HSSFWorkbook(fis);
            HSSFSheet sheet = workbook.getSheetAt(0);
            if (fis != null) {
                fis.close();
            }
            return sheet;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Just streets prefixes to remember
     * @param streetName
     * @return
     */
    private String formatStreetNameToStandardView(String streetName) {
        /*
        Вул.
        Пров.
        Просп.
        Пл.
        Бульв.

        Шосе
        Тупик
        Узвіз
        Міст
        Дорога
        */
        return null;
    }
}