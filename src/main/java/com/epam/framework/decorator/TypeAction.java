package com.epam.framework.decorator;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * PATTERN: Decorator — concrete component (leaf) for type operations.
 *
 * Uses the native HTMLInputElement value setter + dispatches an 'input' event
 * so that React controlled inputs pick up the new value correctly.
 */
public class TypeAction implements ElementAction {

    private final JavascriptExecutor js;
    private final String text;

    public TypeAction(WebDriver driver, String text) {
        this.js = (JavascriptExecutor) driver;
        this.text = text;
    }

    @Override
    public void perform(WebElement element) {
        js.executeScript(
            "var nativeInputValueSetter = Object.getOwnPropertyDescriptor(" +
                "window.HTMLInputElement.prototype, 'value').set;" +
            "nativeInputValueSetter.call(arguments[0], arguments[1]);" +
            "arguments[0].dispatchEvent(new Event('input', {bubbles:true}));",
            element, text);
    }
}
