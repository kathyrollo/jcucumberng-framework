package jcucumberng.runners;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features", tags = { "~@ignore" }, glue = "jcucumberng/steps", plugin = {
		"pretty", "html:target/cucumber-generated-reports/cucumber-report", "json:target/cucumber-generated-reports/cucumber-report.json",
		"junit:target/cucumber-generated-reports/cucumber-report.xml" }, snippets = SnippetType.UNDERSCORE, monochrome = true, dryRun = false)

public class MainRunner {
	// Right-Click > Run As > JUnit Test

	// TODO: Integrate with Jenkins
	// TODO: Update to Cucumber 2.x
	// TODO: Add Log4j 2
	// TODO: Implement PageFactory
	// TODO: Update README.md
}
