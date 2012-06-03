package mdrive.business.bean;

import mdrive.business.bean.type.ModelBean;
import mdrive.business.helper.ToStringModelBeanHelper;
import mdrive.business.type.GeoObjectTypeCode;
import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;

@Entity
@Table(name = "GEO_OBJECT_TYPE")
public class GeoObjectTypeBean implements ModelBean {

    private static final long serialVersionUID = 1L;

    private Long id;
    private GeoObjectTypeCode typeCode;
    private I18NameBean typeI18Name;

    public GeoObjectTypeBean() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    @Enumerated(EnumType.STRING)
    public GeoObjectTypeCode getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(GeoObjectTypeCode typeCode) {
        this.typeCode = typeCode;
    }

    @OneToOne
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
}