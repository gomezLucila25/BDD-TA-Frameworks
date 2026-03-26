package com.epam.framework.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.epam.framework.config.ConfigProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.codeborne.selenide.ClickOptions.usingJavaScript;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.webdriver;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * BONUS TASK: Same test scenarios implemented with Selenide.
 *
 * Selenide advantages vs raw Selenium:
 * - Auto-waits on every $ call — no explicit WebDriverWait needed
 * - Built-in screenshot on failure
 * - Fluent, readable API
 * - Auto-closes browser after test
 */
public class SelenideLoginTest {

    private static final Logger log = LogManager.getLogger(SelenideLoginTest.class);
    private final ConfigProvider config = ConfigProvider.getInstance();

    @BeforeMethod
    public void setUp() {
        Configuration.browser = System.getProperty("browser", "chrome");
        Configuration.baseUrl = config.getBaseUrl();
        Configuration.timeout = config.getExplicitWait() * 1000L;
        Configuration.pageLoadTimeout = config.getPageLoadTimeout() * 1000L;
        log.info("[Selenide] Browser: [{}], BaseURL: [{}]",
                Configuration.browser, Configuration.baseUrl);
    }

    @AfterMethod
    public void tearDown() {
        log.info("[Selenide] Closing browser");
        Selenide.closeWebDriver();
    }

    @Test(groups = {"smoke", "regression"},
          description = "[Selenide] Valid login navigates to inventory page")
    public void testValidLoginWithSelenide() {
        log.info("[Selenide] TEST: valid login");
        open("/");

        $("#user-name").setValue(config.getStandardUser());
        $("#password").setValue(config.getPassword());
        $("#login-button").click();

        $(".title").shouldBe(visible).shouldHave(text("Products"));
        log.info("[Selenide] TEST PASSED: inventory page visible after login");
    }

    @Test(groups = {"regression"},
          description = "[Selenide] Locked user sees error message")
    public void testLockedUserWithSelenide() {
        log.info("[Selenide] TEST: locked user error");
        open("/");

        $("#user-name").setValue(config.get("locked.user"));
        $("#password").setValue(config.getPassword());
        $("#login-button").click();

        $("[data-test='error']")
                .shouldBe(visible)
                .shouldHave(text("locked out"));

        log.info("[Selenide] TEST PASSED: locked user error message displayed");
    }

    @Test(groups = {"smoke", "regression"},
          description = "[Selenide] Full checkout flow")
    public void testCheckoutFlowWithSelenide() {
        log.info("[Selenide] TEST: e2e checkout");
        open("/");

        $("#user-name").setValue(config.getStandardUser());
        $("#password").setValue(config.getPassword());
        $("#login-button").click();

        $(".title").shouldBe(visible);

        $("[data-test='add-to-cart-sauce-labs-backpack']").click();
        $(".shopping_cart_badge").shouldHave(text("1"));

        $(".shopping_cart_link").click();
        $(".title").shouldHave(text("Your Cart"));

        $("#checkout").click(usingJavaScript());
        setReactValue($("#first-name").toWebElement(), "John");
        setReactValue($("#last-name").toWebElement(), "Doe");
        setReactValue($("#postal-code").toWebElement(), "12345");
        $("#continue").click(usingJavaScript());

        $(".summary_total_label").shouldBe(visible);
        $("#finish").click(usingJavaScript());

        assertThat($(".complete-header").getText()).containsIgnoringCase("Thank you");
        log.info("[Selenide] TEST PASSED: order confirmed");
    }

    /**
     * Sets a value on a React controlled input via native setter + input event,
     * the same technique used by BasePage.type() in the main framework.
     */
    private void setReactValue(WebElement element, String value) {
        JavascriptExecutor js = (JavascriptExecutor) webdriver().object();
        js.executeScript(
            "var setter = Object.getOwnPropertyDescriptor(" +
                "window.HTMLInputElement.prototype, 'value').set;" +
            "setter.call(arguments[0], arguments[1]);" +
            "arguments[0].dispatchEvent(new Event('input', {bubbles:true}));",
            element, value);
    }
}
