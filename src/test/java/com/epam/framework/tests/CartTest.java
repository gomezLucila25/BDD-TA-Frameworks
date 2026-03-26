package com.epam.framework.tests;

import com.epam.framework.pages.CartPage;
import com.epam.framework.pages.InventoryPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Feature("Shopping Cart")
public class CartTest extends BaseTest {

    private static final String PRODUCT_NAME = "Sauce Labs Backpack";

    @Story("Add to cart")
    @Description("Adding a product updates the cart badge counter")
    @Test(groups = {"smoke", "regression"})
    public void testAddProductToCart() {
        log.info("TEST: adding product to cart increments badge count");

        InventoryPage inventoryPage = openLoginPage()
                .loginAs(getStandardUser())
                .verifyPageLoaded();

        inventoryPage.addProductToCart(PRODUCT_NAME);

        assertThat(inventoryPage.getCartItemCount()).isEqualTo(1);
        log.info("TEST ASSERTION PASSED: cart badge shows 1");
    }

    @Story("Cart contents")
    @Description("Cart page displays the product added from inventory")
    @Test(groups = {"regression"})
    public void testCartContainsAddedProduct() {
        log.info("TEST: cart page displays the product that was added");

        CartPage cartPage = openLoginPage()
                .loginAs(getStandardUser())
                .addProductToCart(PRODUCT_NAME)
                .goToCart()
                .verifyPageLoaded();

        assertThat(cartPage.getCartItemCount()).isEqualTo(1);
        assertThat(cartPage.getCartItemNames()).contains(PRODUCT_NAME);
        log.info("TEST ASSERTION PASSED: cart contains [{}]", PRODUCT_NAME);
    }

    @Story("Add to cart")
    @Description("Adding two products increments cart badge to 2")
    @Test(groups = {"regression"})
    public void testAddMultipleProducts() {
        log.info("TEST: adding two products increments cart badge to 2");

        InventoryPage inventoryPage = openLoginPage().loginAs(getStandardUser());
        inventoryPage
                .addProductToCart("Sauce Labs Backpack")
                .addProductToCart("Sauce Labs Bike Light");

        assertThat(inventoryPage.getCartItemCount()).isEqualTo(2);
        log.info("TEST ASSERTION PASSED: cart badge shows 2 after adding 2 products");
    }
}
