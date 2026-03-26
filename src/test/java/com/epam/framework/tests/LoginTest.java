package com.epam.framework.tests;

import com.epam.framework.model.User;
import com.epam.framework.pages.InventoryPage;
import com.epam.framework.pages.LoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Feature("Authentication")
public class LoginTest extends BaseTest {

    @Story("Valid login")
    @Description("Standard user logs in and lands on the inventory page")
    @Test(groups = {"smoke", "regression"})
    public void testValidLogin() {
        log.info("TEST: valid login with standard user");

        InventoryPage inventoryPage = openLoginPage().loginAs(getStandardUser());

        inventoryPage.verifyPageLoaded();
        assertThat(inventoryPage.getPageTitle()).isEqualTo("Products");
        log.info("TEST ASSERTION PASSED: inventory page title is 'Products'");
    }

    @Story("Locked user")
    @Description("Locked user sees an error message and cannot proceed")
    @Test(groups = {"regression"})
    public void testLockedUserLogin() {
        log.info("TEST: locked user receives error message on login");

        LoginPage loginPage = openLoginPage();
        loginPage.enterCredentials(
                config.get("locked.user"),
                config.getPassword()
        ).clickLoginButton();

        assertThat(loginPage.isErrorDisplayed()).isTrue();
        assertThat(loginPage.getErrorMessage()).contains("Sorry, this user has been locked out");
        log.info("TEST ASSERTION PASSED: locked user error message displayed");
    }

    @Story("Invalid credentials")
    @Description("Empty credentials trigger a validation error")
    @Test(groups = {"regression"})
    public void testEmptyCredentials() {
        log.info("TEST: empty credentials trigger validation error");

        LoginPage loginPage = openLoginPage()
                .enterCredentials("", "")
                .clickLoginButton();

        assertThat(loginPage.isErrorDisplayed()).isTrue();
        log.info("TEST ASSERTION PASSED: error displayed for empty credentials");
    }

    @Story("Invalid credentials")
    @Description("Wrong password triggers a credentials mismatch error")
    @Test(groups = {"regression"})
    public void testWrongPassword() {
        log.info("TEST: wrong password triggers error");
        User badUser = new User(config.getStandardUser(), "wrong_password");

        LoginPage loginPage = openLoginPage()
                .enterCredentials(badUser.getUsername(), badUser.getPassword())
                .clickLoginButton();

        assertThat(loginPage.isErrorDisplayed()).isTrue();
        assertThat(loginPage.getErrorMessage()).contains("Username and password do not match");
        log.info("TEST ASSERTION PASSED: wrong password error message displayed");
    }

    @Story("Logout")
    @Description("Authenticated user can logout and return to the login page")
    @Test(groups = {"smoke", "regression"})
    public void testLogout() {
        log.info("TEST: logout returns user to login page");

        LoginPage loginPage = openLoginPage()
                .loginAs(getStandardUser())
                .logout();

        assertThat(loginPage.isErrorDisplayed()).isFalse();
        log.info("TEST ASSERTION PASSED: user is back on login page after logout");
    }
}
