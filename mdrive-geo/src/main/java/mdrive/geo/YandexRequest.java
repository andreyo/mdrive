package mdrive.geo;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by IntelliJ IDEA.
 * User: Elena
 * Date: 05.04.11
 * Time: 23:20
 * To change this template use File | Settings | File Templates.
 */
public class YandexRequest {
    public static final String GEOCODE_URL_PRE = "http://geocode-maps.yandex.ru/1.x/?format=xml&geocode=";
    public static final String GEOCODE_URL_POST = "&key=ADFsm00BAAAAPMz_IwMADR7c-L67FP7ZHGmkWhEyNACu_IoAAAAAAAAAAACC5FG9lVMcvpGiElzqdKwD_heKJg==";

    private String city;
    private String street;
    private String buildingNo;

    public YandexRequest(String city, String street, String buildingNo) {
        this.city = city;
        this.street = street;
        this.buildingNo = buildingNo;
    }

    public URL getRequest() throws MalformedURLException {
        String stringUrl = GEOCODE_URL_PRE + city + "," + street + ", " + buildingNo + GEOCODE_URL_POST;
        return new URL(stringUrl);
    }
}