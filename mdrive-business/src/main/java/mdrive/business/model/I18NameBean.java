package mdrive.business.model;

import mdrive.business.helper.ToStringModelBeanHelper;
import mdrive.business.type.Constants;

import javax.persistence.*;
import java.util.Locale;

@Entity
@Table(name = I18NameBean.TABLE_NAME)
public class I18NameBean implements ModelBean {

    private static final long serialVersionUID = 1L;
    public static final String TABLE_NAME = "I18NAME";

    private Long id;
    private String valueEn;
    private String valueRu;
    private String valueUk;

    public I18NameBean() {
    }

    public I18NameBean(String value_en, String value_ru, String value_uk) {
        this.valueEn = value_en;
        this.valueRu = value_ru;
        this.valueUk = value_uk;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    @Column(name = "value_en", nullable = true)
    public String getValueEn() {
        return valueEn;
    }

    public void setValueEn(String valueEn) {
        this.valueEn = valueEn;
    }

    @Column(name = "value_ru", nullable = true)
    public String getValueRu() {
        return valueRu;
    }

    public void setValueRu(String valueRu) {
        this.valueRu = valueRu;
    }

    @Column(name = "value_uk", nullable = true)
    public String getValueUk() {
        return valueUk;
    }

    public void setValueUk(String valueUk) {
        this.valueUk = valueUk;
    }

    public String getValue() {
        return getValue(null);
    }

    //TODO: this should be replaced with polymorphism
    public String getValue(Locale locale) {
        //English - is default locale
        if (locale == null || locale.getLanguage().equals(Constants.LOCALE_EN.getLanguage())) {
            return valueEn;
        }
        if (locale.getLanguage().equals(Constants.LOCALE_RU.getLanguage())) {
            return valueRu;
        }
        if (locale.getLanguage().equals(Constants.LOCALE_UK.getLanguage())) {
            return valueUk;
        }
        return valueEn;
    }

    public void setValue(String value) {
        setValue(value, null);
    }

    public void setValue(String value, Locale locale) {
        if (locale == null || locale.equals(Constants.LOCALE_EN)) {
            valueEn = value;
            return;
        }
        if (locale.equals(Constants.LOCALE_RU)) {
            valueRu = value;
            return;
        }
        if (locale.equals(Constants.LOCALE_UK)) {
            valueUk = value;
            return;
        }
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
        if (!(o instanceof I18NameBean)) {
            return false;
        }

        I18NameBean that = (I18NameBean) o;

        if (valueEn != null ? !valueEn.equals(that.valueEn) : that.valueEn != null) {
            return false;
        }
        if (valueRu != null ? !valueRu.equals(that.valueRu) : that.valueRu != null) {
            return false;
        }
        if (valueUk != null ? !valueUk.equals(that.valueUk) : that.valueUk != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = valueEn != null ? valueEn.hashCode() : 0;
        result = 31 * result + (valueRu != null ? valueRu.hashCode() : 0);
        result = 31 * result + (valueUk != null ? valueUk.hashCode() : 0);
        return result;
    }
}