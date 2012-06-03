package custom;

import java.io.Serializable;
import java.util.Date;

/**
 * User: andrey.osipov
 * Date: 4/26/12
 * Time: 3:55 PM
 */
public class AuditBean implements Serializable {

    private String methodName;
    private Date timestamp;
    private String content;

    public AuditBean() {
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
