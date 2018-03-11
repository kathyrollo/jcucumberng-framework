package jcucumberng.runners;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@CucumberOptions(features = "src/test/resources/features", tags = {
		"not @ignore" }, glue = "jcucumberng/steps/defs", plugin = { "pretty",
				"html:target/cucumber-generated-reports/cucumber-report",
				"json:target/cucumber-generated-reports/cucumber-report.json",
				"junit:target/cucumber-generated-reports/cucumber-report.xml" }, snippets = SnippetType.UNDERSCORE, monochrome = true, dryRun = false)

public class MainRunner extends AbstractTestNGCucumberTests {
}

// Right-Click > Run As > TestNG Test

// TODO: Implement Log4j2
// TODO: Add testng.xml
// TODO: Integrate with Jenkins
// TODO: Integrate with Selenium Grid
// TODO: Update README.md
