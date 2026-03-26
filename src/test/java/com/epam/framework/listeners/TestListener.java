package com.epam.framework.listeners;

import com.epam.framework.utils.ScreenshotUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    private static final Logger log = LogManager.getLogger(TestListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        log.info("========== TEST STARTED: [{}] ==========", result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        log.info("========== TEST PASSED:  [{}] ==========", result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        log.error("========== TEST FAILED:  [{}] ==========", result.getName());
        log.error("Failure reason: {}", result.getThrowable().getMessage());

        String screenshotPath = ScreenshotUtils.takeScreenshot(result.getName());
        if (screenshotPath != null) {
            log.info("Failure screenshot available at: [{}]", screenshotPath);
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        log.warn("========== TEST SKIPPED: [{}] ==========", result.getName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        log.warn("Test [{}] failed but within success percentage", result.getName());
    }
}
