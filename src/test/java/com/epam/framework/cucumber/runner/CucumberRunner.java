package com.epam.framework.cucumber.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

/**
 * Cucumber TestNG runner.
 * Run with: mvn test -Dsuite=cucumber
 */
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {
                "com.epam.framework.cucumber.steps",
                "com.epam.framework.cucumber.hooks"
        },
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber.html",
                "json:target/cucumber-reports/cucumber.json"
        },
        monochrome = true,
        tags = "not @ignore"
)
public class CucumberRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
