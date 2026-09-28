# BDD Test Automation — Cucumber-JVM on top of Selenium Page Objects

> **Module 5 of my EPAM Test Automation track.** Each module added one layer to the same Selenium framework.
> The complete, final version lives in **[selenium-framework-patterns](https://github.com/gomezLucila25/selenium-framework-patterns)**.

## What this module added

- **Cucumber-JVM** integrated on top of the existing Page Objects, reusing them rather than duplicating them.
- Gherkin features for **login** and **cart**, with `Background` and a data-driven `Scenario Outline` using `Examples` tables.
- Step definitions, hooks for driver setup and teardown, and a TestNG runner, so BDD and classic TestNG suites live side by side.

## Stack

Java 17 · Selenium 4 · Cucumber-JVM · TestNG · Allure · Maven

## Run

```bash
mvn clean test
```
