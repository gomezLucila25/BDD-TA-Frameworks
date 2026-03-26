package com.epam.framework.pages;

import com.epam.framework.config.ConfigProvider;
import com.epam.framework.core.DriverManager;
import com.epam.framework.decorator.ClickAction;
import com.epam.framework.decorator.ElementAction;
import com.epam.framework.decorator.HighlightDecorator;
import com.epam.framework.decorator.LoggingDecorator;
import com.epam.framework.decorator.TypeAction;
import com.epam.framework.strategy.ExplicitWaitStrategy;
import com.epam.framework.strategy.WaitStrategy;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {

    protected final WebDriver driver;
    protected final WaitStrategy waitStrategy;
    protected final Logger log = LogManager.getLogger(getClass());
    protected final WebDriverWait wait;

    protected BasePage() {
        this.driver = DriverManager.getDriver();
        int explicitWait = ConfigProvider.getInstance().getExplicitWait();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWait));
        this.waitStrategy = new ExplicitWaitStrategy(explicitWait);
        PageFactory.initElements(driver, this);
        log.debug("Page initialised: [{}]", getClass().getSimpleName());
    }

    @Step("Click element")
    protected void click(WebElement element) {
        waitStrategy.waitForClickable(driver, element);
        ElementAction action = new ClickAction(driver);
        action = new HighlightDecorator(action, driver);
        action = new LoggingDecorator(action, "CLICK");
        action.perform(element);
    }

    @Step("Type '{text}' into element")
    protected void type(WebElement element, String text) {
        waitStrategy.waitForVisible(driver, element);
        ElementAction action = new TypeAction(driver, text);
        action = new HighlightDecorator(action, driver);
        action = new LoggingDecorator(action, "TYPE[" + text + "]");
        action.perform(element);
    }

    protected String getText(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        String text = element.getText();
        log.debug("getText — value: [{}]", text);
        return text;
    }

    protected boolean isDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
