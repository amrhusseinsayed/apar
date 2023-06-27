package utils;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ActionsUtil {
    private static final Logger log = LogManager.getLogger(ActionsUtil.class);
    protected static WebDriver driver;
    protected static FluentWait<WebDriver> wait;
    protected static ExtentTest test;
    private JavascriptExecutor executor;

    // region UI

    /**
     * This method is used to navigate to the given URL
     *
     * @param url the URL to navigate to
     */
    protected void navigateTo(String url) {
        try {
            logInfo(String.format("Navigating to the URL: '%s'", url));
            driver.get(url);
        } catch (Exception e) {
            logError("Exception while navigating to the desired URL");
            logError(String.format("Stack Trace: %s"
                    , Arrays.toString(e.getStackTrace())));
            throw e;
        }
    }

    /**
     * This method is used to find the list of elements that have the
     * given locator
     *
     * @param locator the locator of the desired elements
     * @return the matched web elements
     */
    protected List<WebElement> findElements(By locator) {
        return driver.findElements(locator);
    }

    /**
     * This method is used to find the element that has the given locator
     *
     * @param locator the locator of the desired element
     * @return the matched web element
     */
    protected WebElement findElement(By locator) {
        return driver.findElement(locator);
    }

    /**
     * This method is used to add the given text to the given
     * element after being cleared
     *
     * @param element the element to add the text inside
     * @param text    the text to be added inside the element
     */
    protected void addText(WebElement element, String text) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.clear();
        element.sendKeys(text);
    }

    /**
     * This method is used to click on the given element
     *
     * @param element       the element to click on
     * @param useJavascript a flag to indicate the behavior of the click,
     *                      if true, it will use the Javascript executor to
     *                      perform the action otherwise it will use the normal
     *                      selenium click
     */
    protected void clickOn(WebElement element, boolean useJavascript) {
        wait.until(d -> {
            try {
                if (useJavascript) {
                    executor = (JavascriptExecutor) driver;
                    executor.executeScript("arguments[0].click();"
                            , element);
                } else {
                    element.click();
                }

                return true;
            } catch (Exception e) {
                return false;
            }
        });
    }

    /**
     * This method is used to click on the given element locator
     *
     * @param locator       the locator of the element to click on
     * @param useJavascript a flag to indicate the behavior of the click,
     *                      if true, it will use the Javascript
     *                      executor to perform the action otherwise
     *                      it will use the normal selenium click
     */
    protected void clickOn(By locator, boolean useJavascript) {
        wait.until(d -> {
            try {
                WebElement element = driver.findElement(locator);

                if (useJavascript) {
                    executor = (JavascriptExecutor) driver;
                    executor.executeScript("arguments[0].click();"
                            , element);
                } else {
                    element.click();
                }

                return true;
            } catch (Exception e) {
                return false;
            }
        });
    }

    /**
     * This method is used to wait for the given element
     * to be visible
     *
     * @param element the element to wait for its visibility
     */
    protected void waitForElementVisibility(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * This method is used to wait for the element that has the given locator and text
     *
     * @param locator the locator of the element
     * @param text    the text of the element
     */
    protected void waitForLocatorWithText(By locator, String text) {
        wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    /**
     * This method is used to wait for the given text to be visible
     * within the current html pae
     *
     * @param text the text to wait for
     */
    protected void waitForTextToBeVisible(String text) {
        wait.until(d -> {
            try {
                WebElement body = driver.findElement(By.tagName("body"));
                return body.getText().contains(text);
            } catch (Exception e) {
                return false;
            }
        });
    }

    /**
     * This method is used to select an option from the given list
     * based on its given value
     *
     * @param list  the list to get the value from
     * @param value the value to exist in the list
     */
    public void selectByValue(WebElement list, String value) {
        Select select = new Select(list);
        select.selectByValue(value);
    }

    /**
     * This method is used to select an option from the given list
     * based on its given visible text
     *
     * @param list the list to get the value from
     * @param text the text to exist in the list
     */
    public void selectByText(WebElement list, String text) {
        Select select = new Select(list);
        select.selectByVisibleText(text);
    }

    /**
     * This method is used to switch to the given frame
     * as web element
     *
     * @param frame the frame as web element
     */
    public void switchToFrame(WebElement frame) {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frame));
    }

    /**
     * This method is used to switch to the default content
     */
    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    public void isElementTextEquals(WebElement element, String text) {
        assert Objects.equals(element.getText(), text);
    }

    public void isElementAttributeEquals(WebElement element, String attribute, String value) {
        assert Objects.equals(element.getAttribute(attribute), value);
    }

    // endregion

    // region Logging

    /**
     * This method is used to log an INFO log inside both the log
     * txt file and the html report
     *
     * @param message the message to be logged
     */
    protected void logInfo(String message) {
        log.info(message);
        test.log(LogStatus.INFO, message);
    }

    /**
     * This method is used to log an ERROR log inside both the log
     * txt file and the html report
     *
     * @param message the message to be logged
     */
    protected void logError(String message) {
        log.error(message);
        test.log(LogStatus.ERROR, message);
    }

    /**
     * This method is used to log an INFO log inside the log txt file
     * and a PASS log inside the html report
     *
     * @param message the message to be logged
     */
    protected void logSuccess(String message) {
        log.info(message);
        test.log(LogStatus.PASS, message);
    }

    /**
     * This method is used to log an ERROR log inside the log txt file
     * and a FAIL log inside the html report
     *
     * @param message the message to be logged
     */
    protected void logFail(String message) {
        log.error(message);
        test.log(LogStatus.FAIL, message);
    }

    /**
     * This method is used to log an INFO log inside the log txt file
     * and a SKIP log inside the html report
     *
     * @param message the message to be logged
     */
    protected void logSkip(String message) {
        log.info(message);
        test.log(LogStatus.SKIP, message);
    }

    /**
     * This method is used to log an INFO log inside the log txt file
     * and an UNKNOWN log inside the html report
     *
     * @param message the message to be logged
     */
    protected void logUnknown(String message) {
        log.info(message);
        test.log(LogStatus.UNKNOWN, message);
    }

    // endregion
}
