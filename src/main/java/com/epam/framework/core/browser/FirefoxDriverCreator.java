package com.epam.framework.core.browser;

import com.epam.framework.core.WebDriverCreator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

/**
 * PATTERN: Factory Method — concrete creator for Firefox.
 */
public class FirefoxDriverCreator implements WebDriverCreator {

    private static final Logger log = LogManager.getLogger(FirefoxDriverCreator.class);

    @Override
    public WebDriver createDriver() {
        log.debug("Creating FirefoxDriver via Selenium Manager");
        FirefoxOptions options = new FirefoxOptions();
        log.info("FirefoxDriver created successfully");
        return new FirefoxDriver(options);
    }
}
