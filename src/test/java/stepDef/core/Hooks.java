package stepDef.core;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.Status;
import io.cucumber.java.en.Given;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.*;

import java.util.Arrays;
import java.util.Properties;

public class Hooks extends ActionsUtil {
    private final Logger log = LogManager.getLogger(Hooks.class);
    private final ExtentReports report = ExtentReportUtil.getReport();
    private final Properties properties = PropertiesFileUtil.getProperties();

    /**
     * This method is used to navigate to the homepage
     */
    @Given("user navigates to the homepage")
    public void navigate_to() {
        navigateTo(properties.getProperty("homepage"));
    }

    /**
     * This method is executed before each scenario to log that the
     * scenario is started using its name inside the log file and
     * add a new entry for the test case to be executed inside the
     * html report  using its name
     *
     * @param scenario the scenario to be executed
     */
    @Before(order = 0)
    public void before(Scenario scenario) {
        String scenarioName = scenario.getName();

        log.info("---------------------------------------------------------------");
        log.info(String.format("Test Case Name: %s", scenarioName));
        log.info("Status: Started");
        log.info("---------------------------------------------------------------");

        test = report.startTest(scenarioName);
    }

    /**
     * This method is used to initialize the browser instance before
     * any flow scenario
     *
     * @throws Exception in case of not being able to initialize the
     *                   browser instance with the desired data
     */
    @Before(order = 1)
    public void beforeFlow() throws Exception {
        try {
            logInfo("Initializing the browser instance");
            DriverUtil.initializeBrowser();
            driver = DriverUtil.getDriver();
            wait = DriverUtil.getWait();
        } catch (Exception e) {
            logError("Exception while trying to initialize the browser instance");
            logError(String.format("Stack Trace: %s"
                    , Arrays.toString(e.getStackTrace())));
            throw e;
        }
    }

    /**
     * This method is executed after each scenario to log the final
     * results of the executed scenario inside both the log file or
     * the html report, take a screenshot in case of the test case has
     * failed and save it to the html report
     *
     * @param scenario the executed scenario
     * @throws Exception in case of not being able to log the final
     *                   results of the executed scenario in both the log
     *                   file and the html report or not being able to
     *                   take a screenshot in case of having failed test
     *                   case
     */
    @After(order = 1)
    public void after(Scenario scenario) throws Exception {
        logTestRunResults(scenario);
    }

    /**
     * This method is used to close the opened browser instance
     * after any flow scenario
     */
    @After(order = 0)
    public void afterFlow() {
        try {
            logInfo("Closing the running browser instance");
            DriverUtil.closeDriver();
        } catch (Exception e) {
            logError("Exception while trying to close the running browser instance");
            throw e;
        }
    }

    /**
     * This method is executed after each scenario to log the final
     * results of the executed scenario inside both the log file and the
     * html report also to take a screenshot in case of the scenario
     * has failed and save it to the html report
     *
     * @param scenario the executed scenario
     * @throws Exception in case of not being able to log the final
     *                   results of the executed scenario in either the
     *                   log file or the html report or not being able
     *                   to take a screenshot in case of having failed
     *                   scenario
     */
    private void logTestRunResults(Scenario scenario) throws Exception {
        Status scenarioStatus = scenario.getStatus();
        String scenarioName = scenario.getName();

        switch (scenarioStatus) {
            case PASSED:
                logSuccess(String.format("Test Status: %s", "Passed"));
                break;
            case FAILED:
                if (driver != null) {
                    String screenshotsPath = properties.getProperty("screenshotsPath");

                    test.log(LogStatus.ERROR, "Check the following screenshot: "
                            + test.addBase64ScreenShot("data:image/png;charset=utf-8;base64, " +
                            ScreenshotUtil.takeScreenshot(driver
                                    , screenshotsPath
                                    , "Screenshot_" + scenarioName + ".png")));
                }

                logFail(String.format("Test Status: %s", "Failed"));
                break;
            case SKIPPED:
                logSkip(String.format("Test Status: %s", "Skipped"));
                break;
            default:
                logUnknown(String.format("Test Status: %s", "Unknown"));
                break;
        }
    }
}
