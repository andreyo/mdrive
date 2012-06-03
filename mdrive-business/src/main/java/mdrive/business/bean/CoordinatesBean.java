package mdrive.business.bean;

import mdrive.business.bean.embeddable.Point;
import mdrive.business.bean.type.ModelBean;
import mdrive.business.helper.ToStringModelBeanHelper;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "COORDINATES")
public class CoordinatesBean implements ModelBean {

    private static final long serialVersionUID = 1L;

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