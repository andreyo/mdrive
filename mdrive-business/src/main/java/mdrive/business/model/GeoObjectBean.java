package mdrive.business.model;

import mdrive.business.util.ToStringModelBeanHelper;
import org.hibernate.annotations.ForeignKey;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@NamedQueries(value = {
        @NamedQuery(name = GeoObjectBean.GET_BUILDINGS_BY_STREET_ID,
                query = "select goBuildings from GeoObjectBean goBuildings "
                        + "inner join goBuildings.parentGeoObjectBean goStreets " + "join fetch goBuildings.coordinatesBean "
                        + "where goBuildings.geoObjectTypeBean.typeCode = 'BUILDING' and goStreets.id = :streetId"),
        @NamedQuery(name = GeoObjectBean.GET_TOTAL_UNRESOLVED_BUILDINGS_LEFT,
                query = "select count(goBuildings) from GeoObjectBean goBuildings where goBuildings.geoObjectTypeBean.typeCode = 'BUILDING' "
                        + "and (coordinatesBean.center.latitude = null or coordinatesBean.center.longitude = null)")
})
@Entity
@Table(name = GeoObjectBean.TABLE_NAME,
        indexes = {
                @Index(name = GeoObjectBean.GEO_OBJECT_TYPE_ID, columnList = GeoObjectBean.GEO_OBJECT_TYPE_ID)
        }
)
public class GeoObjectBean implements ModelBean {

    private static final long serialVersionUID = 1L;
    public static final String TABLE_NAME = "GEO_OBJECT";
    public static final String GET_BUILDINGS_BY_STREET_ID = TABLE_NAME + ".getBuildingsByStreetId";
    public static final String GET_TOTAL_UNRESOLVED_BUILDINGS_LEFT = TABLE_NAME + ".getTotalUnresolvedBuildingsLeft";

    private Long id;
    private CoordinatesBean coordinatesBean;
    private GeoObjectTypeBean geoObjectTypeBean;
    private I18NameBean objectI18Name;
    private GeoObjectBean parentGeoObjectBean;

    public GeoObjectBean() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "coordinates_id", nullable = false)
    @ForeignKey(name = "FK_COORDINATES")
    public CoordinatesBean getCoordinatesBean() {
        return coordinatesBean;
    }

    public void setCoordinatesBean(CoordinatesBean coordinatesBean) {
        this.coordinatesBean = coordinatesBean;
    }


    static final String GEO_OBJECT_TYPE_ID = "geo_object_type_id";

    @OneToOne
    @JoinColumn(name = GEO_OBJECT_TYPE_ID, nullable = false)
    @ForeignKey(name = "FK_GEO_OBJECT_TYPE")
    public GeoObjectTypeBean getGeoObjectTypeBean() {
        return geoObjectTypeBean;
    }

    public void setGeoObjectTypeBean(GeoObjectTypeBean geoObjectTypeBean) {
        this.geoObjectTypeBean = geoObjectTypeBean;
    }

    @OneToOne
    @JoinColumn(name = "i18name_id", nullable = false)
    @ForeignKey(name = "FK_I18NAME")
    public I18NameBean getObjectI18Name() {
        return objectI18Name;
    }

    public void setObjectI18Name(I18NameBean objectI18Name) {
        this.objectI18Name = objectI18Name;
    }

    @OneToOne
    @JoinColumn(name = "parent_id")
    @ForeignKey(name = "FK_PARENT_GEO_OBJECT")
    public GeoObjectBean getParentGeoObjectBean() {
        return parentGeoObjectBean;
    }

    public void setParentGeoObjectBean(GeoObjectBean parentGeoObjectBean) {
        this.parentGeoObjectBean = parentGeoObjectBean;
    }

    @Override
    public String toString() {
        return ToStringModelBeanHelper.toString(this);
    }
}