package mdrive.business.model;

import mdrive.business.util.ToStringModelBeanHelper;
import mdrive.business.type.GeoObjectTypeCode;
import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;

@Entity
@Table(name = GeoObjectTypeBean.TABLE_NAME)
public class GeoObjectTypeBean implements ModelBean {

    private static final long serialVersionUID = 1L;
    public static final String TABLE_NAME = "GEO_OBJECT_TYPE";

    private Long id;
    private GeoObjectTypeCode typeCode;
    private I18NameBean name;

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
}