package com.epam.framework.strategy;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * PATTERN: Strategy (BONUS) — concrete strategy using WebDriverWait.
 *
 * The default strategy used by BasePage. Waits up to {@code timeoutSeconds}
 * for the expected condition to be met; throws TimeoutException if not met.
 */
public class ExplicitWaitStrategy implements WaitStrategy {

    private final long timeoutSeconds;

    public ExplicitWaitStrategy(long timeoutSeconds) {
        this.timeoutSeconds = timeoutSeconds;
    }

    @Override
    public void waitForClickable(WebDriver driver, WebElement element) {
        new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(ExpectedConditions.elementToBeClickable(element));
    }

    @Override
    public void waitForVisible(WebDriver driver, WebElement element) {
        new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(ExpectedConditions.visibilityOf(element));
    }
}
