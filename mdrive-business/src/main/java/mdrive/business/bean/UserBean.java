package mdrive.business.bean;

import mdrive.business.bean.type.ModelBean;
import mdrive.business.helper.ToStringModelBeanHelper;
import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "USER")
public class UserBean implements ModelBean {

    private static final long serialVersionUID = 1L;

    private Long id;
    private UserTypeBean userTypeBean;
    private SettingsBean settingsBean;
    private String userName;
    private List<GoBidBean> goBids;
    private List<GoReplyBean> goReplies;

    public UserBean() {
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
    @JoinColumn(name = "user_type_id", nullable = false)
    @ForeignKey(name = "FK_USER_TYPE")
    public UserTypeBean getUserTypeBean() {
        return userTypeBean;
    }

    public void setUserTypeBean(UserTypeBean userTypeBean) {
        this.userTypeBean = userTypeBean;
    }

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "userBean")
    @ForeignKey(name = "FK_SETTINGS")
    public SettingsBean getSettingsBean() {
        return settingsBean;
    }

    public void setSettingsBean(SettingsBean settingsBean) {
        this.settingsBean = settingsBean;
    }

    @Column(nullable = false)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @OneToMany
    @JoinColumn(name = "user_id")
    public List<GoBidBean> getGoBids() {
        return goBids;
    }

    public void setGoBids(List<GoBidBean> goBids) {
        this.goBids = goBids;
    }

    @OneToMany
    @JoinColumn(name = "user_id")
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