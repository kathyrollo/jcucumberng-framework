# jCucumberNG-Framework
Allows automation testers to write feature/gherkin files for Cucumber and implement step definitions in basic Java classes. ngWebDriver (Protractor) offers extended support for Angular/JS web applications.

### Table of Contents
1. [Write Tests, Not Page Objects](#write-tests-not-page-objects)
2. [How It Works](#how-it-works)
3. [Features](#features)
4. [Prerequisites](#prerequisites)
5. [Running Tests](#running-tests)
6. [Checking Results](#checking-results)

## Write Tests, Not Page Objects
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

### POM and BDD
An anti-pattern occurs when adhering to the design becomes the larger chore of automation efforts instead of writing sensible tests which is the common pitfall of the popular [Page Object Model](https://www.seleniumhq.org/docs/06_test_design_considerations.jsp#page-object-design-pattern). The priority becomes sustaining the design pattern, not _testing_. Automation testers find themselves spending more time overengineering page objects than writing high quality tests that provide actual value.

Why then, is POM a pervasive design pattern seen in many test automation suites? Tradition. This comes from the days of "pure" Selenium tests that do not offer [behavior-driven (BDD)](https://dzone.com/articles/the-basics-of-bdd-in-testing) or step-based capabilities. Add that to the fact that Selenium actively promotes the pattern and comes with `PageFactory` to support it, automation testers simply incorporated it to their BDD test frameworks by default and not by design.

> **TL;DR:**
>
> POM and BDD do not mix.

### Cucumber and DI
Cucumber inherently calls steps to move from one state to the next so mapping the user journey by [returning page objects](https://github.com/SeleniumHQ/selenium/wiki/PageObjects) within step definitions does not make much sense. [PicoContainer](https://docs.cucumber.io/cucumber/state/#dependency-injection) (developed by [Aslak Hellesøy](https://twitter.com/aslak_hellesoy)) eliminates the tight coupling of page objects to step definitions by sharing states in the glue code using [Dependency Injection](http://picocontainer.com/injection.html). It requires minimal configuration and injects the needed classes via the constructor. With shared objects, each step definition becomes an autonomous unit as is the nature of a Java method. The current step does not need to know the step before or after (loose coupling) making it much reusable.

In fact, there is no mention of POM in [The Cucumber for Java Book](https://pragprog.com/book/srjcuc/the-cucumber-for-java-book) but there is _Chapter 11: Simplifying Design with Dependency Injection_. The no-frills design allows for a faster return on investment (ROI) and a compact codebase to maintain in the long haul.

> **TL;DR:**
>
> - Selenium WebDriver + POM = OK
>
> - Selenium WebDriver + Cucumber + POM = Not OK
>
> - Selenium WebDriver + Cucumber + DI = ROI (fast, simple, and lightweight)

[ [Back](#table-of-contents) ]

## How It Works
The code below shows writing test scripts directly into step definitions because why not?

### ui-map.properties:
~~~
net.per.month=binding:roundDown(monthlyNet())
~~~

### Gherkin Syntax:
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
    LOGGER.debug("Net Per Month={}", actual);
}
~~~

[User Interface (UI) Mapping](https://www.seleniumhq.org/docs/06_test_design_considerations.jsp#user-interface-mapping) complements DI and is a known approach for keeping web elements in a single file. The `WebDriver` is not exposed while containing everything within the method in plain sight.

No need to plow through **76** page objects. Based on a true story.

[ [Back](#table-of-contents) ]

## Features
- [Selenium WebDriver 3](https://www.seleniumhq.org/) for browser automation
- [WebDriverManager](https://github.com/bonigarcia/webdrivermanager) for automatic management of webdriver binaries
- [ngWebDriver](https://github.com/paul-hammant/ngWebDriver) (Protractor) for Angular/JS support
- [Cucumber-JVM 3](https://github.com/cucumber/cucumber-jvm) for behavior-driven testing
- [PicoContainer](http://picocontainer.com/) for DI module
- [AssertJ](http://joel-costigliola.github.io/assertj/) for fluent assertions
- [Maven](https://maven.apache.org/) for dependency management and build execution
- [Log4j2](https://logging.apache.org/log4j/2.x/) / [SLF4J](https://www.slf4j.org/) for logging mechanism
- Extended Selenium API for commonly used web testing actions
- UI Map for central object repository of web elements
- Compatible with IE11, Edge, Chrome, Firefox
- Test result generation in HTML, JSON, XML
- Embedded screenshots on generated HTML reports

[ [Back](#table-of-contents) ]

## Prerequisites
- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) or higher
- [Eclipse IDE](http://www.eclipse.org/downloads/eclipse-packages/) / [VSCode](https://code.visualstudio.com/download) / [IntelliJ](https://www.jetbrains.com/idea/download/#section=windows) (install relevant Cucumber/Gherkin plugins)
- [Git](https://git-scm.com/downloads)
- [Maven](https://maven.apache.org/download.cgi)
- [Cmder](http://cmder.net/) (optional, includes Git for Windows)

[ [Back](#table-of-contents) ]

## Running Tests
Visit the application under test (AUT) here: http://simplydo.com/projector/

No further configurations needed at this point. The tests will run against the AUT in [headless browser](https://en.wikipedia.org/wiki/Headless_browser) mode using ChromeDriver as defined in `framework.properties`.

**To run the tests:**

Git Bash or Cmder is recommended.
~~~
$ cd /path/to/workspace/
$ git clone <repo-url>
$ cd jcucumberng-framework/
$ mvn verify
~~~

Maven performs a one-time download of all dependencies. Execute `mvn verify` again after the downloads complete to begin running the tests.

**Output:**
~~~
[INFO] Results:
[INFO]
[ERROR] Failures:
[ERROR]   expected:<...Do - Balance Project[]"> but was:<...Do - Balance Project[or]">
[INFO]
[ERROR] Tests run: 3, Failures: 1, Errors: 0, Skipped: 0
[INFO]
[ERROR] There are test failures.
~~~
1 scenario is purposely failed to produce variance in the test reports.

[ [Back](#table-of-contents) ]

## Checking Results
HTML reports and logs are created in the `/target/` directory after the build is successful.

### Static Reporting
Cucumber-JVM ships with its native (boring) HTML reporter that can be found in `/target/cucumber-html-default/`. Best for debugging.

**Output:**

![static_report](https://user-images.githubusercontent.com/28589393/44956144-d5ac7280-aef1-11e8-80ed-ccfeb9d2aaef.png)

### Dyamic Reporting (3-in-1)
Different reporting plugins generate animated visuals and colorful graphs/charts. Impressive for demos.

#### [Maven Cucumber Reporting](https://github.com/damianszczepanik/maven-cucumber-reporting)
> This report is standalone that can be zipped and emailed to clients. Any of the HTML files can be viewed locally using the browser.

Generate report into directory: `/target/cucumber-html-reports/`
~~~
mvn verify
~~~
This is the same task for test execution and report generation.

**Output:**

![maven_cucumber_reporting](https://user-images.githubusercontent.com/28589393/44955736-de4d7a80-aeea-11e8-803c-1dced0499fda.gif)

#### [Cucumber Extent Reporter](https://github.com/email2vimalraj/CucumberExtentReporter)
TODO

#### [Allure Test Report](https://github.com/allure-framework)
> This report is a single page application (SPA). Dynamic attributes use AJAX and need to be launched from a [running web server](https://github.com/allure-framework/allure1/issues/896#issuecomment-271599716) to view.

In Allure, test execution and report generation are different tasks. Choose any method to generate the report _after_ running the tests.

**Method 1:** Generate report into temp folder and start local web server (opens browser):
~~~
mvn allure:serve
~~~

**Method 2:** Generate report into directory: `/target/site/allure-maven-plugin/`
~~~
mvn allure:report
~~~

Alternatively, the commands can be combined:
~~~
mvn verify allure:serve
~~~
This also invokes all reporting plugins.

**Output:**

![allure_report](https://user-images.githubusercontent.com/28589393/44995297-0913fd80-afd5-11e8-9519-850218d84e1e.gif)

### Logging
Logs are written to a daily rolling file. Executions from the previous day are saved with a datestamp. Best for debugging.

**Directory:**
~~~
target/
|__ cucumber-logging/
    |__ cucumber_2018-07-19.log
    |__ cucumber_2018-07-20.log
    |__ cucumber_2018-07-21.log
    |__ cucumber.log
~~~

**Output:**
~~~
[INFO ] 2018-07-21 22:02:40,107 ScenarioHook.beforeScenario() - BEGIN TEST -> Verify Page Title
[INFO ] 2018-07-21 22:02:44,191 ScenarioHook.beforeScenario() - Browser=CHROME32_NOHEAD
[INFO ] 2018-07-21 22:02:45,387 ScenarioHook.beforeScenario() - Screen Resolution (WxH)=1366x768
[DEBUG] 2018-07-21 22:02:49,642 HomePageNavigationSteps.I_Am_At_The_Home_Page() - Base URL=http://simplydo.com/projector/
[DEBUG] 2018-07-21 22:02:50,095 HomePageNavigationSteps.I_Should_See_Page_Title() - Window Title=Simply Do - Balance Projector
[INFO ] 2018-07-21 22:02:50,413 ScenarioHook.afterScenario() - END TEST -> Verify Page Title - PASSED
~~~

[ [Back](#table-of-contents) ]