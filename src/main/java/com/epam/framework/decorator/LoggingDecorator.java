package com.epam.framework.decorator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;

/**
 * PATTERN: Decorator — adds structured logging around any ElementAction.
 *
 * Wraps another ElementAction and logs before/after execution.
 * Can be stacked with other decorators (e.g., HighlightDecorator) in any order.
 *
 * Usage example (from BasePage):
 *   ElementAction action = new ClickAction(driver);
 *   action = new HighlightDecorator(action, driver);   // highlight first
 *   action = new LoggingDecorator(action, "CLICK");    // log outer shell
 *   action.perform(element);
 */
public class LoggingDecorator extends AbstractElementActionDecorator {

    private static final Logger log = LogManager.getLogger(LoggingDecorator.class);
    private final String actionName;

    public LoggingDecorator(ElementAction wrapped, String actionName) {
        super(wrapped);
        this.actionName = actionName;
    }

    @Override
    public void perform(WebElement element) {
        log.debug("ACTION [{}] starting on element: [{}]", actionName, element.getTagName());
        wrapped.perform(element);
        log.debug("ACTION [{}] completed", actionName);
    }
}
