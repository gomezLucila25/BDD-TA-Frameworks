package com.epam.framework.core;

import com.epam.framework.core.browser.ChromeDriverCreator;
import com.epam.framework.core.browser.ChromeHeadlessDriverCreator;
import com.epam.framework.core.browser.EdgeDriverCreator;
import com.epam.framework.core.browser.FirefoxDriverCreator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import java.util.Map;

/**
 * PATTERN: Factory Method — client that selects and delegates to the correct WebDriverCreator.
 *
 * SOLID fix — OCP (Open/Closed Principle):
 * Previously this class used a switch statement; adding a new browser required modifying it.
 * Now each browser is encapsulated in its own WebDriverCreator implementation.
 * To add a new browser: create a new class implementing WebDriverCreator and register
 * it in the CREATORS map — no other code changes needed.
 */
public class DriverFactory {

    private static final Logger log = LogManager.getLogger(DriverFactory.class);

    private static final Map<String, WebDriverCreator> CREATORS = Map.of(
            "chrome",          new ChromeDriverCreator(),
            "chrome-headless", new ChromeHeadlessDriverCreator(),
            "firefox",         new FirefoxDriverCreator(),
            "edge",            new EdgeDriverCreator()
    );

    private DriverFactory() {}

    public static WebDriver createDriver(String browser) {
        log.info("Creating WebDriver for browser: [{}]", browser);
        WebDriverCreator creator = CREATORS.getOrDefault(
                browser.toLowerCase().trim(),
                new ChromeDriverCreator()   // default fallback
        );
        if (!CREATORS.containsKey(browser.toLowerCase().trim())) {
            log.warn("Unknown browser [{}]. Defaulting to Chrome.", browser);
        }
        return creator.createDriver();
    }
}
