![java_selenium_cucumber_protractor](https://user-images.githubusercontent.com/28589393/49656394-38288800-fa78-11e8-9e1b-d828342d2aca.png)

# jCucumberNG-Framework
Allows automation testers to write feature/gherkin files for Cucumber and implement step definitions in basic Java classes. ngWebDriver (Protractor) offers extended support for Angular/JS web applications.

### Table Of Contents
1. [How It Works](#how-it-works)
2. [Technology Stack](#technology-stack)
3. [What You Need](#what-you-need)
4. [Running Tests](#running-tests)
5. [Checking Results](#checking-results)

## How It Works
Test script logic can be placed directly in step definitions (methods) to focus test automation on [writing tests instead of page objects](https://www.linkedin.com/pulse/dependency-injection-write-tests-page-objects-katherine-rollo/) with Dependency Injection (DI). A UI Map is used for central object repository of web elements.

### ui-map.properties
~~~
net.per.month=binding:roundDown(monthlyNet())
~~~

### NetIncomeProjector.feature
~~~
Then I Should See Net Income Per Month: 23769
~~~

### NetIncomeProjectorSteps.java
~~~
private Selenium selenium = null; // Extended Selenium API

// PicoContainer injects ScenarioHook object
public NetIncomeProjectorSteps(ScenarioHook scenarioHook) {
    selenium = scenarioHook.getSelenium(); // Instantiate Selenium object with injected ScenarioHook
}

@Then("I Should See Net Income Per Month: {word}")
public void I_Should_See_Net_Income_Per_Month(String expected) throws Throwable {
    WebElement netPerMonth = selenium.getVisibleElement("net.per.month"); // Use Selenium object in class
    String actual = netPerMonth.getText();
    Assertions.assertThat(actual).isEqualTo(expected);
    LOGGER.debug("Net Per Month={}", actual);
    selenium.scrollToElement(netPerMonth);
}
~~~

[ [Back](#table-of-contents) ]

## Technology Stack

- [Selenium WebDriver 3](https://www.seleniumhq.org/) for browser automation (with extended API)
- [WebDriverManager](https://github.com/bonigarcia/webdrivermanager) for automatic management of webdriver binaries (IE11, Edge, Chrome, Firefox)
- [ngWebDriver](https://github.com/paul-hammant/ngWebDriver) (Protractor) for Angular/JS support
- [Cucumber-JVM 3](https://github.com/cucumber/cucumber-jvm) for behavior-driven testing
- [PicoContainer](http://picocontainer.com/) for DI module
- [AssertJ](http://joel-costigliola.github.io/assertj/) for fluent assertions
- [SLF4J](https://www.slf4j.org/) / [Apache Log4j2](https://logging.apache.org/log4j/2.x/) for logging mechanism
- [iText 5](https://developers.itextpdf.com/itext-java) for handling PDF files
- [Apache POI](https://poi.apache.org/) for handling office documents (Word, PowerPoint, Excel)
- [Fillo](https://codoid.com/fillo/) for SQL-like manipulation of Excel files

[ [Back](#table-of-contents) ]

## What You Need
- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) or higher
- [Eclipse IDE](http://www.eclipse.org/downloads/eclipse-packages/) / [VSCode](https://code.visualstudio.com/download) / [IntelliJ](https://www.jetbrains.com/idea/download/#section=windows) (install relevant Cucumber/Gherkin plugins)
- [Git](https://git-scm.com/downloads)
- [Apache Maven](https://maven.apache.org/download.cgi)
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
HTML reports and logs are created in the `/target/` directory after the build.

### Static Reporting
Cucumber-JVM ships with its default HTML reporter. Best for debugging scripts.

Generate report into directory: `/target/cucumber-html-default/`
~~~
mvn verify
~~~

**Output:**

![static_report](https://user-images.githubusercontent.com/28589393/44956144-d5ac7280-aef1-11e8-80ed-ccfeb9d2aaef.png)

### Dyamic Reporting (3-in-1)
Different reporting plugins generate animated visuals and colorful graphs/charts. Impressive for demos.

#### [Maven Cucumber Reporting](https://github.com/damianszczepanik/maven-cucumber-reporting)
> This report is standalone and can be zipped/emailed to clients. HTML files can be viewed locally using the browser.

Generate report into directory: `/target/cucumber-html-reports/`
~~~
mvn verify
~~~

**Output:**

![maven_cucumber_reporting](https://user-images.githubusercontent.com/28589393/44955736-de4d7a80-aeea-11e8-803c-1dced0499fda.gif)

#### [Cucumber Extent Reports](https://github.com/extent-framework)
> This report is standalone and can be zipped/emailed to clients. HTML files can be viewed locally using the browser.

Generate report into directory: `/target/extentreports-cucumber/`
~~~
mvn verify
~~~

**Output:**

![extent_reports_cucumber](https://user-images.githubusercontent.com/28589393/49515372-b139ab00-f8d1-11e8-99b3-7b077e06d572.gif)

#### [Allure Test Report](https://github.com/allure-framework)
> This report is a single page application (SPA). Dynamic attributes use AJAX and need to be launched from a [running web server](https://github.com/allure-framework/allure1/issues/896#issuecomment-271599716) to view.

Choose any method to generate the report **_after_** running the tests.

**Method 1:** Generate report into temp folder and start local web server (opens browser)
~~~
mvn allure:serve
~~~

**Method 2:** Generate report in `/target/site/allure-maven-plugin/`
~~~
mvn allure:report
~~~

**Method 3:** Combine commands (invoke all reporting plugins)
~~~
mvn verify allure:serve
~~~

**Output:**

![allure_report](https://user-images.githubusercontent.com/28589393/44995297-0913fd80-afd5-11e8-9519-850218d84e1e.gif)

### Logging
Logs are written to a daily rolling file. Executions from the previous day are saved with a datestamp. Best for debugging scripts.

**Directory:**
~~~
target/
|__ test-logs/
    |__ jcucumberng_2018-07-19.log
    |__ jcucumberng_2018-07-20.log
    |__ jcucumberng_2018-07-21.log
    |__ jcucumberng.log
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

## LICENSE

Copyright 2018 Katherine L. Rollo

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
```
   http://www.apache.org/licenses/LICENSE-2.0
```
Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.