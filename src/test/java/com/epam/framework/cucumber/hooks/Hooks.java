package com.epam.framework.cucumber.hooks;

import com.epam.framework.core.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Cucumber lifecycle hooks — replaces TestNG @BeforeMethod/@AfterMethod.
 * Runs before and after each scenario (including each Scenario Outline row).
 */
public class Hooks {

    private static final Logger log = LogManager.getLogger(Hooks.class);

    @Before
    public void setUp() {
        log.info("------ [CUCUMBER] Setting up WebDriver for scenario ------");
        DriverManager.initDriver();
    }

    @After
    public void tearDown() {
        log.info("------ [CUCUMBER] Quitting WebDriver after scenario ------");
        DriverManager.quitDriver();
    }
}
