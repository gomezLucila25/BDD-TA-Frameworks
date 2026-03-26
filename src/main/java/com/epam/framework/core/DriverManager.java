package com.epam.framework.core;

import com.epam.framework.config.ConfigProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

/**
 * PATTERN: Singleton with ThreadLocal — one driver per thread, shared safely across parallel tests.
 *
 * SOLID fix — SRP:
 * DriverManager is now only responsible for driver lifecycle (store, retrieve, quit).
 * Browser-specific driver creation is fully delegated to DriverFactory + WebDriverCreator implementations.
 * Initial timeout/window configuration remains here as it belongs to driver "setup" (not "creation").
 */
public class DriverManager {

    private static final Logger log = LogManager.getLogger(DriverManager.class);
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    private DriverManager() {}

    public static void initDriver() {
        String browser = System.getProperty("browser", "chrome");
        log.info("Initialising WebDriver — browser: [{}], thread: [{}]",
                browser, Thread.currentThread().getName());

        WebDriver driver = DriverFactory.createDriver(browser);
        applyTimeouts(driver);

        driverThreadLocal.set(driver);
        log.info("WebDriver initialised and stored in ThreadLocal");
    }

    public static WebDriver getDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver == null) {
            log.error("WebDriver is null — initDriver() was never called for this thread");
            throw new IllegalStateException("WebDriver not initialised. Call DriverManager.initDriver() first.");
        }
        return driver;
    }

    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            log.info("Quitting WebDriver for thread: [{}]", Thread.currentThread().getName());
            driver.quit();
            driverThreadLocal.remove();
            log.debug("WebDriver removed from ThreadLocal");
        } else {
            log.warn("quitDriver() called but no driver found in ThreadLocal");
        }
    }

    private static void applyTimeouts(WebDriver driver) {
        ConfigProvider config = ConfigProvider.getInstance();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(config.getImplicitWait()));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(config.getPageLoadTimeout()));
        driver.manage().window().maximize();
    }
}
