# jCucumberNG-Framework

## Overview
Allows automation testers to write Feature/Gherkin files for Cucumber and implement step definitions in plain Java classes. ngWebDriver (Protractor) offers extended support for Angular/JS web applications.

## Write Tests, Not Page Objects
This framework deliberately removes the Page Object Model (POM). _Test automation should be focused on testing_ instead of continually over-engineering a design pattern -- or [anti-pattern](https://blog.getgauge.io/are-page-objects-anti-pattern-21b6e337880f).

Cucumber PicoContainer ([officially recommended](https://docs.cucumber.io/cucumber/state/#dependency-injection)) removes the tight coupling of page objects to step definitions by sharing states in the step classes using [dependency injection](http://picocontainer.com/injection.html). Each step is an independent unit that can be reused anywhere.

Here is how easy it is to immediately begin writing test scripts without the additional overhead of setting up page objects:

### ui-map.properties:
~~~
net.per.month=binding:roundDown(monthlyNet())
~~~

### Feature/Gherkin:
~~~
Then I Should See Net Income Per Month: 23769
~~~

### Step Definition:
~~~
private WebDriver driver = null;

// PicoContainer injects ScenarioHook class
public NetIncomeProjectorSteps(ScenarioHook scenarioHook) {
    driver = scenarioHook.getDriver();
}

@Then("I Should See Net Income Per Month: {word}")
public void I_Should_See_Net_Income_Per_Month(String expected) throws Throwable {
    WebElement netPerMonth = driver.findElement(Selenium.by("net.per.month"));
    String actual = netPerMonth.getText();
    Assertions.assertThat(actual).isEqualTo(expected);
    LOGGER.debug("Net Per Month=" + actual);
}
~~~

## Capabilities
Supports the following features:
- [ngWebDriver](https://github.com/paul-hammant/ngWebDriver) (Protractor) for Angular/JS locators
- [Cucumber PicoContainer](https://github.com/cucumber/cucumber-jvm/tree/master/picocontainer) for dependency injection
- [AssertJ](http://joel-costigliola.github.io/assertj/) for fluent assertions
- [Maven](https://maven.apache.org/) for build and test execution via cmdline
- [Log4j2](https://logging.apache.org/log4j/2.x/) with [SLF4J](https://www.slf4j.org/) wrapper for logging mechanism
- Automated test result generation in [HTML](https://github.com/damianszczepanik/maven-cucumber-reporting), JSON, XML
- Embedded screenshots on generated HTML reports
- API for commonly used web testing actions
- Central object repository for UI elements
- Compatible with IE11, Edge, Chrome, Firefox (extendable)

## Prerequisites
The following are required:
- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) or higher
- [Eclipse IDE](http://www.eclipse.org/downloads/eclipse-packages/) / [VSCode](https://code.visualstudio.com/download) / [IntelliJ](https://www.jetbrains.com/idea/download/#section=windows)
- [Git](https://git-scm.com/downloads)
- [Maven](https://maven.apache.org/download.cgi)
- [Cmder](http://cmder.net/) (recommended, includes Git for Windows)

Setup and installation are not in the scope of this guide. Check the corresponding documentation or tutorials accordingly.

## Getting Started
Visit the application under test (AUT) here: http://simplydo.com/projector/

No further configurations needed at this point. The tests will run against the AUT in [headless browser](https://en.wikipedia.org/wiki/Headless_browser) mode using ChromeDriver as defined in the default framework settings.

Run the following commands in the cmdline:
~~~
$ cd /path/to/workspace/
$ git clone <https or ssh>
$ cd jcucumberng-framework/
$ mvn verify
~~~

Maven performs a one-time download of all dependencies for the first run. Execute `mvn verify` again after the downloads complete to begin test execution.

![mvn_verify](https://user-images.githubusercontent.com/28589393/43071460-79da3de6-8ea5-11e8-9935-a6afc02d62d8.gif)

Test artefacts are created in the `/target/` directory after the build is successful.

### Reporting
Generates rich HTML reports with dynamic visuals and statistics.

#### Maven Cucumber Reporting
Report found in `/target/cucumber-html-reports/`:
![dynamic_report](https://user-images.githubusercontent.com/28589393/43090686-acbd9c00-8eda-11e8-9c08-d74c1a86e03b.gif)

#### Cucumber Extent Reporter
Plugin does not support Cucumber-JVM 3.x.x.

#### Allure Test Report
Plugin does not support Cucumber-JVM 3.x.x.

### Logging
Writes logs to a daily rolling file. Logs found in `/target/cucumber-logs/`:
~~~
[INFO ] 2018-07-21 22:02:40,107 ScenarioHook.beforeScenario() - BEGIN TEST -> Verify Page Title
[INFO ] 2018-07-21 22:02:44,191 ScenarioHook.beforeScenario() - Browser=CHROME32_NOHEAD
[INFO ] 2018-07-21 22:02:45,387 ScenarioHook.beforeScenario() - Screen Resolution (WxH)=1366x768
[DEBUG] 2018-07-21 22:02:49,642 HomePageNavigationSteps.I_Am_At_The_Home_Page() - Base URL=http://simplydo.com/projector/
[DEBUG] 2018-07-21 22:02:50,095 HomePageNavigationSteps.I_Should_See_Page_Title() - Window Title=Simply Do - Balance Projector
[INFO ] 2018-07-21 22:02:50,413 ScenarioHook.afterScenario() - END TEST -> Verify Page Title - PASSED
~~~