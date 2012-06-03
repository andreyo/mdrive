package custom;

/**
 * User: andrey.osipov
 * Date: 4/26/12
 * Time: 3:32 PM
 */

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Reporter {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception {
//        run("custom/test_jasper.jrxml", DataBeanMaker.getDataBeanList(), "D:/test_jasper.pdf");
        run("custom/report_audit.jrxml",
                AuditJRDataSourceFactory.createAuditBeanCollection(),
                "D:/test_jasper_audit.pdf");
    }

    private static void run(String reportName, Collection beansCollection, String outputPath) throws JRException {
        InputStream inputStream = Reporter.class.getClassLoader().getResourceAsStream(reportName);

        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(beansCollection);

        Map parameters = new HashMap();

        JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanColDataSource);
        JasperExportManager.exportReportToPdfFile(jasperPrint, outputPath);
    }
}