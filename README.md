![java_selenium_cucumber_protractor](https://user-images.githubusercontent.com/28589393/49656394-38288800-fa78-11e8-9e1b-d828342d2aca.png)

# jCucumberNG-Framework
Allows automation testers to write feature files for Cucumber and implement step definitions in basic Java classes. ngWebDriver (Protractor) offers extended support for Angular/JS web applications.

### Contents
1. [How It Works](#how-it-works)
2. [Technology Stack](#technology-stack)
3. [What You Need](#what-you-need)
4. [Running Tests](#running-tests)
5. [Checking Results](#checking-results)

## How It Works
Test script logic is implemented directly in step definitions using Dependency Injection (DI) to focus test automation on [developing tests instead of keeping up with page objects](https://www.linkedin.com/pulse/dependency-injection-write-tests-page-objects-katherine-rollo/). An intelligent UI Map serves as the central object repository of web element locators.

### Basic Usage

**project.properties**
~~~
ap.authentication=http://automationpractice.com/index.php?controller=authentication&back=my-account
~~~

**ui-map.properties**
~~~
# User Interface (UI) Map

# Patterns
# ui.element.key=locator:selector
# ui.element.key=css_containing_text:selector|text
# ui.element.key=by_all:key1|key2|keyN
# ui.element.key=by_chained:key1|key2|keyN

# Selenium Locators
# id, name, link_text, partial_link_text, tag, class, css, xpath, by_all,
# by_chained, by_id_or_name

# ngWebDriver (Protractor) Locators
# binding, model, button_text, css_containing_text, exact_binding,
# exact_repeater, options, partial_button_text, repeater

#------------------------------------------------------------------------------#

ap.email.create=by_id_or_name:email_create
ap.submit.create=by_id_or_name:SubmitCreate
ap.page.heading=xpath://h1[@class='page-heading']
~~~

**Feature**
~~~
Given I Am At Page: ap.authentication
When I Enter Email: username@xyz.com
Then I Should See Page Heading: 'CREATE AN ACCOUNT'
~~~

**StepDef**
~~~
private Selenium selenium = null; // Extended Selenium API

// PicoContainer injects ScenarioHook object
public NetIncomeSteps(ScenarioHook scenarioHook) {
    selenium = scenarioHook.getSelenium(); // Instantly begin using API
}

@Given("I Am At Page: {word}")
public void I_Am_At_Page(String key) throws Throwable {
	// Use key from project.properties for app settings
	selenium.navigate(key); // 1
}

@When("I Enter Email: {word}")
public void I_Enter_Email(String email) throws Throwable {
	// Use key from ui-map.properties for web elements
	selenium.type(email, "ap.email.create"); // 2
	selenium.click("ap.submit.create"); // 3
}

@Then("I Should See Page Heading: {string}")
public void I_Should_See_Page_Heading(String expected) throws Throwable {
	// Use fluent assertion
	Assertions.assertThat(selenium.refreshAndTextToBePresent(expected, "ap.page.heading")).isTrue(); // 4
}
~~~
4 lines of actual test script, 1 class.

[ [Back](#table-of-contents) ]

## Technology Stack

- [Selenium WebDriver 4](https://www.seleniumhq.org/) for browser automation with extended API
- [WebDriverManager](https://github.com/bonigarcia/webdrivermanager) for automatic management of webdriver binaries (IE11, Edge, Chrome, Firefox)
- [ngWebDriver](https://github.com/paul-hammant/ngWebDriver) (Protractor) for Angular/JS support
- [Cucumber-JVM 4](https://github.com/cucumber/cucumber-jvm) for behavior-driven testing
- [PicoContainer](http://picocontainer.com/) for Dependency Injection module
- [AssertJ](http://joel-costigliola.github.io/assertj/) for fluent assertions
- [SLF4J](https://www.slf4j.org/) / [Log4j2](https://logging.apache.org/log4j/2.x/) for logging mechanism
- [Masterthought](https://github.com/damianszczepanik/maven-cucumber-reporting) / [Extent Reports](https://github.com/extent-framework) / [Allure](https://github.com/allure-framework) for dynamic HTML test reports
- [iText 5](https://developers.itextpdf.com/itext-java) for handling PDF files
- [POI](https://poi.apache.org/) for handling office documents (Word, PowerPoint, Excel)
- [Fillo](https://codoid.com/fillo/) for SQL-like manipulation of Excel files

[ [Back](#table-of-contents) ]

## What You Need
- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) or higher
- [Eclipse IDE](http://www.eclipse.org/downloads/eclipse-packages/) / [VSCode](https://code.visualstudio.com/download) / [IntelliJ](https://www.jetbrains.com/idea/download/#section=windows) (install relevant Cucumber plugins)
- [Git](https://git-scm.com/downloads)
- [Maven](https://maven.apache.org/download.cgi)
- [Cmder](http://cmder.net/) (optional, includes Git for Windows)

[ [Back](#table-of-contents) ]

## Running Tests
The framework is running tests against 2 applications:
- http://automationpractice.com/index.php
- http://simplydo.com/projector/

No further configurations needed at this point. The tests will run in [headless browser](https://en.wikipedia.org/wiki/Headless_browser) mode using ChromeDriver as defined in `framework.properties`.

**To run the tests:**

Git Bash or Cmder is recommended.
~~~
$ cd /path/to/workspace/
$ git clone <repo-url>
$ cd jcucumberng-framework/
$ mvn verify
~~~

Maven performs a one-time download of all dependencies. Execute `mvn verify` again if needed after the downloads complete to begin running the tests.

**Output:**
~~~
5 Scenarios (5 passed)
18 Steps (18 passed)
9m12.749s

[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 555.177 s - in runners.RunCukesTest
[INFO]
[INFO] Results:
[INFO]
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0
[INFO]
[INFO]
[INFO] --- maven-jar-plugin:2.4:jar (default-jar) @ jcucumberng-framework ---
[INFO] Building jar: \path\to\jCucumberNG-Framework\target\jcucumberng-framework-4.0.0-SNAPSHOT.jar
[INFO]
[INFO] --- maven-cucumber-reporting:4.7.0:generate (execution) @ jcucumberng-framework ---
[INFO] About to generate Cucumber report.
Jun 28, 2019 5:02:44 PM net.masterthought.cucumber.ReportParser parseJsonFiles
INFO: File '\path\to\jCucumberNG-Framework\target\cucumber-report.json' contains 3 features
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  09:25 min
[INFO] Finished at: 2019-06-28T17:02:46+10:00
[INFO] ------------------------------------------------------------------------
~~~

[ [Back](#table-of-contents) ]

## Checking Results
HTML, JSON, XML test reports and logs are created in the `/target/` directory after the build.

### Static Reporting
Cucumber-JVM ships with its default HTML reporter. Best for debugging scripts.

Generate report into directory: `/target/cucumber-html-default/`
~~~
mvn verify
~~~

**Output:**

![static_report](https://user-images.githubusercontent.com/28589393/44956144-d5ac7280-aef1-11e8-80ed-ccfeb9d2aaef.png)

### Dyamic Reporting (3-in-1)
Animated visuals with colorful graphs and charts are generated by 3 different reporting plugins. Best for stakeholder demos.

### 1. Masterthought
> This report is standalone that can be zipped and emailed to clients. HTML files can be viewed locally using any browser.

Generate report into directory: `/target/cucumber-html-reports/`
~~~
mvn verify
~~~

**Output:**

![maven_cucumber_reporting](https://user-images.githubusercontent.com/28589393/44955736-de4d7a80-aeea-11e8-803c-1dced0499fda.gif)

### 2. Extent Reports
> This report is standalone that can be zipped and emailed to clients. HTML files can be viewed locally using any browser.

Generate report into directory: `/target/cucumber-extentreports/`
~~~
mvn verify
~~~

The same command generates Masterthought and Extent Reports.

**Output:**

![extent_reports_cucumber](https://user-images.githubusercontent.com/28589393/49515372-b139ab00-f8d1-11e8-99b3-7b077e06d572.gif)

### 3. Allure
> This report is a single page application (SPA). Dynamic attributes use AJAX and need to be launched from a [running web server](https://github.com/allure-framework/allure1/issues/896#issuecomment-271599716) to view.

**Method 1:** Generate report into temp folder and start local web server (launches browser)
~~~
mvn verify
mvn allure:serve
~~~

**Method 2:** Combine commands (run tests and invoke all reporting plugins)
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
    |__ jcucumberng_2019-06-21.log
    |__ jcucumberng_2019-06-22.log
    |__ jcucumberng_2019-06-23.log
    |__ jcucumberng.log
~~~

**Output:**
~~~
[INFO ] 2019-06-28 16:58:28,920 ScenarioHook.setUp() - BEGIN TEST -> Verify Page Title
[INFO ] 2019-06-28 16:58:28,921 ScenarioHook.setUp() - Browser=CHROME32_NOHEAD
[INFO ] 2019-06-28 16:58:30,618 ScenarioHook.setUp() - Screen Resolution (WxH)=1366x768
[DEBUG] 2019-06-28 16:59:49,148 GlobalSteps.I_Am_At_Page() - Page URL=http://simplydo.com/projector/
[DEBUG] 2019-06-28 16:59:49,505 GlobalSteps.I_Should_See_Page_Title() - Page Title=Simply Do - Balance Projector
[INFO ] 2019-06-28 16:59:49,863 ScenarioHook.tearDown() - END TEST -> Verify Page Title - PASSED
~~~

[ [Back](#table-of-contents) ]

## LICENSE

Copyright 2019 Katherine L. Rollo

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
```
   http://www.apache.org/licenses/LICENSE-2.0
```
Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.