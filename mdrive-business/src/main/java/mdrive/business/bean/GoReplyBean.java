package mdrive.business.bean;

import mdrive.business.bean.type.ModelBean;
import mdrive.business.helper.ToStringModelBeanHelper;
import org.hibernate.annotations.ForeignKey;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "GO_REPLY")
public class GoReplyBean implements ModelBean {

    private static final long serialVersionUID = 1L;

    private Long id;
    private GoBidBean goBidBean;
    private UserBean userBean;
    private String car;
    private String price;
    private String time;

    public GoReplyBean() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "go_bid_id", nullable = false)
    @ForeignKey(name = "FK_GO_BID")
    public GoBidBean getGoBidBean() {
        return goBidBean;
    }

    public void setGoBidBean(GoBidBean goBidBean) {
        this.goBidBean = goBidBean;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @ForeignKey(name = "FK_USER")
    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
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

    @Override
    public String toString() {
        return ToStringModelBeanHelper.toString(this);
    }
}