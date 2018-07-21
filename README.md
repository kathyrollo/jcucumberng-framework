# jCucumberNG-Framework

## Overview
Allows automation testers to easily write Feature/Gherkin files for Cucumber and implement step definitions in plain Java classes. ngWebDriver (Protractor) offers support for Angular/JS web applications.

## Features
Supports the following capabilities:
- API for commonly used web testing actions
- Central object repository for UI elements
- Cucumber PicoContainer for dependency injection
- Compatibile with IE11, Edge, Chrome, Firefox (extendable)
- Uses Maven for build and test execution via cmdline
- Uses Log4j2 for logging mechanism with daily rolling file
- Automated test result generation in HTML, JSON, XML
- Embedded screenshots in HTML reports

## Prerequisites
The following are required:
- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) or higher
- [Eclipse IDE](http://www.eclipse.org/downloads/eclipse-packages/) / [VSCode](https://code.visualstudio.com/download) / [IntelliJ](https://www.jetbrains.com/idea/download/#section=windows)
- [Git](https://git-scm.com/downloads)
- [Maven](https://maven.apache.org/download.cgi)
- [Cmder](http://cmder.net/) (optional, includes Git for Windows)

Setup and installation of the above are not in the scope of this guide. Check the corresponding documentation or tutorials accordingly.

## Getting Started
We will run the tests against the following web application: http://simplydo.com/projector/

No further configurations needed at this point. The tests will execute in [headless](https://en.wikipedia.org/wiki/Headless_browser) mode as defined in the default framework settings.

Run the following commands in the cmdline:
~~~
$ cd /path/to/workspace/
$ git clone <https or ssh>
$ cd jcucumberng-framework
$ mvn verify
~~~

Artefacts are created in the `/target/` directory after the test run is complete.

### Reporting
Below is the generated dynamic HTML report in `/target/cucumber-html-reports/`:
![dynamic_report_1](https://user-images.githubusercontent.com/28589393/42723826-fe23c716-8798-11e8-9b31-6e6148bba39a.png)

![dynamic_report_2](https://user-images.githubusercontent.com/28589393/42723842-75ee2386-8799-11e8-85d2-df309569c466.png)

Below is the generated static (default) HTML report in `/target/cucumber-html-default`:
![static_report_1](https://user-images.githubusercontent.com/28589393/42722129-31cbfd56-8779-11e8-8117-55a91f09a4db.png)

### Logging
Below is a sample of the generated logs in `/target/report-logs/`:
~~~
[INFO ] 2018-07-21 22:02:40,107 ScenarioHook.beforeScenario() - BEGIN TEST -> Verify Page Title
[INFO ] 2018-07-21 22:02:44,191 ScenarioHook.beforeScenario() - Browser=CHROME32_NOHEAD
[INFO ] 2018-07-21 22:02:45,387 ScenarioHook.beforeScenario() - Screen Resolution (WxH)=1366x768
[DEBUG] 2018-07-21 22:02:49,642 HomePageNavigationSteps.I_Am_At_The_Home_Page() - Base URL=http://simplydo.com/projector/
[DEBUG] 2018-07-21 22:02:50,095 HomePageNavigationSteps.I_Should_See_Page_Title() - Window Title=Simply Do - Balance Projector
[INFO ] 2018-07-21 22:02:50,413 ScenarioHook.afterScenario() - END TEST -> Verify Page Title - PASSED
~~~

The user guide is available in the project [Wiki](https://github.com/kathyrollo/jcucumberng-framework/wiki).
