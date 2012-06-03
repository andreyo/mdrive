package mdrive.business;

import mdrive.business.bean.CoordinatesBean;
import mdrive.business.bean.GeoObjectBean;
import mdrive.business.bean.GeoObjectTypeBean;
import mdrive.business.bean.I18NameBean;
import mdrive.business.bean.embeddable.Point;
import mdrive.business.dao.hibernate.GeoObjectDAO;
import mdrive.business.dao.hibernate.GeoObjectTypeDAO;
import mdrive.business.helper.Translit;
import mdrive.business.type.Constants;
import mdrive.business.type.GeoObjectTypeCode;
import mdrive.geo.YandexRequest;
import net.opengis.gml.FeatureMemberType;
import net.opengis.gml.MetaDataPropertyType;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.yandex.maps.ymaps._1.GeoObjectType;
import ru.yandex.maps.ymaps._1.YmapsType;

import javax.sql.DataSource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * User: andrey.osipov
 * Date: 8/16/11
 * Time: 11:43 AM
 */

/*

CREATE TABLE COORDINATES_LOG (
 id bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
 street_id int NOT NULL,
 resolved_buildings varchar(1000),
 not_resolved_buildings varchar(1000),
 resolved_number int,
 total_buildings_left int,
 comment varchar(255),
 process_time TIMESTAMP(8)
)

 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:mysqlTestApplicationContext.xml"})
public class StreetsCoordinatesResolverTest extends AbstractTransactionalJUnit4SpringContextTests {
    /**
     * Task is to find street with unresolved building coordinates (at least one building)
     * resolveNextStreet them, and store to database. DB is remote mysql.
     */
    public static final String CITY_UK = "Київ";

    @Autowired
    DataSource mysqlDataSource;

    @Autowired
    GeoObjectDAO geoObjectDAO;

    @Autowired
    GeoObjectTypeDAO geoObjectTypeDAO;

    @Rollback(false)
    @Test
