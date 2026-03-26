package com.epam.framework.decorator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * PATTERN: Decorator — adds visual highlighting around any ElementAction.
 *
 * SOLID fix — DIP (Dependency Inversion Principle):
 * Previously, HighlightUtils called DriverManager.getDriver() internally,
 * meaning a utility class depended on a high-level infrastructure class.
 * Now the WebDriver is injected via constructor, inverting the dependency:
 * the decorator depends on the abstraction (WebDriver interface), not on
 * the concrete DriverManager.
 */
public class HighlightDecorator extends AbstractElementActionDecorator {

    private static final Logger log = LogManager.getLogger(HighlightDecorator.class);
    private static final String HIGHLIGHT_STYLE = "background: yellow; border: 2px solid red;";
    private static final int HIGHLIGHT_DURATION_MS = 300;

    private final JavascriptExecutor js;

    public HighlightDecorator(ElementAction wrapped, WebDriver driver) {
        super(wrapped);
        this.js = (JavascriptExecutor) driver;
    }

    @Override
    public void perform(WebElement element) {
        String originalStyle = element.getAttribute("style");
        log.debug("Highlighting element: [{}]", element.getTagName());
        try {
            js.executeScript("arguments[0].setAttribute('style', arguments[1]);",
                    element, HIGHLIGHT_STYLE);
            Thread.sleep(HIGHLIGHT_DURATION_MS);
            js.executeScript("arguments[0].setAttribute('style', arguments[1]);",
                    element, originalStyle == null ? "" : originalStyle);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.warn("Highlight interrupted for element: [{}]", element.getTagName());
        }
        wrapped.perform(element);
    }
}
