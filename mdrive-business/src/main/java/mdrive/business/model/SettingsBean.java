package mdrive.business.model;

import mdrive.business.model.embeddable.Car;
import mdrive.business.model.embeddable.UISetup;
import mdrive.business.model.type.ModelBean;
import mdrive.business.helper.ToStringModelBeanHelper;
import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = SettingsBean.TABLE_NAME)
public class SettingsBean implements ModelBean {

    private static final long serialVersionUID = 1L;
    public static final String TABLE_NAME = "SETTINGS";

    private Long id;
    private UserBean userBean;
    private Set<String> recentLocations = new TreeSet<String>();
    private Map<Integer, String> statistics = new TreeMap<Integer, String>();
    private Car prefferedCar;
    private Car anotherCar;
    private List<UISetup> uiSetups = new ArrayList<UISetup>();

    public SettingsBean() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ForeignKey(name = "FK_USER")
    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    @ElementCollection
    @CollectionTable(name = "RECENT_LOCATIONS", joinColumns = @JoinColumn(name = "settings_id"))
    public Set<String> getRecentLocations() {
        return recentLocations;
    }

    public void setRecentLocations(Set<String> recentLocations) {
        this.recentLocations = recentLocations;
    }

    @ElementCollection
    public Map<Integer, String> getStatistics() {
        return statistics;
    }

    public void setStatistics(Map<Integer, String> statistics) {
        this.statistics = statistics;
    }

    @Embedded
    public Car getPrefferedCar() {
        return prefferedCar;
    }

    public void setPrefferedCar(Car prefferedCar) {
        this.prefferedCar = prefferedCar;
    }

    @Embedded
    public Car getAnotherCar() {
        return anotherCar;
    }

    public void setAnotherCar(Car anotherCar) {
        this.anotherCar = anotherCar;
    }

    @ElementCollection
    public List<UISetup> getUiSetups() {
        return uiSetups;
    }

    public void setUiSetups(List<UISetup> uiSetups) {
        this.uiSetups = uiSetups;
    }

    @Override
    public String toString() {
        return ToStringModelBeanHelper.toString(this);
    }
}