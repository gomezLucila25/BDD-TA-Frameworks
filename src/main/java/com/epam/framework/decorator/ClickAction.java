package com.epam.framework.decorator;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * PATTERN: Decorator — concrete component (leaf) for click operations.
 *
 * Uses JavaScript click to reliably trigger React synthetic events on SauceDemo SPA.
 */
public class ClickAction implements ElementAction {

    private final JavascriptExecutor js;

    public ClickAction(WebDriver driver) {
        this.js = (JavascriptExecutor) driver;
    }

    @Override
    public void perform(WebElement element) {
        js.executeScript("arguments[0].click();", element);
    }
}
