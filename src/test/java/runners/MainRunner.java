package runners;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features", tags = { "~@ignore" }, glue = "stepdefs", plugin = {
		"pretty", "html:target/cucumber-reports/test-report", "json:target/cucumber-reports/test-report.json",
		"junit:target/cucumber-reports/test-report.xml" }, snippets = SnippetType.UNDERSCORE, monochrome = true, dryRun = false)

public class MainRunner {
	// Right-Click > Run As > JUnit Test

	// TODO: Update to Cucumber 2.x
	// TODO: Add cucumber-reporting
	// TODO: Add Log4j
	// TODO: Implement PageFactory
	// TODO: Update README.md
}
