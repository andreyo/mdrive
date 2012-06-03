package mdrive.report.service.impl;

import mdrive.report.HsqldbTransactionalJUnit4SpringContextTests;
import mdrive.report.model.ReportData;
import mdrive.report.service.ReportService;
import mdrive.report.service.ReportService.JRXMLReportBody;
import mdrive.report.type.ExportFormat;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.test.annotation.DirtiesContext;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


public class ReportEngineServiceImplTestCase extends HsqldbTransactionalJUnit4SpringContextTests {

    @Autowired
    @Qualifier(value = "reportService")
    private ReportService reportService;

    private String reportBodyJrxml;
    private Map<String, Object> parameters;
    protected final DefaultResourceLoader resourceLoader = new DefaultResourceLoader();

    @Before
    public void init() throws Exception {
        super.init();

        InputStream is = resourceLoader.getResource("reports/test_report.jrxml").getInputStream();
        reportBodyJrxml = IOUtils.toString(is);

        parameters = new HashMap<String, Object>();
        parameters.put("START_TIME", "12:59 AM");
    }

    @Test
    @DirtiesContext
    public void testRunReport() throws Exception {

        ReportData reportData = reportService.runReport(new JRXMLReportBody(reportBodyJrxml), parameters);
        assertNotNull(reportData);
        assertNotNull(reportData.getReportBody());
        assertTrue(reportData.getTotalPages() == 1);
    }

    @Test
    @DirtiesContext
    public void testExportToXLS() throws Exception {
        ReportData reportData = reportService.runReport(new JRXMLReportBody(reportBodyJrxml), parameters);
        InputStream is = reportService.export(reportData, ExportFormat.XLS);
        assertNotNull(is);
        iStreamToFile(is, "export.xls");
    }

    private void iStreamToFile(InputStream is, String fileName) throws FileNotFoundException, IOException {
        OutputStream out = new FileOutputStream(fileName);
        out.write(IOUtils.toByteArray(is));
    }
}