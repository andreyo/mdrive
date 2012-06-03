package mdrive.geo;

import mdrive.geo.exception.GeoException;
import net.opengis.gml.FeatureMemberType;
import net.opengis.gml.MetaDataPropertyType;
import org.xml.sax.SAXException;
import ru.yandex.maps.ymaps._1.GeoObjectType;
import ru.yandex.maps.ymaps._1.YmapsType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Elena
 * Date: 09.04.11
 * Time: 12:22
 * To change this template use File | Settings | File Templates.
 */
public class CoordinatesResolver {

    public static void getCoordinates(String city, String street, String buildingNo) throws GeoException {
        JAXBElement<YmapsType> result = null;
        try {
            URL url = new YandexRequest(city, street, buildingNo).getRequest();
            result = callAndUnmarshall(url);
        } catch (Exception e) {
            throw new GeoException(e);
        }
        processResult(result);
    }

    private static JAXBElement<YmapsType> callAndUnmarshall(
            URL url) throws JAXBException, IOException, SAXException, ParserConfigurationException {
        JAXBContext jc = JAXBContext.newInstance("ru.yandex.maps.ymaps._1");
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        JAXBElement<YmapsType> result = (JAXBElement<YmapsType>) unmarshaller.unmarshal(url);
        return result;
    }

    private static void processResult(JAXBElement<YmapsType> result) {
        YmapsType ymapsType = (YmapsType) result.getValue();
        MetaDataPropertyType metaDataPropertyType = ymapsType.getGeoObjectCollection().getMetaDataProperty();
        System.out.print("found = " + metaDataPropertyType.getGeocoderResponseMetaData().getFound() + " ");

        List<FeatureMemberType> featureMemberList = ymapsType.getGeoObjectCollection().getFeatureMember();
        for (FeatureMemberType featureMemberType : featureMemberList) {
            GeoObjectType geoObjectType = featureMemberType.getGeoObject();
            System.out.print(geoObjectType.getPoint().getPos() + ", ");
            System.out.println(geoObjectType.getMetaDataProperty().getGeocoderMetaData().getText());
        }
    }
}