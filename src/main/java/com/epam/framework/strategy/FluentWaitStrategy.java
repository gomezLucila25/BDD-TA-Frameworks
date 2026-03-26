package com.epam.framework.strategy;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;

/**
 * PATTERN: Strategy (BONUS) — concrete strategy using FluentWait.
 *
 * Polls every {@code pollingMillis} ms and ignores transient exceptions.
 * Useful for flaky elements that briefly disappear from the DOM.
 * Can be injected into any BasePage subclass instead of ExplicitWaitStrategy.
 */
public class FluentWaitStrategy implements WaitStrategy {

    private final long timeoutSeconds;
    private final long pollingMillis;

    public FluentWaitStrategy(long timeoutSeconds, long pollingMillis) {
        this.timeoutSeconds = timeoutSeconds;
        this.pollingMillis  = pollingMillis;
    }

    @Override
    public void waitForClickable(WebDriver driver, WebElement element) {
        buildWait(driver).until(ExpectedConditions.elementToBeClickable(element));
    }

    @Override
    public void waitForVisible(WebDriver driver, WebElement element) {
        buildWait(driver).until(ExpectedConditions.visibilityOf(element));
    }

    private FluentWait<WebDriver> buildWait(WebDriver driver) {
        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(timeoutSeconds))
                .pollingEvery(Duration.ofMillis(pollingMillis))
                .ignoring(Exception.class);
    }
}
