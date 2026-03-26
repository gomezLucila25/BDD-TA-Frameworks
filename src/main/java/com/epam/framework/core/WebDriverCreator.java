package com.epam.framework.core;

import org.openqa.selenium.WebDriver;

/**
 * PATTERN: Factory Method — abstract product creator.
 *
 * Each concrete subclass encapsulates the creation logic for one browser type.
 * Adding a new browser means creating a new class that implements this interface,
 * with zero changes to existing code (Open/Closed Principle).
 *
 * SOLID fix — OCP:
 * Previously, DriverFactory used a switch statement that required modification
 * every time a new browser was added. Now each browser is an isolated class.
 */
public interface WebDriverCreator {

    /**
     * Factory method: creates and returns a configured WebDriver instance.
     */
    WebDriver createDriver();
}
