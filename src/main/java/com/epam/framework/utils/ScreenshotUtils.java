package com.epam.framework.utils;

import com.epam.framework.core.DriverManager;
import io.qameta.allure.Attachment;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotUtils {

    private static final Logger log = LogManager.getLogger(ScreenshotUtils.class);
    private static final String SCREENSHOT_DIR = "target/screenshots/";
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

    private ScreenshotUtils() {}

    /**
     * Saves screenshot to disk AND attaches it to the Allure report.
     * Called from TestListener on test failure.
     */
    public static String takeScreenshot(String testName) {
        log.debug("Capturing screenshot for test: [{}]", testName);
        try {
            TakesScreenshot ts = (TakesScreenshot) DriverManager.getDriver();
            byte[] bytes = ts.getScreenshotAs(OutputType.BYTES);

            // Attach to Allure report
            attachScreenshotToAllure(bytes);

            // Also save to disk for CI artifact archiving
            String timestamp = LocalDateTime.now().format(FORMATTER);
            String filename = SCREENSHOT_DIR + testName + "_" + timestamp + ".png";
            File destination = new File(filename);
            FileUtils.writeByteArrayToFile(destination, bytes);

            log.info("SCREENSHOT SAVED — test: [{}], path: [{}]", testName,
                    destination.getAbsolutePath());
            return destination.getAbsolutePath();

        } catch (IOException e) {
            log.error("Failed to save screenshot for test: [{}]", testName, e);
            return null;
        } catch (Exception e) {
            log.error("Unexpected error during screenshot capture for test: [{}]", testName, e);
            return null;
        }
    }

    /**
     * Allure attachment — the byte[] returned by this method is embedded
     * directly in the Allure report as a PNG image.
     */
    @Attachment(value = "Failure Screenshot", type = "image/png")
    public static byte[] attachScreenshotToAllure(byte[] screenshot) {
        return screenshot;
    }
}
