package com.epam.framework.cucumber.steps;

import com.epam.framework.pages.InventoryPage;
import com.epam.framework.pages.LoginPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for login.feature.
 *
 * Regex annotations are used for parametrization and flexibility:
 * - ([^"]*) captures any string between quotes
 * - (succeed|fail) filters/restricts valid values via alternation
 */
public class LoginSteps {

    private static final Logger log = LogManager.getLogger(LoginSteps.class);

    private LoginPage loginPage;
    private InventoryPage inventoryPage;

    @Given("^the user is on the SauceDemo login page$")
    public void openLoginPage() {
        log.info("[STEP] Opening SauceDemo login page");
        loginPage = new LoginPage().open();
    }

    @When("^the user enters username \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void enterCredentials(String username, String password) {
        log.info("[STEP] Entering credentials — username: [{}]", username);
        loginPage.enterCredentials(username, password);
    }

    @And("^the user clicks the login button$")
    public void clickLoginButton() {
        log.info("[STEP] Clicking login button");
        loginPage.clickLoginButton();
    }

    @Then("^the user should see the inventory page with title \"([^\"]*)\"$")
    public void verifyInventoryPageTitle(String expectedTitle) {
        log.info("[STEP] Verifying inventory page title is: [{}]", expectedTitle);
        inventoryPage = new InventoryPage();
        inventoryPage.verifyPageLoaded();
        assertThat(inventoryPage.getPageTitle())
                .as("Inventory page title")
                .isEqualTo(expectedTitle);
    }

    /**
     * Uses regex alternation (succeed|fail) to filter and validate expected outcomes.
     * This makes the step reusable across multiple scenarios with strict value control.
     */
    @Then("^the login attempt should \"(succeed|fail)\"$")
    public void verifyLoginOutcome(String outcome) {
        log.info("[STEP] Verifying login outcome: [{}]", outcome);
        if ("succeed".equals(outcome)) {
            inventoryPage = new InventoryPage();
            inventoryPage.verifyPageLoaded();
            assertThat(inventoryPage.getPageTitle())
                    .as("Inventory page should be displayed after successful login")
                    .isEqualTo("Products");
        } else {
            assertThat(loginPage.isErrorDisplayed())
                    .as("Error message should be displayed after failed login")
                    .isTrue();
        }
    }

    @When("^the user logs out$")
    public void logout() {
        log.info("[STEP] Logging out");
        if (inventoryPage == null) {
            inventoryPage = new InventoryPage();
        }
        loginPage = inventoryPage.logout();
    }

    @Then("^the user should be back on the login page$")
    public void verifyBackOnLoginPage() {
        log.info("[STEP] Verifying user is back on the login page");
        assertThat(loginPage.isErrorDisplayed())
                .as("No error should be shown on the login page after logout")
                .isFalse();
    }
}
