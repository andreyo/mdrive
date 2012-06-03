package mdrive.report.service.impl;

import mdrive.report.model.ReportData;
import mdrive.report.service.ReportService;
import mdrive.report.type.ExportFormat;
import mdrive.report.util.ReportUtil;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JROrigin;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRCsvExporterParameter;
import net.sf.jasperreports.engine.export.JROriginExporterFilter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.fill.JRBaseFiller;
import net.sf.jasperreports.engine.fill.JRFillInterruptedException;
import net.sf.jasperreports.engine.fill.JRFiller;
import net.sf.jasperreports.engine.fill.JRSwapFileVirtualizer;
import net.sf.jasperreports.engine.util.JRSwapFile;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


@Service(value = "reportService")
public class ReportServiceImpl implements ReportService {

    @Autowired
    @Qualifier(value = "dataSource")
    private DataSource dataSource;

    /*@Autowired
    private ConfigOptionService configOptionService;*/

    public static final String REPORT_COUNT_VARIABLE_NAME = "REPORT_COUNT";

    //public static final String NO_REPORT_DATA_MESSAGE = "No Records Found";
    //public static final String NO_REPORT_DATA_MESSAGE_PARAMETER_NAME = "NO_REPORT_DATA_MESSAGE";

    public static final String EXPORT_REPORT_FORMAT_PARAMETER_NAME = "EXPORT_REPORT_FORMAT";

    public static final String DATE_CREATED_ON_PARAMETER_NAME = "DATE_CREATED_ON";

    //    private static final String REPORT_EXPORT_CSV_DELIMITER = "report.export.csv.delimiter";
    private static final String DEFAULT_EXPORT_CSV_DELIMITER = ";";

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /*public void setConfigOptionService(ConfigOptionService configOptionService) {
        this.configOptionService = configOptionService;
    }*/

    public ReportData runReport(JRXMLReportBody jrxmlReportBody, Map<String, Object> parametersMap) throws JRException {
        JasperPrint jasperPrint = prepareAndRun(jrxmlReportBody.getXml(), parametersMap, ExportFormat.HTML);
        return new ReportData(jasperPrint, parametersMap, jrxmlReportBody.getXml());
    }

    private JasperPrint prepareAndRun(String jrxmlReportBody, Map<String, Object> parametersMap,
                                      ExportFormat exportFormat) throws JRException {
        InputStream jrxmlInputStream = new ByteArrayInputStream(jrxmlReportBody.getBytes());
        JasperDesign jasperDesign = JRXmlLoader.load(jrxmlInputStream);
        tuneJasperDesign(jasperDesign, exportFormat);
        return runReport(jasperDesign, parametersMap, exportFormat);
    }

    private void tuneJasperDesign(JasperDesign jasperDesign, ExportFormat exportFormat) {
        if (ExportFormat.PDF.equals(exportFormat)) {
            jasperDesign.setPageHeight(595);
            jasperDesign.setPageWidth(842);
            jasperDesign.setLeftMargin(30);
            jasperDesign.setRightMargin(30);
            jasperDesign.setTopMargin(20);
            jasperDesign.setBottomMargin(20);
        } else if (ExportFormat.XLS.equals(exportFormat)) {
            jasperDesign.setIgnorePagination(true);
        }
    }

    private JasperPrint runReport(JasperDesign jasperDesign, Map<String, Object> parametersMap,
                                  ExportFormat exportFormat, JRDataSource jrDataSource) throws JRException {
        return null;
    }

    private JasperPrint runReport(JasperDesign jasperDesign, Map<String, Object> parametersMap,
                                  ExportFormat exportFormat) throws JRException {

        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = null;
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            JRSwapFileVirtualizer virtualizer = null;

            String swapDir = createTempDir().getAbsolutePath();
            Map<String, Object> reportParametersMap = new HashMap<String, Object>();
            reportParametersMap.putAll(parametersMap);

            //reportParametersMap.put(NO_REPORT_DATA_MESSAGE_PARAMETER_NAME, NO_REPORT_DATA_MESSAGE);
            reportParametersMap.put(EXPORT_REPORT_FORMAT_PARAMETER_NAME, exportFormat.name());
            reportParametersMap.put(DATE_CREATED_ON_PARAMETER_NAME, ReportUtil.getGMTDateTime(new Date()));

            if (swapDir != null) {
                JRSwapFile swapFile = new JRSwapFile(swapDir, 1024, 1024);
                virtualizer = new JRSwapFileVirtualizer(2, swapFile, true);
                reportParametersMap.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);
                //parametersMap.put(JRParameter.IS_IGNORE_PAGINATION, true);
            }

