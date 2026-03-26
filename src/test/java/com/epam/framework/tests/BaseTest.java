package com.epam.framework.tests;

import com.epam.framework.config.ConfigProvider;
import com.epam.framework.core.DriverManager;
import com.epam.framework.model.User;
import com.epam.framework.pages.LoginPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class BaseTest {

    protected final Logger log = LogManager.getLogger(getClass());
    protected ConfigProvider config;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        log.info("------ Setting up test environment ------");
        config = ConfigProvider.getInstance();
        DriverManager.initDriver();
        log.info("Test setup complete — base URL: [{}]", config.getBaseUrl());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        log.info("------ Tearing down test environment ------");
        DriverManager.quitDriver();
    }

    protected LoginPage openLoginPage() {
        log.debug("Opening login page");
        return new LoginPage().open();
    }

    protected User getStandardUser() {
        return new User(config.getStandardUser(), config.getPassword());
    }

    protected User getLockedUser() {
        return new User(config.get("locked.user"), config.getPassword());
    }
}
