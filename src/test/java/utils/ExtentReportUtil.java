package utils;

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;

import java.io.File;

public class ExtentReportUtil {
    private static ExtentReports report;

    /**
     * This method is used to initialize the html report that will be
     * used to log the test case executions
     *
     * @param htmlReportPath       the path of the generated html report
     * @param htmlReportConfigPath the path of the configuration xml file
     *                             for the html report
     */
    public static void initializeHtmlReport(String htmlReportPath
            , String htmlReportConfigPath) {
        report = new ExtentReports(htmlReportPath
                , true, DisplayOrder.OLDEST_FIRST);

        report.loadConfig(new File(htmlReportConfigPath));
    }

    /**
     * This method is used to get the value of the extent report
     *
     * @return the value of the extent report
     */
    public static ExtentReports getReport() {
        return report;
    }
}