/* This test is excluded from normal build, so we shouldn't Ignore it*/
    public void resolveNextStreet() throws Exception {
        //variables for report
        String resolvedBuildings = "";
        Long resolvedBuildingsNumber = 0L;
        String notResolvedBuildings = "";
        geoObjectDAO.setAutoFlush(false);

        //Get Next Street To Work On
        Long streetId = getNextStreetId();
        GeoObjectBean streetBean = geoObjectDAO.findById(streetId);
        String streetNameUk = streetBean.getObjectI18Name().getValue(Constants.LOCALE_UK);
        //write to log at the beginning, to prevent infinite loop on error
        writeDBLog(streetBean.getId(), "start", "start", 0L, 0L, Translit.toTranslit(streetNameUk));
        //Get Buildings Of That Street
        List<GeoObjectBean> buildingBeans = geoObjectDAO.getBuildingsByStreetId(streetBean.getId());
        try {
            for (GeoObjectBean buildingBean : buildingBeans) {
                String buildingNameUk = buildingBean.getObjectI18Name().getValue(Constants.LOCALE_UK);
                logger.debug(CITY_UK + ", " + streetNameUk + ", " + buildingNameUk);
                //Resolve Coordinates Of Building
                if (true == resolveBuildingCoordinates(buildingBean, CITY_UK, streetNameUk, buildingNameUk)) {
                    resolvedBuildings += " " + buildingNameUk;
                    resolvedBuildingsNumber++;
                } else {
                    notResolvedBuildings += " " + buildingNameUk;
                    //exit if first building in street - not resolved
                    if (resolvedBuildingsNumber == 0) {
                        break;
                    }
                }
                Thread.sleep(2000);
            }
        } catch (Exception e) {
            //try-catch to prevent data loss
            logger.error(e);
        }
        //save results
        geoObjectDAO.saveOrUpdateAll(buildingBeans);
        //
        Long totalBuildingsLeft = geoObjectDAO.getTotalUnresolvedBuildingsLeft();
        writeDBLog(streetBean.getId(),
                resolvedBuildings,
                notResolvedBuildings,
                resolvedBuildingsNumber,
                totalBuildingsLeft,
                Translit.toTranslit(streetNameUk));
    }

    //select streets by type_id and maximum street id in LOG table
    private final String GET_NEXT_STREET_ID = "select min(id) as str_id from GEO_OBJECT where geo_object_type_id = (select id from GEO_OBJECT_TYPE where typeCode = 1) and id > coalesce((select max(street_id) from COORDINATES_LOG), 0)";

    private Long getNextStreetId() throws SQLException {
        Long nextStreetId = null;
        Connection conn = null;
        PreparedStatement statement = null;
        try {
            conn = mysqlDataSource.getConnection();
            statement = conn.prepareStatement(GET_NEXT_STREET_ID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                nextStreetId = resultSet.getLong("str_id");
            }
            return nextStreetId;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (statement != null) {
                statement.close();
            }
            conn.setAutoCommit(true);
        }
    }

    private final String WRITE_LOG_SQL = "INSERT INTO COORDINATES_LOG (street_id, resolved_buildings, not_resolved_buildings, resolved_number, total_buildings_left, comment) VALUES (?, ?, ?, ?, ?, ?);";

    private void writeDBLog(Long streetId, String resolvedBuildings, String notResolvedBuildings, Long resolved_number,
                            Long totalBuildingsLeft, String comment) throws SQLException {
        Connection conn = null;
        PreparedStatement statement = null;
        try {
            conn = mysqlDataSource.getConnection();
            statement = conn.prepareStatement(WRITE_LOG_SQL);
            statement.setLong(1, streetId);
            statement.setString(2, resolvedBuildings);
            statement.setString(3, notResolvedBuildings);
            statement.setLong(4, resolved_number);
            statement.setLong(5, totalBuildingsLeft);
            statement.setString(6, comment);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (statement != null) {
                statement.close();
            }
            conn.setAutoCommit(true);
        }
    }

    /**
     * CITY_AREA of street
     */
    private GeoObjectBean cityAreaGO = null;

    /**
     * Resolves coordinates, and saves to buildingBean
     *
     * @param buildingBean
     * @param city
     * @param street
     * @param buildingNo
     * @throws Exception
     */
    private boolean resolveBuildingCoordinates(GeoObjectBean buildingBean, String city, String street,
                                               String buildingNo) throws Exception {
        URL url = new YandexRequest(city, street, buildingNo).getRequest();
        JAXBContext jc = JAXBContext.newInstance("ru.yandex.maps.ymaps._1");
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        JAXBElement<YmapsType> result = (JAXBElement<YmapsType>) unmarshaller.unmarshal(url);
        YmapsType ymapsType = (YmapsType) result.getValue();
        MetaDataPropertyType metaDataPropertyType = ymapsType.getGeoObjectCollection().getMetaDataProperty();
        System.out.print("found = " + metaDataPropertyType.getGeocoderResponseMetaData().getFound() + " ");

        List<FeatureMemberType> featureMemberList = ymapsType.getGeoObjectCollection().getFeatureMember();
        if (featureMemberList == null) {
            logger.error("List of buildings in yandex response is null");
            return false;
        }
        if (featureMemberList.size() != 1) {
            logger.error("Size of List of buildings !=1  size = " + featureMemberList.size());
            return false;
        }
        for (FeatureMemberType featureMemberType : featureMemberList) {
            GeoObjectType geoObjectType = featureMemberType.getGeoObject();

            //Set Center Point coordinates
            String centerPoint = geoObjectType.getPoint().getPos();
            buildingBean.getCoordinatesBean().setCenter(new Point(centerPoint));
            //Set Lower Point coordinates
            String lowerLeft = geoObjectType.getBoundedBy().getEnvelope().getLowerCorner();
            buildingBean.getCoordinatesBean().setLowerLeft(new Point(lowerLeft));
            //Set Upper Point coordinates
            String upperRight = geoObjectType.getBoundedBy().getEnvelope().getLowerCorner();
            buildingBean.getCoordinatesBean().setUpperRight(new Point(upperRight));

            //process CITY_AREA
            if (cityAreaGO == null || !geoObjectDAO.sessionContains(cityAreaGO)) {
                String addressText = geoObjectType.getMetaDataProperty().getGeocoderMetaData().getText();
                cityAreaGO = getOrCreateCityAreaGeoObject(getCityAreaName(addressText));
            }
            //set cityArea as parent of street
            buildingBean.getParentGeoObjectBean().setParentGeoObjectBean(cityAreaGO);

            //print
            System.out.print(geoObjectType.getPoint().getPos() + ", ");
            System.out.println(geoObjectType.getMetaDataProperty().getGeocoderMetaData().getText());
        }
        return true;
    }

    private GeoObjectTypeBean cityAreaGeoObjectTypeBean = null;

    //search for existing City-Area before creating new one
    private GeoObjectBean getOrCreateCityAreaGeoObject(String cityAreaName) {
        //get type bean
        if (cityAreaGeoObjectTypeBean == null || !geoObjectTypeDAO.sessionContains(cityAreaGeoObjectTypeBean)) {
            cityAreaGeoObjectTypeBean = geoObjectTypeDAO.findByTypeCode(GeoObjectTypeCode.CITY_AREA);
        }
        // search in DB first
        GeoObjectBean cityAreaGO = new GeoObjectBean();
        cityAreaGO.setGeoObjectTypeBean(cityAreaGeoObjectTypeBean);
        cityAreaGO.setObjectI18Name(new I18NameBean(Translit.toTranslit(cityAreaName), "", cityAreaName));
        List<GeoObjectBean> cityAreasList = geoObjectDAO
                .getGeoObjectsByTypeAndParentAndPrefix(GeoObjectTypeCode.CITY_AREA,
                        null,
                        cityAreaName,
                        Constants.LOCALE_UK,
                        2);
        if (cityAreasList != null && cityAreasList.size() == 1) {
            return cityAreasList.get(0);
        } else {
            //return created new one if not found
            CoordinatesBean coordinatesBean = new CoordinatesBean();
            coordinatesBean.setGeoObjectBean(cityAreaGO);
            cityAreaGO.setCoordinatesBean(coordinatesBean);
            //city and country will be created later
            cityAreaGO.setParentGeoObjectBean(null);
            geoObjectDAO.create(cityAreaGO);
            return cityAreaGO;
        }
    }

    private String getCityAreaName(String addressText) {
        if (StringUtils.isEmpty(addressText)) {
            logger.error("Can't get CityArea: Address text is empty");
            return "";
        }
        String[] parts = addressText.split(",");
        if (parts.length != 5) {
            logger.error(
                    "Can't get CityArea: Address text doesn't contain 5 parts (Country, City, City-Area, Street, Building) ");
            return "";
        }
        return StringUtils.trim(parts[2]);
    }

    /**
     * pos - string like "32.2444 54.2355"
     * index = 0 - Latitude
     * index = 1 - Longtitude
     *
     * @param pos
     * @param index
     * @return
     */
    private Float getLongLat(String pos, int index) {
        if (StringUtils.isEmpty(pos)) {
            logger.error("Can't get coordinates from pos, it's empty");
            return Float.valueOf(0);
        }
        String[] coords = pos.split(" ");
        assertEquals(2, coords.length);
        return Float.valueOf(coords[index]);
    }


