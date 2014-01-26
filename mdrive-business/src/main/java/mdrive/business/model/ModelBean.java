package mdrive.business.model;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Elena
 * Date: 24.05.11
 * Time: 23:34
 * To change this template use File | Settings | File Templates.
 */
public interface ModelBean extends Serializable {

    //for ToStringModelBeanHelper and lazy loading
    Long getId();

}
