package jcucumberng.project.runners;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;
import jcucumberng.framework.api.ConfigLoader;

@RunWith(Cucumber.class)
@CucumberOptions(features = { "src/test/resources/jcucumberng/project/features" }, tags = { "not @ignore" }, glue = {
		"jcucumberng.project.typeregistry", "jcucumberng.project.stepdefs", "jcucumberng.project.hooks" }, plugin = {
				"pretty", "html:target/cucumber-html-default", "json:target/cucumber-report.json",
				"junit:target/cucumber-report.xml" }, snippets = SnippetType.UNDERSCORE, monochrome = true, strict = true, dryRun = false)

public class BalanceProjectorRunner {
	private static boolean isLoaded = false;

	@BeforeClass
	public static void loadLoggerConf() { // No edit
		if (!isLoaded) {
			ConfigLoader.loggerConf();
			isLoaded = true;
		}
	}

}
