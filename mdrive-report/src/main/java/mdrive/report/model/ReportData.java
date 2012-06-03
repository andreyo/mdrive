package mdrive.report.model;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.design.JasperDesign;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ReportData implements Serializable {

    private JasperPrint reportBody;

    private Map<String, Object> parametersMap;

    private String jasperSourceScript;

    public ReportData(JasperPrint reportBody, Map<String, Object> parametersMap, String jasperSourceScript) {
        this.reportBody = reportBody;
        this.parametersMap = parametersMap;
        this.jasperSourceScript = jasperSourceScript;
    }

    public JasperPrint getReportBody() {
        return reportBody;
    }

    public int getTotalPages() {
        return reportBody.getPages().size();
    }

    public Map<String, Object> getParametersMap() {
        return parametersMap;
    }

    public void setParametersMap(Map<String, Object> parametersMap) {
        this.parametersMap = parametersMap;
    }

    public String getJasperSourceScript() {
        return jasperSourceScript;
    }

    public void setJasperSourceScript(String jasperSourceScript) {
        this.jasperSourceScript = jasperSourceScript;
    }

    @Override
    public String toString() {
        ToStringBuilder toStringBuilder = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        toStringBuilder.append("parameterMap", parametersMap);
        //toStringBuilder.append("jasperSourceScript", jasperSourceScript);
        return toStringBuilder.toString();
    }
}
