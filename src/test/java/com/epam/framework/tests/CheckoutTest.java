package com.epam.framework.tests;

import com.epam.framework.model.Order;
import com.epam.framework.model.Product;
import com.epam.framework.pages.CheckoutPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Feature("Checkout")
public class CheckoutTest extends BaseTest {

    @Story("Complete purchase")
    @Description("Full e2e flow: login → add to cart → checkout → confirm order")
    @Test(groups = {"smoke", "regression"})
    public void testCompleteCheckout() {
        log.info("TEST: full e2e purchase — login > add to cart > checkout > confirm");

        Order order = new Order()
                .addProduct(new Product("Sauce Labs Backpack", 29.99))
                .withShippingInfo("John", "Doe", "12345");

        CheckoutPage checkoutPage = openLoginPage()
                .loginAs(getStandardUser())
                .addProductToCart("Sauce Labs Backpack")
                .goToCart()
                .proceedToCheckout()
                .fillShippingInfo(order)
                .clickContinue();

        log.info("Order total on overview: [{}]", checkoutPage.getOrderTotal());
        checkoutPage.clickFinish();

        assertThat(checkoutPage.isOrderConfirmed()).isTrue();
        assertThat(checkoutPage.getConfirmationMessage()).containsIgnoringCase("Thank you");
        log.info("TEST ASSERTION PASSED: order confirmed with thank you message");
    }

    @Story("Checkout validation")
    @Description("Submitting checkout without first name shows a validation error")
    @Test(groups = {"regression"})
    public void testCheckoutEmptyFirstName() {
        log.info("TEST: empty first name in checkout triggers validation error");

        Order order = new Order().withShippingInfo("", "Doe", "12345");

        CheckoutPage checkoutPage = openLoginPage()
                .loginAs(getStandardUser())
                .addProductToCart("Sauce Labs Backpack")
                .goToCart()
                .proceedToCheckout()
                .fillShippingInfo(order)
                .clickContinue();

        assertThat(checkoutPage.getErrorMessage()).contains("First Name is required");
        log.info("TEST ASSERTION PASSED: first name validation error shown");
    }
}
