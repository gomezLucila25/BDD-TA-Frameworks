package com.epam.framework.core.browser;

import com.epam.framework.core.WebDriverCreator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

/**
 * PATTERN: Factory Method — concrete creator for Microsoft Edge.
 */
public class EdgeDriverCreator implements WebDriverCreator {

    private static final Logger log = LogManager.getLogger(EdgeDriverCreator.class);

    @Override
    public WebDriver createDriver() {
        log.debug("Creating EdgeDriver via Selenium Manager");
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--start-maximized");
        log.info("EdgeDriver created successfully");
        return new EdgeDriver(options);
    }
}
