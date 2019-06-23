package project.stepdefs;

import org.assertj.core.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import jcucumberng.api.Configuration;
import jcucumberng.api.Selenium;
import project.hooks.ScenarioHook;

public class HomePageSteps {

	private static final Logger LOGGER = LoggerFactory.getLogger(HomePageSteps.class);
	private Selenium selenium = null;

	// PicoContainer injects ScenarioHook object
	public HomePageSteps(ScenarioHook scenarioHook) {
		selenium = scenarioHook.getSelenium();
	}

	@Given("^I Am At The Home Page$")
	public void I_Am_At_The_Home_Page() throws Throwable {
		String baseUrl = Configuration.project("base.url");
		selenium.getDriver().get(baseUrl);
		LOGGER.debug("Base URL={}", baseUrl);
	}

	@Then("^I Should See Page Title: (.*)$")
	public void I_Should_See_Page_Title(String pageTitle) throws Throwable {
		String windowTitle = selenium.getDriver().getTitle();
		Assertions.assertThat(windowTitle).isEqualTo(pageTitle);
		LOGGER.debug("Window Title={}", windowTitle);
	}

}
