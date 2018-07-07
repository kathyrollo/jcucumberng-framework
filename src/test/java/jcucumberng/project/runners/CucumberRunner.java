package jcucumberng.project.runners;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = { "src/test/resources/jcucumberng/project/features" }, tags = { "not @ignore" }, glue = {
		"jcucumberng/project/typeregistry", "jcucumberng/project/stepdefs", "jcucumberng/project/hooks" }, plugin = {
				"pretty", "html:target/jcucumberng-output/cucumber-html-default",
				"json:target/jcucumberng-output/test-report.json",
				"junit:target/jcucumberng-output/test-report.xml" }, snippets = SnippetType.UNDERSCORE, monochrome = true, strict = true, dryRun = false)

public class CucumberRunner {
}
