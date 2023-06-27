package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import utils.ExtentReportUtil;
import utils.Log4jUtil;
import utils.PropertiesFileUtil;

import java.util.Properties;

@RunWith(Cucumber.class)
@CucumberOptions(features = "./src/test/resources/features/"
        , glue = {"stepDef"}, publish = true
        , plugin = {"pretty"
        , "json:target/cucumber-reports/CucumberTestReport.json"
        , "rerun:target/cucumber-reports/rerun.txt"}
        , tags = "not @ignore")
public class TestRunner {
    /**
     * This method is executed before all the test case to extract
     * the run properties from the config.properties file, initialize
     * the html report and finally to load the configuration
     * of the Log4j through its configuration file path added to
     * the properties file
     *
     * @throws Exception in case of facing an issue while
     *                   initializing any of the mentioned above
     */
    @BeforeClass
    public static void beforeClass() throws Exception {
        String propertyFilePath = "./src/test/resources/configurations/config.properties";
        PropertiesFileUtil.setProperties(propertyFilePath);
        Properties properties = PropertiesFileUtil.getProperties();

        ExtentReportUtil.initializeHtmlReport(properties.getProperty("htmlReportPath")
                , properties.getProperty("htmlReportConfigPath"));
        Log4jUtil.loadConfigurations(properties.getProperty("log4jConfigPath"));
    }

    /**
     * This method is executed after all the scenarios to flush the
     * html report with all the executed scenarios results
     */
    @AfterClass
    public static void afterClass() {
        ExtentReportUtil.getReport().flush();
    }
}
