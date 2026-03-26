package com.epam.framework.core.browser;

import com.epam.framework.core.WebDriverCreator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * PATTERN: Factory Method — concrete creator for Chrome headless (CI mode).
 */
public class ChromeHeadlessDriverCreator implements WebDriverCreator {

    private static final Logger log = LogManager.getLogger(ChromeHeadlessDriverCreator.class);

    @Override
    public WebDriver createDriver() {
        log.debug("Creating Chrome headless driver via Selenium Manager");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");
        log.info("Chrome headless driver created successfully");
        return new ChromeDriver(options);
    }
}
