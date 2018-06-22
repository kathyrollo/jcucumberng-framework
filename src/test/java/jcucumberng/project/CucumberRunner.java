package jcucumberng.project;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@CucumberOptions(features = { "src/test/resources/features" }, tags = { "not @ignore" }, glue = {
		"jcucumberng/project/registry", "jcucumberng/project/stepdefs", "jcucumberng/project/hooks" }, plugin = {
				"pretty", "html:target/cucumber-output/test-report", "json:target/cucumber-output/test-report.json",
				"junit:target/cucumber-output/test-report.xml" }, snippets = SnippetType.UNDERSCORE, monochrome = true, strict = true, dryRun = false)

public class CucumberRunner extends AbstractTestNGCucumberTests {
}

// For Eclipse IDE: Right-Click > Run As > TestNG Test
