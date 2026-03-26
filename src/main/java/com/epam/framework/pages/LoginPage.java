package com.epam.framework.pages;

import com.epam.framework.config.ConfigProvider;
import com.epam.framework.model.User;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    private static final String URL_PATH = "/";

    @FindBy(id = "user-name")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;

    public LoginPage open() {
        String url = ConfigProvider.getInstance().getBaseUrl() + URL_PATH;
        log.info("Opening login page: [{}]", url);
        driver.get(url);
        return this;
    }

    public InventoryPage loginAs(User user) {
        log.info("Logging in as user: [{}]", user);
        type(usernameField, user.getUsername());
        type(passwordField, user.getPassword());
        click(loginButton);
        log.info("Login form submitted for user: [{}]", user.getUsername());
        return new InventoryPage();
    }

    public LoginPage enterCredentials(String username, String password) {
        log.debug("Entering credentials — username: [{}]", username);
        type(usernameField, username);
        type(passwordField, password);
        return this;
    }

    public LoginPage clickLoginButton() {
        log.debug("Clicking login button");
        click(loginButton);
        return this;
    }

    public String getErrorMessage() {
        String msg = getText(errorMessage);
        log.warn("Login error displayed: [{}]", msg);
        return msg;
    }

    public boolean isErrorDisplayed() {
        return isDisplayed(errorMessage);
    }
}
