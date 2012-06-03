package mdrive.business.bean.embeddable;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Elena
 * Date: 09.05.12
 * Time: 23:52
 * To change this template use File | Settings | File Templates.
 */
@Embeddable
public class UISetup implements Serializable {
    private String colorScheme;
    private String navigationType;

    public UISetup() {
    }

    public String getColorScheme() {
        return colorScheme;
    }

    public void setColorScheme(String colorScheme) {
        this.colorScheme = colorScheme;
    }

    public String getNavigationType() {
        return navigationType;
    }

    public void setNavigationType(String navigationType) {
        this.navigationType = navigationType;
    }
}
