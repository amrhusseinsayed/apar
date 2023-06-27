package utils;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.util.Base64;

public class ScreenshotUtil {
    private static Logger log = Logger.getLogger(ScreenshotUtil.class);

    /**
     * This method is used to take a screenshot from the current view
     *
     * @param driver          the WebDriver used to take the screenshot
     * @param screenshotsPath the path of all the screenshots folder
     * @param fileName        the name of the screenshot
     * @return the path of the captured screenshot
     * @throws Exception in case of not being able to take the screenshot
     */
    public static String takeScreenshot(WebDriver driver
            , String screenshotsPath, String fileName) throws Exception {
        var filePath = screenshotsPath + fileName;

        try {
            File scrFile = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.FILE);

            FileUtils.copyFile(scrFile, new File(filePath));

            byte[] fileContent = FileUtils
                    .readFileToByteArray(new File(filePath));

            return Base64.getEncoder().encodeToString(fileContent);
        } catch (Exception e) {
            log.error("Exception while taking the screenshot");
            throw e;
        }
    }
}
