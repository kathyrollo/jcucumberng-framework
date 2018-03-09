package jcucumberng.runners;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@CucumberOptions(features = "src/test/resources/features", tags = { "~@ignore" }, glue = "jcucumberng/steps", plugin = {
		"pretty", "html:target/cucumber-generated-reports/cucumber-report",
		"json:target/cucumber-generated-reports/cucumber-report.json",
		"junit:target/cucumber-generated-reports/cucumber-report.xml" }, snippets = SnippetType.UNDERSCORE, monochrome = true, dryRun = false)

public class MainRunner extends AbstractTestNGCucumberTests {
}

// Right-Click > Run As > TestNG Test

// TODO: Implement Log4j 2
// TODO: Add testng.xml
// TODO: Update to Cucumber 2.x
// TODO: Integrate with Selenium Grid
// TODO: Integrate with Jenkins
// TODO: Update README.md
