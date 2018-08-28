package project;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;
import jcucumberng.api.logger.Logger;

@RunWith(Cucumber.class)
@CucumberOptions(features = { "src/test/resources/features" }, tags = { "not @ignore" }, glue = { "project.datatable",
		"project.hooks", "project.stepdefs" }, plugin = { "pretty", "html:target/cucumber-html-default",
				"json:target/cucumber-report.json",
				"junit:target/cucumber-report.xml" }, snippets = SnippetType.UNDERSCORE, monochrome = true, strict = true, dryRun = false)

public class RunCukesTest {

	// No edit
	private static boolean isLoaded = false;

	@BeforeClass
	public static void beforeClass() {
		if (!isLoaded) {
			Logger.init();
			isLoaded = true;
		}
	}

}
