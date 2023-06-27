package stepDefImpl;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.ActionsUtil;
import utils.JsonFileUtil;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class HomepageImpl extends ActionsUtil {
    private final By row = By.cssSelector(".plan-row .plan-names");
    private final By titleCell = By.cssSelector("div>strong");
    private final By textCell = By.cssSelector("div");
    private final By priceCell = By.cssSelector("div>div");
    private final By imageCell = By.cssSelector("img");

    @SuppressWarnings("unchecked")
    public void validateSubscriptionPlans(String country) throws IOException, ParseException {
        List<JSONObject> subscriptions = (List<JSONObject>) ((JSONObject) JsonFileUtil.readJsonObject("src/test/resources/test-data/subscriptions.json").get("subscriptions")).get(country);
        List<WebElement> rows = findElements(row);

        assert rows.size() == subscriptions.get(0).entrySet().size() + 1;

        subscriptions.forEach(subscription -> {
            int rowIndex = 0;
            int subscriptionIndex = subscriptions.indexOf(subscription);

            isElementTextEquals(rows.get(rowIndex++).findElements(titleCell).get(subscriptionIndex), subscription.get("name").toString());
            isElementTextEquals(rows.get(rowIndex++).findElements(priceCell).get(subscriptionIndex), subscription.get("price").toString());
            isElementTextEquals(rows.get(rowIndex++).findElements(textCell).get(subscriptionIndex), subscription.get("discovery").toString());
            isElementTextEquals(rows.get(rowIndex++).findElements(textCell).get(subscriptionIndex), subscription.get("quality").toString());
            isElementTextEquals(rows.get(rowIndex++).findElements(textCell).get(subscriptionIndex), subscription.get("devices").toString());
            isElementTextEquals(rows.get(rowIndex++).findElements(textCell).get(subscriptionIndex), subscription.get("rewind").toString());
            verifyCellImage(rows.get(rowIndex++).findElements(imageCell).get(subscriptionIndex), subscription.get("offline").toString());
            verifyCellImage(rows.get(rowIndex++).findElements(imageCell).get(subscriptionIndex), subscription.get("simultaneously").toString());
            verifyCellImage(rows.get(rowIndex++).findElements(imageCell).get(subscriptionIndex), subscription.get("cast").toString());
            isElementTextEquals(rows.get(rowIndex++).findElements(textCell).get(subscriptionIndex), subscription.get("discovery").toString());
            isElementTextEquals(rows.get(rowIndex).findElements(textCell).get(subscriptionIndex), subscription.get("fight").toString());
        });
    }

    private void verifyCellImage(WebElement cell, String status) {
        String disabledSign = "https://cdn-stg.jawwy.tv/28/New%20Design/jawwy_close.svg";
        String enabledSign = "https://cdn-stg.jawwy.tv/28/New%20Design/jawwy_check.svg";
        isElementAttributeEquals(cell, "src", Objects.equals(status, "true") ? enabledSign : disabledSign);
    }
}
