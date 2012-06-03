package mdrive.business.bean.embeddable;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Elena
 * Date: 09.05.12
 * Time: 23:39
 * To change this template use File | Settings | File Templates.
 */
@Embeddable
public class Car implements Serializable {

    private String make;
    private String model;
    private Integer yearsOld;
    //etc

    public Car() {
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getYearsOld() {
        return yearsOld;
    }

    public void setYearsOld(Integer yearsOld) {
        this.yearsOld = yearsOld;
    }
}
