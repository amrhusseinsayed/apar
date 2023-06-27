package stepDefImpl;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.ActionsUtil;
import utils.JsonFileUtil;

import java.io.IOException;
import java.util.List;

public class HomepageImpl extends ActionsUtil {
    private final By row = By.cssSelector(".plan-row .plan-names");
    private final By titleCell = By.cssSelector("div>strong");
    private final By textCell = By.cssSelector("div");
    private final By priceCell = By.cssSelector("div.price");
    private final By imageCell = By.cssSelector("img");
    private final By selectedCountry = By.id("country-name");

    /**
     * This method is used to validate the subscription plans for the given country
     *
     * @param country the country name
     * @throws IOException    in case of having invalid path for the file
     * @throws ParseException in case of not being able to parse the given file into a json
     */
    @SuppressWarnings("unchecked")
    public void validateSubscriptionPlans(String country) throws IOException, ParseException {
        List<JSONObject> subscriptions = (List<JSONObject>) ((JSONObject) JsonFileUtil.readJsonObject("src/test/resources/test-data/subscriptions.json").get("subscriptions")).get(country);
        List<WebElement> rows = findElements(row);

        assert rows.size() == subscriptions.get(0).size() + 1;

        subscriptions.forEach(subscription -> {
            int rowIndex = 0;
            int subscriptionIndex = subscriptions.indexOf(subscription);

            isElementTextEquals(rows.get(rowIndex++).findElements(titleCell).get(subscriptionIndex), subscription.get("name").toString());
            isElementTextEquals(rows.get(rowIndex++).findElements(priceCell).get(subscriptionIndex), subscription.get("price").toString());
            isElementTextEquals(rows.get(rowIndex++).findElements(textCell).get(subscriptionIndex), subscription.get("discovery").toString());

            if (subscription.containsKey("freeTrial"))
                verifyCellImage(rows.get(rowIndex++).findElements(imageCell).get(subscriptionIndex), (Boolean) subscription.get("freeTrial"));

            isElementTextEquals(rows.get(rowIndex++).findElements(textCell).get(subscriptionIndex), subscription.get("quality").toString());
            isElementTextEquals(rows.get(rowIndex++).findElements(textCell).get(subscriptionIndex), subscription.get("devices").toString());
            isElementTextEquals(rows.get(rowIndex++).findElements(textCell).get(subscriptionIndex), subscription.get("rewind").toString());
            verifyCellImage(rows.get(rowIndex++).findElements(imageCell).get(subscriptionIndex), (Boolean) subscription.get("offline"));
            checkSimultaneousDevices(rows.get(rowIndex++), subscriptionIndex, subscription.get("simultaneously"));
            verifyCellImage(rows.get(rowIndex++).findElements(imageCell).get(subscriptionIndex), (Boolean) subscription.get("cast"));
            isElementTextEquals(rows.get(rowIndex++).findElements(textCell).get(subscriptionIndex), subscription.get("discovery").toString());
            isElementTextEquals(rows.get(rowIndex).findElements(textCell).get(subscriptionIndex), subscription.get("fight").toString());
        });
    }

    /**
     * This method is used to check the image of the given cell
     *
     * @param cell   the cell to be checked
     * @param status if true, it will check on the existence of the checked image, otherwise the closing one
     */
    private void verifyCellImage(WebElement cell, Boolean status) {
        String disabledSign = "https://cdn-stg.jawwy.tv/28/New%20Design/jawwy_close.svg";
        String enabledSign = "https://cdn-stg.jawwy.tv/28/New%20Design/jawwy_check.svg";
        isElementAttributeEquals(cell, "src", status ? enabledSign : disabledSign);
    }

    /**
     * This method is used to check the simultaneous devices for the given row
     *
     * @param row               the row to be checked
     * @param subscriptionIndex the index of the subscription to be checked
     * @param devices           the number of simultaneous devices if any
     */
    public void checkSimultaneousDevices(WebElement row, int subscriptionIndex, Object devices) {
        if (devices != null) {
            isElementTextEquals(row.findElements(textCell).get(subscriptionIndex), devices.toString());
        } else {
            verifyCellImage(row.findElements(imageCell).get(subscriptionIndex), false);
        }
    }

    /**
     * This method is used to select the given country if not selected by default
     *
     * @param country the country to be selected
     */
    public void selectCountry(String country) {
        WebElement element = findElement(selectedCountry);

        if (!element.getText().equals(country)) {
            clickOn(element, false);
            clickOn(By.cssSelector(String.format("[src$='%s.svg']", country)), false);
            waitForLocatorWithText(selectedCountry, country);
        }
    }
}
