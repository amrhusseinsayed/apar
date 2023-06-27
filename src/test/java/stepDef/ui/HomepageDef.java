package stepDef.ui;

import io.cucumber.java.en.Then;
import org.json.simple.parser.ParseException;
import stepDefImpl.HomepageImpl;
import utils.ActionsUtil;

import java.io.IOException;

public class HomepageDef extends ActionsUtil {
    private final HomepageImpl homepage = new HomepageImpl();

    /**
     * This method is used to validate the subscription plans for the given country
     *
     * @param country the country name
     */
    @Then("^validates the plans details for (.*)$")
    public void validates_the_plan_details_for(String country) throws IOException, ParseException {
        logInfo(String.format("Validating the subscription plans for %s", country));
        homepage.selectCountry(country);
        homepage.validateSubscriptionPlans(country);
    }
}
