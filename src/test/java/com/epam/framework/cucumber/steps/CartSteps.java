package com.epam.framework.cucumber.steps;

import com.epam.framework.model.User;
import com.epam.framework.pages.CartPage;
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
 * Step definitions for cart.feature.
 *
 * Regex annotations are used:
 * - ([^"]*) captures product name strings between quotes
 * - (\d+) captures integer values for item counts
 * - item\(s\) matches literal "item(s)" in the step text
 */
public class CartSteps {

    private static final Logger log = LogManager.getLogger(CartSteps.class);

    private InventoryPage inventoryPage;
    private CartPage cartPage;

    @Given("^the user is logged in as \"([^\"]*)\" with password \"([^\"]*)\"$")
    public void loginAs(String username, String password) {
        log.info("[STEP] Logging in as: [{}]", username);
        inventoryPage = new LoginPage().open().loginAs(new User(username, password));
    }

    @And("^the user is on the inventory page$")
    public void verifyInventoryPageLoaded() {
        log.info("[STEP] Verifying inventory page is loaded");
        inventoryPage.verifyPageLoaded();
    }

    @When("^the user adds \"([^\"]*)\" to the cart$")
    public void addProductToCart(String productName) {
        log.info("[STEP] Adding product to cart: [{}]", productName);
        inventoryPage.addProductToCart(productName);
    }

    /**
     * Uses \d+ regex to capture and validate the expected numeric item count.
     * The literal "(s)" in "item(s)" is escaped as \(s\) to match it verbatim.
     */
    @Then("^the cart badge should show (\\d+) item\\(s\\)$")
    public void verifyCartBadgeCount(int expectedCount) {
        log.info("[STEP] Verifying cart badge shows [{}] item(s)", expectedCount);
        assertThat(inventoryPage.getCartItemCount())
                .as("Cart badge item count")
                .isEqualTo(expectedCount);
    }

    @And("^the user goes to the cart page$")
    public void goToCartPage() {
        log.info("[STEP] Navigating to cart page");
        cartPage = inventoryPage.goToCart();
        cartPage.verifyPageLoaded();
    }

    @Then("^the cart page should contain (\\d+) item\\(s\\)$")
    public void verifyCartPageItemCount(int expectedCount) {
        log.info("[STEP] Verifying cart page contains [{}] item(s)", expectedCount);
        assertThat(cartPage.getCartItemCount())
                .as("Cart page item count")
                .isEqualTo(expectedCount);
    }

    @And("^the cart should include the product \"([^\"]*)\"$")
    public void verifyProductInCart(String productName) {
        log.info("[STEP] Verifying cart includes product: [{}]", productName);
        assertThat(cartPage.getCartItemNames())
                .as("Cart should contain product: " + productName)
                .contains(productName);
    }
}