            JRBaseFiller filler = JRFiller.createFiller(jasperReport);
            try {
                jasperPrint = filler.fill(reportParametersMap, conn);
            } catch (JRFillInterruptedException e) {
                throw new JRException("The report filling thread was interrupted.");
            }

            parametersMap.put(REPORT_COUNT_VARIABLE_NAME,
                    filler.getMainDataset().getVariableValue(REPORT_COUNT_VARIABLE_NAME));

            if (virtualizer != null) {
                virtualizer.setReadOnly(true);
            }
        } catch (SQLException sqle) {
            throw new JRException("Error while executing report sql: " + sqle.getMessage(), sqle);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception sqle) {
                    sqle.printStackTrace(System.err);
                }
            }
        }
        return jasperPrint;
    }

    private Map<String, Object> convertToMap(List parameters) {
        Map<String, Object> parametersMap = new HashMap<String, Object>();
        for (int i = 0; i < parameters.size(); i++) {
            parametersMap.put("P" + i, parameters.get(i));
        }
        return parametersMap;
    }

    public static File createTempDir() {
        String baseTempPath = System.getProperty("java.io.tmpdir");

        if (!(baseTempPath.endsWith("/") || baseTempPath.endsWith("\\"))) {
            baseTempPath = baseTempPath + System.getProperty("file.separator", "/");
        }

        Random rand = new Random();
        int randomInt = 1 + rand.nextInt();

        File tempDir = new File(baseTempPath + "jasperswap" + randomInt);
        if (!tempDir.exists()) {
            tempDir.mkdir();
        }

        tempDir.deleteOnExit();

        return tempDir;
    }

    public InputStream export(ReportData reportData, ExportFormat exportFormat) throws JRException {
        JRExporter exporter = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        switch (exportFormat) {
            case CSV: {
                exporter = new JRCsvExporter();

                exporter.setParameter(JRCsvExporterParameter.FIELD_DELIMITER, DEFAULT_EXPORT_CSV_DELIMITER);
                exporter.setParameter(JRCsvExporterParameter.IGNORE_PAGE_MARGINS, true);


                JROriginExporterFilter jrOriginExporterFilter = new JROriginExporterFilter();
                jrOriginExporterFilter.addOrigin(new JROrigin(JROrigin.COLUMN_HEADER), true);
                jrOriginExporterFilter.addOrigin(new JROrigin(JROrigin.PAGE_HEADER), true);
                jrOriginExporterFilter.addOrigin(new JROrigin(JROrigin.PAGE_FOOTER), false);
                exporter.setParameter(JRCsvExporterParameter.FILTER, jrOriginExporterFilter);
                break;
            }
            case XLS: {
                exporter = new JExcelApiExporter();
                exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
                exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
                exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
                exporter.setParameter(JRXlsExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
                exporter.setParameter(JRXlsExporterParameter.IS_FONT_SIZE_FIX_ENABLED, Boolean.TRUE);

                JROriginExporterFilter jrOriginExporterFilter = new JROriginExporterFilter();
                jrOriginExporterFilter.addOrigin(new JROrigin(JROrigin.COLUMN_HEADER), true);
                jrOriginExporterFilter.addOrigin(new JROrigin(JROrigin.PAGE_HEADER), true);
                jrOriginExporterFilter.addOrigin(new JROrigin(JROrigin.PAGE_FOOTER), true);
                exporter.setParameter(JRXlsExporterParameter.FILTER, jrOriginExporterFilter);
                break;
            }
            case PDF: {
                exporter = new JRPdfExporter();
            }
        }

        JasperPrint jasperPrint = prepareAndRun(reportData.getJasperSourceScript(),
                reportData.getParametersMap(),
                exportFormat);

        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, byteArrayOutputStream);
        exporter.exportReport();

        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return new ByteArrayInputStream(byteArray);
    }


}
