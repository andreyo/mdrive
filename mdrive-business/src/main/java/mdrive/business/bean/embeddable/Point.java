package mdrive.business.bean.embeddable;

import org.apache.commons.lang.StringUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Elena
 * Date: 13.05.12
 * Time: 20:56
 * To change this template use File | Settings | File Templates.
 */
@Embeddable
public class Point implements Serializable {

    private static final int PRECISION = 8;
    private static final int SCALE = 6;

    private Float latitude;
    private Float longitude;

    public Point() {
    }

    public Point(Float latitude, Float longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Point(String latlong) {
        if (StringUtils.isEmpty(latlong)) {
            throw new RuntimeException("incorrect format of 'lat long' " + latlong);
        }
        String[] coords = latlong.split(" ");
        latitude = Float.valueOf(coords[0]);
        longitude = Float.valueOf(coords[1]);
    }

    @Column(precision = PRECISION, scale = SCALE) //example 55.750778
    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    @Column(precision = PRECISION, scale = SCALE) //example 55.750778
    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }
}
