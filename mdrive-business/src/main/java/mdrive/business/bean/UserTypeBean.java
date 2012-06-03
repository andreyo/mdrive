package mdrive.business.bean;

import mdrive.business.bean.type.ModelBean;
import mdrive.business.helper.ToStringModelBeanHelper;
import org.hibernate.annotations.ForeignKey;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "USER_TYPE")
public class UserTypeBean implements ModelBean {

    private static final long serialVersionUID = 1L;

    private Long id;
    private I18NameBean typeI18Name;

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
    public I18NameBean getTypeI18Name() {
        return typeI18Name;
    }

    public void setTypeI18Name(I18NameBean typeI18Name) {
        this.typeI18Name = typeI18Name;
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

        if (typeI18Name != null ? !typeI18Name.equals(that.typeI18Name) : that.typeI18Name != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return typeI18Name != null ? typeI18Name.hashCode() : 0;
    }
}