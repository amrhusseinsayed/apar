package stepDef.ui;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import stepDefImpl.HomepageImpl;
import utils.ActionsUtil;

public class HomepageDef extends ActionsUtil {
    private HomepageImpl homepage = new HomepageImpl();


    /**
     * This method is used to click on the sign in button
     * inside the homepage
     */
    @When("user clicks on the sign in button")
    public void user_clicks_on_the_sign_in_button() {
        homepage.clickOnSignInButton();
    }

    /**
     * This method is used to assert the the registration or the login
     * is completed by waiting for the sign out button to be visible
     */
    @Then("^verify that the registration is successfully completed$" +
            "|^verify that the login is successfully completed$")
    public void verify_that_the_registration_or_login_is_successfully_completed() {
        homepage.isUserLoggedIn();
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
    @When("user clicks on {string} menu")
    public void user_clicks_on_menu(String menuName) throws Exception {
        homepage.clickOnMenu(menuName);
    }
}
