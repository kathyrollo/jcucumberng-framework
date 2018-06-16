package jcucumberng.runners;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@CucumberOptions(features = { "src/test/resources/features" }, tags = { "not @ignore" }, glue = {
		"jcucumberng/steps/config", "jcucumberng/steps/defs", "jcucumberng/steps/hooks" }, plugin = { "pretty",
				"html:target/cucumber-output/generated-report", "json:target/cucumber-output/cucumber-report.json",
				"junit:target/cucumber-output/cucumber-report.xml" }, snippets = SnippetType.UNDERSCORE, monochrome = true, strict = true, dryRun = false)

public class CucumberRunner extends AbstractTestNGCucumberTests {
}

// For Eclipse IDE: Right-Click > Run As > TestNG Test
