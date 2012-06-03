package mdrive.report.model.tmp;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * User: andrey.osipov
 */

@Entity
@Table(name = "script_registry")
@SequenceGenerator(name = "IdSequenceGenerator", sequenceName = "SEQ_ID", allocationSize = 1)
public class ScriptRegistryEntity implements Serializable {

    public static enum Field {
        id, scriptCode, scriptType, description, updatedOn, data;
    }

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IdSequenceGenerator")
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "script_code", length = 64, nullable = false)
    private String scriptCode;

    @Column(name = "script_type", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private ScriptType scriptType;

    @Column(name = "description", length = 128)
    private String description;

    @Lob
    @Column(name = "data", nullable = false)
    private String data;

    @Column(name = "updated_on", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedOn;

    public ScriptRegistryEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getScriptCode() {
        return scriptCode;
    }

    public void setScriptCode(String scriptCode) {
        this.scriptCode = scriptCode;
    }

    public ScriptType getScriptType() {
        return scriptType;
    }

    public void setScriptType(ScriptType scriptType) {
        this.scriptType = scriptType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("scriptCode", scriptCode)
                .append("scriptType", scriptType)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ScriptRegistryEntity that = (ScriptRegistryEntity) o;

        if (data != null ? !data.equals(that.data) : that.data != null) {
            return false;
        }
        if (description != null ? !description.equals(that.description) : that.description != null) {
            return false;
        }
        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        if (scriptCode != null ? !scriptCode.equals(that.scriptCode) : that.scriptCode != null) {
            return false;
        }
        if (scriptType != that.scriptType) {
            return false;
        }
        if (updatedOn != null ? !updatedOn.equals(that.updatedOn) : that.updatedOn != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (scriptCode != null ? scriptCode.hashCode() : 0);
        result = 31 * result + (scriptType != null ? scriptType.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        result = 31 * result + (updatedOn != null ? updatedOn.hashCode() : 0);
        return result;
    }
}
