package mdrive.report.util;

import mdrive.report.model.ReportData;
import mdrive.report.service.impl.ReportServiceImpl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


public class ReportUtil {

    public static final String dateTimeformat = "yyyy-MM-dd hh:mm:ss.ssss a z";

    public static String getGMTDateTime(String pattern, Date date) {
        DateFormat df = new SimpleDateFormat(pattern);
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        return df.format(date);
    }

    public static String getGMTDateTime(Date date) {
        return getGMTDateTime(dateTimeformat, date);
    }

    public static Integer getReportDataRowsCount(ReportData reportData) {
        return (Integer) reportData.getParametersMap().get(ReportServiceImpl.REPORT_COUNT_VARIABLE_NAME);
    }

}