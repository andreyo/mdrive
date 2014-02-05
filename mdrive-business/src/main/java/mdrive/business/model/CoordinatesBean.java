package mdrive.business.model;

import mdrive.business.model.embeddable.Point;
import mdrive.business.util.ToStringModelBeanHelper;

import javax.persistence.*;

@Entity
@Table(name = CoordinatesBean.TABLE_NAME)
public class CoordinatesBean implements ModelBean {

    private static final long serialVersionUID = 1L;
    public static final String TABLE_NAME = "COORDINATES";
    public static final String COL_CENTER = "CENTER";

    private Long id;
    private GeoObjectBean geoObjectBean;
    private Point center;
    private Point lowerLeft;
    private Point upperRight;

    public CoordinatesBean() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    private void setId(Long pId) {
        this.id = pId;
    }

    @OneToOne(mappedBy = "coordinatesBean")
    public GeoObjectBean getGeoObjectBean() {
        return geoObjectBean;
    }

    public void setGeoObjectBean(GeoObjectBean geoObjectBean) {
        this.geoObjectBean = geoObjectBean;
    }

    @Embedded
//    @AttributeOverrides(@AttributeOverride(name = Point.LAT, column = @Column(name = "CENTER" + Point.LAT)))
    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    @Embedded
    public Point getLowerLeft() {
        return lowerLeft;
    }

    public void setLowerLeft(Point lowerLeft) {
        this.lowerLeft = lowerLeft;
    }

    @Embedded
    public Point getUpperRight() {
        return upperRight;
    }

    public void setUpperRight(Point upperRight) {
        this.upperRight = upperRight;
    }

    @Override
    public String toString() {
        return ToStringModelBeanHelper.toString(this);
    }
}