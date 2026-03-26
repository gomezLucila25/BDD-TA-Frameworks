package com.epam.framework.core.browser;

import com.epam.framework.core.WebDriverCreator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * PATTERN: Factory Method — concrete creator for Chrome (headed).
 */
public class ChromeDriverCreator implements WebDriverCreator {

    private static final Logger log = LogManager.getLogger(ChromeDriverCreator.class);

    @Override
    public WebDriver createDriver() {
        log.debug("Creating ChromeDriver via Selenium Manager");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        log.info("ChromeDriver created successfully");
        return new ChromeDriver(options);
    }
}