/*
--REMOVE REDUNDANT CITY_AREAS

delete from COORDINATES where id = (select coordinates_id from GEO_OBJECT where id = 42271)
delete from I18NAME where id = (select i18name_id from GEO_OBJECT where id = 42271)
delete from GEO_OBJECT where id = 42271

update GEO_OBJECT set parent_id = 42272 where parent_id = 42271;

--REMOVE ORPHAN COORDINATES

select * from COORDINATES where id not in (select coordinates_id from GEO_OBJECT)

--REMOVE ORPHAN I18NAME-s

select * from I18NAME where id not in (select i18name_id from GEO_OBJECT)


--- GET NUMBER OF BUILDINGS FOR EACH RESOLVED STREET

select go.parent_id, count(*)
from GEO_OBJECT go,
COORDINATES_LOG cl
where go.parent_id = cl.street_id
  and cl.process_time > STR_TO_DATE('01/11/2011', '%d/%m/%Y')
group by go.parent_id;

+-----------+----------+
| parent_id | count(*) |
+-----------+----------+
|         1 |       25 |
|        27 |        1 |
|        29 |       68 |
|        99 |        3 |
|       103 |       48 |
|       153 |       43 |
|       197 |       11 |
|       209 |       11 |
|       221 |       17 |
|       239 |       48 |
|       288 |       44 |
|       333 |        7 |
+-----------+----------+
*/

/*select go.parent_id, count(*)
from GEO_OBJECT go,
COORDINATES_LOG cl
where go.parent_id = cl.street_id
  and cl.process_time > STR_TO_DATE('07/11/2011', '%d/%m/%Y')
group by go.parent_id;*/

/*SELECT go.*, nm.*
  FROM GEO_OBJECT go,
       GEO_OBJECT_TYPE got,
       I18NAME nm
  WHERE got.typeCode = 2
      and go.geo_object_type_id = got.id
      and go.i18name_id = nm.id*/

//#daily_sum_result
/*select count(*) as day_runs, sum(ifnull(resolved_number, 0)) as day_sum, date_format(process_time, '%Y/%m/%d') day_day
  from COORDINATES_LOG
  where resolved_buildings <> 'start'
  group by date_format(process_time, '%Y/%m/%d')*/


//# total buildings: 40178
/*select count(*)
from GEO_OBJECT
  where geo_object_type_id = 100*/

//# today_result
/*select street_id, resolved_buildings, resolved_number, comment, date_format(process_time, '%Y/%m/%d %H:%i') as process_time
  from COORDINATES_LOG
  where date_format(process_time, '%Y/%m/%d') = date_format(now(), '%Y/%m/%d')
  and resolved_buildings <> 'start'*/

/*today_not_resolved*/
/*select *, LENGTH(not_resolved_buildings) - LENGTH(REPLACE(not_resolved_buildings, ' ', '')) #street_id, resolved_buildings, resolved_number, comment, date_format(process_time, '%Y/%m/%d %H:%i') as process_time
  from COORDINATES_LOG
  where date_format(process_time, '%Y/%m/%d') = date_format(now(), '%Y/%m/%d')
  and resolved_buildings <> 'start'
      and not_resolved_buildings <> ''*/


/*daily_sum_not_resolved*/
/*
select count(*) as day_runs, sum(LENGTH(not_resolved_buildings) - LENGTH(REPLACE(not_resolved_buildings, ' ', ''))) as day_sum, date_format(process_time, '%Y/%m/%d') day_day
  from COORDINATES_LOG
  where resolved_buildings <> 'start'
    and not_resolved_buildings <> ''
  group by date_format(process_time, '%Y/%m/%d')*/

}