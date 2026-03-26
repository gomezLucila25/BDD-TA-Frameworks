package com.epam.framework.strategy;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * PATTERN: Strategy (BONUS) — defines the algorithm interface for waiting.
 *
 * Allows BasePage to switch waiting behaviour at runtime without changing its code.
 * Concrete strategies:
 *   - ExplicitWaitStrategy  — standard WebDriverWait (default)
 *   - FluentWaitStrategy    — polls at a custom interval, ignores transient exceptions
 *
 * SOLID — OCP: new wait strategies (e.g., custom polling) can be added by creating
 * a new class without touching BasePage or existing strategies.
 */
public interface WaitStrategy {

    /** Wait until the element is clickable. */
    void waitForClickable(WebDriver driver, WebElement element);

    /** Wait until the element is visible. */
    void waitForVisible(WebDriver driver, WebElement element);
}
