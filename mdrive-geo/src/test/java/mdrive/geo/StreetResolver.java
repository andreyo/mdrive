package mdrive.geo;

import mdrive.geo.exception.GeoException;

import java.util.StringTokenizer;

/**
 * Created by IntelliJ IDEA.
 * User: Elena
 * Date: 09.04.11
 * Time: 23:31
 * To change this template use File | Settings | File Templates.
 */
public class StreetResolver {

//TODO: generate ymap-menu from DB with coordinates, for visual check  (Google as well)

    private static final String city = "Киев";
    private static final String street = "ЄРЕВАНСЬКА ВУЛ.";
    private static final String numbers = "1, 2, 3, 3А, 4, 5, 6, 7, 8, 8А, 9, 10, 10А, 11, 12, 12А, 13, 13/1, 14, 14А, 14Б, 14В, 14Г, 14Д, 15, 16, 16А, 17, 18, 18А, 19, 20, 21, 22, 23, 24, 25, 25/1, 26, 27, 27/1, 28, 28А, 29, 29/1, 30, 31/1, 32,";


    public static void resolveStreet(String street, String numbers) throws GeoException, InterruptedException {
        StringTokenizer stringTokenizer = new StringTokenizer(numbers);
        while (stringTokenizer.hasMoreTokens()) {
            CoordinatesResolver.getCoordinates(city, street, stringTokenizer.nextToken());
            Thread.sleep(2000);
        }
    }

    public static void main(String[] args) throws GeoException, InterruptedException {
        resolveStreet(street, numbers);
    }
}
