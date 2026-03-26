package com.epam.framework.pages;

import com.epam.framework.model.Order;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CheckoutPage extends BasePage {

    // --- Step 1: Information ---
    @FindBy(id = "first-name")
    private WebElement firstNameField;

    @FindBy(id = "last-name")
    private WebElement lastNameField;

    @FindBy(id = "postal-code")
    private WebElement postalCodeField;

    @FindBy(id = "continue")
    private WebElement continueButton;

    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;

    // --- Step 2: Overview ---
    @FindBy(css = ".summary_total_label")
    private WebElement totalLabel;

    @FindBy(id = "finish")
    private WebElement finishButton;

    // --- Step 3: Complete ---
    @FindBy(css = ".complete-header")
    private WebElement completeHeader;

    public CheckoutPage fillShippingInfo(Order order) {
        log.info("Filling shipping info for order: [{}]", order);
        type(firstNameField, order.getFirstName());
        type(lastNameField, order.getLastName());
        type(postalCodeField, order.getPostalCode());
        return this;
    }

    public CheckoutPage clickContinue() {
        log.info("Clicking continue in checkout step 1");
        click(continueButton);
        return this;
    }

    public String getOrderTotal() {
        String total = getText(totalLabel);
        log.info("Order total: [{}]", total);
        return total;
    }

    public CheckoutPage clickFinish() {
        log.info("Clicking finish — placing order");
        click(finishButton);
        return this;
    }

    public String getConfirmationMessage() {
        String msg = getText(completeHeader);
        log.info("Order confirmation: [{}]", msg);
        return msg;
    }

    public boolean isOrderConfirmed() {
        return isDisplayed(completeHeader);
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }
}
