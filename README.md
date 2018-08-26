# jCucumberNG-Framework

## Overview
Allows automation testers to write feature/gherkin files for Cucumber and implement step definitions in basic Java classes. ngWebDriver (Protractor) offers extended support for Angular/JS web applications.

## The Design: Write tests, not page objects (optional reading)

### An Analogy
Like arithmetic, there are many ways to arrive to the same answer. Some longer, some shorter.

Page Object Model (POM) is like this:
~~~
[(2 + 2) * 3 - 7] / 1 = 5
~~~

Dependency Injection (DI) is like this:
~~~
2 + 3 = 5
~~~

### The Rantionale: A rant with a rationale
An anti-pattern occurs when adhering to the design becomes the larger chore of automation efforts instead of writing sensible tests which is the common pitfall of the popular [Page Object Model](https://github.com/SeleniumHQ/selenium/wiki/PageObjects). The priority becomes sustaining the design pattern, not _testing_. Automation testers find themselves spending more time overengineering page objects than delivering test results that provide value.

Returning page objects within step definitions does not make much sense because Cucumber inherently calls steps to move from one state to the next. PicoContainer eliminates the tight coupling of page objects to step definitions by sharing states in the glue code using [Dependency Injection](http://picocontainer.com/injection.html). It requires [minimal configuration](https://docs.cucumber.io/cucumber/state/#dependency-injection) and injects the needed classes via the constructor. With shared objects, each step definition becomes an autonomous unit as is the nature of a Java method.

In fact, there is no mention of POM in [The Cucumber for Java Book](https://pragprog.com/book/srjcuc/the-cucumber-for-java-book) (authored by the creator and developer of Cucumber and PicoContainer, [Aslak Hellesøy](https://github.com/aslakhellesoy)) but there is _Chapter 11: Simplifying Design with Dependency Injection_. Why then, is POM a pervasive design pattern seen in most test automation suites? Tradition. This comes from the days of "pure" Selenium tests that do not offer behavior-driven (BDD)/step-based capabilities. Add that to the fact that Selenium actively promotes the pattern and comes with `PageFactory` to support it, automation testers simply incorporated it to their BDD test frameworks by default. _POM complements Selenium, not Cucumber._

**jCucumberNG-Framework** deliberately foregoes the added complexity and abstraction of POM to take advantage of Cucumber's intended design - to build a library of loosely coupled steps which can be independently called anywhere while Selenium WebDriver, true to its official definition, automates browser actions. That's it! Writing new feature files becomes a matter of reusing and combining steps in the proper order.

> **_TL;DR:_**
> - Selenium WebDriver + POM = OK
> - Selenium WebDriver + Cucumber + POM = Not OK
> - Selenium WebDriver + Cucumber + DI = ROI (fast and simple)

## How It Works
The code snippet below shows writing test scripts directly into step definitions because why not?

### ui-map.properties:
~~~
net.per.month=binding:roundDown(monthlyNet())
~~~

### Feature File:
~~~
Then I Should See Net Income Per Month: 23769
~~~

### Step Definition:
~~~
private Selenium selenium = null; // Extended Selenium API

// PicoContainer injects ScenarioHook object
public NetIncomeProjectorSteps(ScenarioHook scenarioHook) { // ScenarioHook instantiates Selenium object
    selenium = scenarioHook.getSelenium();
}

@Then("I Should See Net Income Per Month: {word}")
public void I_Should_See_Net_Income_Per_Month(String expected) throws Throwable {
    WebElement netPerMonth = selenium.getVisibleElement("net.per.month"); // Use injected Selenium object
    String actual = netPerMonth.getText();
    Assertions.assertThat(actual).isEqualTo(expected);
    LOGGER.debug("Net Per Month=" + actual);
}
~~~

[User Interface (UI) Mapping](https://www.seleniumhq.org/docs/06_test_design_considerations.jsp#user-interface-mapping) is a familiar approach for storing web elements and works well with DI. The `WebDriver` is not exposed while containing eveything within the method in plain sight.

<!-- No need to plow through # page objects. Based on a true story. -->

## Capabilities & Technology Stack
- [Selenium WebDriver 3](https://www.seleniumhq.org/) for browser automation
- [Cucumber-JVM 3](https://github.com/cucumber/cucumber-jvm) for behavior-driven testing
- [ngWebDriver](https://github.com/paul-hammant/ngWebDriver) (Protractor) for Angular/JS support
- [PicoContainer](http://picocontainer.com/) for DI module
- [AssertJ](http://joel-costigliola.github.io/assertj/) for fluent assertions
- [Maven](https://maven.apache.org/) for dependency management and build execution
- [Log4j2](https://logging.apache.org/log4j/2.x/) / [SLF4J](https://www.slf4j.org/) for logging mechanism
- [Extended Selenium API](https://github.com/kathyrollo/jcucumberng-framework/blob/develop/src/test/java/jcucumberng/framework/api/Selenium.java) for commonly used web testing actions
- [UI Map](https://github.com/kathyrollo/jcucumberng-framework/blob/develop/src/test/resources/jcucumberng/project/ui-map.properties) for central object repository of web elements
- Compatible with IE11, Edge, Chrome, Firefox
- Test result generation in HTML, JSON, XML
- Embedded screenshots on generated HTML reports

## Prerequisites
- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) or higher
- [Eclipse IDE](http://www.eclipse.org/downloads/eclipse-packages/) / [VSCode](https://code.visualstudio.com/download) / [IntelliJ](https://www.jetbrains.com/idea/download/#section=windows)
- [Git](https://git-scm.com/downloads)
- [Maven](https://maven.apache.org/download.cgi)
- [Cmder](http://cmder.net/) (recommended, includes Git for Windows)

Setup and installation are not in the scope of this guide.

## Getting Started
Visit the application under test (AUT) here: http://simplydo.com/projector/

No further configurations needed at this point. The tests will run against the AUT in [headless browser](https://en.wikipedia.org/wiki/Headless_browser) mode using ChromeDriver as defined in `framework.properties`.

### Test Execution

Run the following commands in the cmdline:
~~~
$ cd /path/to/workspace/
$ git clone <repo-url>
$ cd jcucumberng-framework/
$ mvn verify
~~~

Maven performs a one-time download of all dependencies for the first run. Execute `mvn verify` again after the downloads complete to begin test execution.

![mvn_verify](https://user-images.githubusercontent.com/28589393/43071460-79da3de6-8ea5-11e8-9935-a6afc02d62d8.gif)

Test artefacts are created in the `/target/` directory after the build is successful.

### Reporting
HTML reports are generated with dynamic visuals and statistics.

#### [Maven Cucumber Reporting](https://github.com/damianszczepanik/maven-cucumber-reporting)
Directory: `/target/cucumber-html-reports/`
![dynamic_report](https://user-images.githubusercontent.com/28589393/43090686-acbd9c00-8eda-11e8-9c08-d74c1a86e03b.gif)

#### [Cucumber Extent Reporter](https://github.com/email2vimalraj/CucumberExtentReporter)
TODO

#### [Allure Test Report](https://github.com/allure-framework)
TODO

### Logging
Logs are written to a daily rolling file. Executions from the previous day are saved with a datestamp in a separate file.
~~~
target/
|__ cucumber-logs/
    |__ cucumber_2018-07-19.log
    |__ cucumber_2018-07-20.log
    |__ cucumber_2018-07-21.log
    |__ cucumber.log
~~~

#### Sample Logs
Directory: `/target/cucumber-logs/`
~~~
[INFO ] 2018-07-21 22:02:40,107 ScenarioHook.beforeScenario() - BEGIN TEST -> Verify Page Title
[INFO ] 2018-07-21 22:02:44,191 ScenarioHook.beforeScenario() - Browser=CHROME32_NOHEAD
[INFO ] 2018-07-21 22:02:45,387 ScenarioHook.beforeScenario() - Screen Resolution (WxH)=1366x768
[DEBUG] 2018-07-21 22:02:49,642 HomePageNavigationSteps.I_Am_At_The_Home_Page() - Base URL=http://simplydo.com/projector/
[DEBUG] 2018-07-21 22:02:50,095 HomePageNavigationSteps.I_Should_See_Page_Title() - Window Title=Simply Do - Balance Projector
[INFO ] 2018-07-21 22:02:50,413 ScenarioHook.afterScenario() - END TEST -> Verify Page Title - PASSED
~~~