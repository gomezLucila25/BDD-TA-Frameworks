package com.epam.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class CartPage extends BasePage {

    @FindBy(css = ".title")
    private WebElement pageTitle;

    @FindBy(css = ".cart_item")
    private List<WebElement> cartItems;

    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    @FindBy(id = "continue-shopping")
    private WebElement continueShoppingButton;

    public CartPage verifyPageLoaded() {
        log.info("Verifying cart page loaded");
        wait.until(d -> isDisplayed(pageTitle));
        return this;
    }

    public int getCartItemCount() {
        int count = cartItems.size();
        log.debug("Cart contains [{}] item(s)", count);
        return count;
    }

    public List<String> getCartItemNames() {
        return cartItems.stream()
                .map(item -> item.findElement(By.cssSelector(".inventory_item_name")).getText())
                .toList();
    }

    public CheckoutPage proceedToCheckout() {
        log.info("Proceeding to checkout");
        click(checkoutButton);
        wait.until(ExpectedConditions.urlContains("checkout-step-one"));
        log.info("Navigated to checkout step 1");
        return new CheckoutPage();
    }

    public InventoryPage continueShopping() {
        log.info("Continuing shopping from cart");
        click(continueShoppingButton);
        return new InventoryPage();
    }
}
