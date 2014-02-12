package mdrive.business.model;

import mdrive.business.util.ToStringModelBeanHelper;
import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;

@Entity
@Table(name = UserTypeBean.TABLE_NAME)
public class UserTypeBean implements ModelBean {

    private static final long serialVersionUID = 1L;
    public static final String TABLE_NAME = "USER_TYPE";

    private Long id;
    private I18NameBean name;

    public UserTypeBean() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "i18name_id", nullable = false)
    @ForeignKey(name = "FK_I18NAME")
    public I18NameBean getName() {
        return name;
    }

    public void setName(I18NameBean name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return ToStringModelBeanHelper.toString(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserTypeBean)) {
            return false;
        }

        UserTypeBean that = (UserTypeBean) o;

        if (name != null ? !name.equals(that.name) : that.name != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}