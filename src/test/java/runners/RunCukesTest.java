package runners;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@CucumberOptions(features = { "src/main/resources/features" }, tags = { "@automationPractice" }, glue = { "project.datatables",
		"project.hooks", "project.stepdefs" }, plugin = { "pretty",
				"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
				"io.qameta.allure.cucumber4jvm.AllureCucumber4Jvm", "html:target/cucumber-html-default",
				"json:target/cucumber-report.json",
				"junit:target/cucumber-report.xml" }, snippets = SnippetType.UNDERSCORE, monochrome = true, strict = true, dryRun = false)

public class RunCukesTest extends AbstractTestNGCucumberTests {
}
