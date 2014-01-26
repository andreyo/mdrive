package mdrive.business.model;

import mdrive.business.helper.ToStringModelBeanHelper;
import org.hibernate.annotations.ForeignKey;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = GoBidBean.TABLE_NAME)
public class GoBidBean implements ModelBean {

    private static final long serialVersionUID = 1L;
    public static final String TABLE_NAME = "GO_BID";

    private Long id;
    private UserBean userBean;
    private GeoObjectBean fromGeoObjectBean;
    private GeoObjectBean toGeoObjectBean;
    private GeoObjectBean viaGeoObjectBean;
    private String price;
    private String time;
    private String numberOfPeople;
    private String baggage;
    private String comment;
    private List<GoReplyBean> goReplies = new ArrayList<GoReplyBean>();

    public GoBidBean() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    @ForeignKey(name = "FK_USER")
    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    @OneToOne
    @JoinColumn(name = "from_geo_object_id", nullable = false)
    @ForeignKey(name = "FK_FROM_GEO_OBJECT")
    public GeoObjectBean getFromGeoObjectBean() {
        return fromGeoObjectBean;
    }

    public void setFromGeoObjectBean(GeoObjectBean fromGeoObjectBean) {
        this.fromGeoObjectBean = fromGeoObjectBean;
    }

    @OneToOne
    @JoinColumn(name = "to_geo_object_id", nullable = false)
    @ForeignKey(name = "FK_TO_GEO_OBJECT")
    public GeoObjectBean getToGeoObjectBean() {
        return toGeoObjectBean;
    }

    public void setToGeoObjectBean(GeoObjectBean toGeoObjectBean) {
        this.toGeoObjectBean = toGeoObjectBean;
    }

    @OneToOne
    @JoinColumn(name = "via_geo_object_id", nullable = true)
    @ForeignKey(name = "FK_VIA_GEO_OBJECT")
    public GeoObjectBean getViaGeoObjectBean() {
        return viaGeoObjectBean;
    }

    public void setViaGeoObjectBean(GeoObjectBean viaGeoObjectBean) {
        this.viaGeoObjectBean = viaGeoObjectBean;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Column(name = "time_wait")
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(String numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public String getBaggage() {
        return baggage;
    }

    public void setBaggage(String baggage) {
        this.baggage = baggage;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @OneToMany
    @JoinColumn(name = "go_bid_id")
    public List<GoReplyBean> getGoReplies() {
        return goReplies;
    }

    public void setGoReplies(List<GoReplyBean> goReplies) {
        this.goReplies = goReplies;
    }

    @Override
    public String toString() {
        return ToStringModelBeanHelper.toString(this);
    }
}