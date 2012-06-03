package mdrive.report.service;

import mdrive.report.model.ReportData;
import mdrive.report.type.ExportFormat;
import net.sf.jasperreports.engine.JRException;

import java.io.InputStream;
import java.util.Map;

public interface ReportService {

    ReportData runReport(JRXMLReportBody jrxmlReportBody, Map<String, Object> parametersMap) throws JRException;

    InputStream export(ReportData reportData, ExportFormat exportFormat) throws JRException;

    /**
     * Report Body holder
     */
    public static final class JRXMLReportBody {

        private final String xml;

        public JRXMLReportBody(String xml) {
            this.xml = xml;
        }

        public String getXml() {
            return xml;
        }

        @Override
        public String toString() {
            return "[Report Body Holder]";
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((xml == null) ? 0 : xml.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            JRXMLReportBody other = (JRXMLReportBody) obj;
            if (xml == null) {
                if (other.xml != null) {
                    return false;
                }
            } else if (!xml.equals(other.xml)) {
                return false;
            }
            return true;
        }

    }
}