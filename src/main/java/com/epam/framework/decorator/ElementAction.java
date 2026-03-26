package com.epam.framework.decorator;

import org.openqa.selenium.WebElement;

/**
 * PATTERN: Decorator — component interface.
 *
 * Defines the contract for any action that can be performed on a WebElement.
 * Concrete actions (ClickAction, TypeAction) implement the real behaviour.
 * Decorator classes wrap a component to add cross-cutting concerns such as
 * logging and visual highlighting without modifying the core action logic.
 *
 * SOLID fix — SRP:
 * Previously, BasePage.click() mixed waiting, highlighting, logging, and clicking
 * into a single method. Each concern is now its own class:
 *   - Waiting    → WaitStrategy (Strategy pattern, see strategy package)
 *   - Highlighting → HighlightDecorator
 *   - Logging      → LoggingDecorator
 *   - Clicking     → ClickAction
 */
public interface ElementAction {
    void perform(WebElement element);
}
