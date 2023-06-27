package stepDefImpl;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.ActionsUtil;

import java.util.Arrays;

public class HomepageImpl extends ActionsUtil {
    @CacheLookup
    @FindBy(css = "[class='login']")
    private WebElement signInBtn;

    @CacheLookup
    @FindBy(css = "[class='logout']")
    private WebElement signOutBtn;

    @CacheLookup
    @FindBy(xpath = "//ul[not (contains(@style,'display: none;'))]/li/a[@title='T-shirts']")
    private WebElement tShirts;

    public HomepageImpl() {
        PageFactory.initElements(driver, this);
    }

    /**
     * This method is used t click on the sign in button
     * that exists in the homepage
     */
    public void clickOnSignInButton() {
        try {
            clickOn(signInBtn, false);
            logInfo("Click on the sign in button");
        } catch (Exception e) {
            logError("Exception while trying to click on the sign in of the registration form");
            logError(String.format("Stack Trace: %s"
                    , Arrays.toString(e.getStackTrace())));
            throw e;
        }
    }

    /**
     * This method is used to assert the the registration or the login
     * is completed by waiting for the sign out button to be visible
     */
    public void isUserLoggedIn() {
        try {
            logInfo("Waiting for the sign out button to be visible");
            waitForElementVisibility(signOutBtn);
            logInfo("The sign out button is now visible");
        } catch (Exception e) {
            logError("Exception while waiting for the sign out button to be visible");
            logError(String.format("Stack Trace: %s"
                    , Arrays.toString(e.getStackTrace())));
            throw e;
        }
    }

    /**
     * This method is used to click on a menu based on its given
     * name
     *
     * @param menuName the name of the menu
     * @throws Exception in case of not being able to click on the
     *                   menu that matches the given name or in case
     *                   the given name does not match any menu in
     *                   the pae
     */
    public void clickOnMenu(String menuName) throws Exception {
        try {
            switch (menuName.toLowerCase()) {
                case "t-shirts":
                    clickOn(tShirts, false);
                    break;
                case "dress":
                case "women":
                    break;
                default:
                    throw new Exception(String.format("Invalid menu name: '%s'"
                            , menuName));
            }

            logInfo(String.format("Navigate to %s page", menuName));
        } catch (Exception e) {
            logError(String.format("Exception while navigating the the %s page"
                    , menuName));
            logError(String.format("Stack Trace: %s"
                    , Arrays.toString(e.getStackTrace())));
            throw e;
        }
    }
}
