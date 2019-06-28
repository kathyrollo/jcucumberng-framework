package project.stepdefs.simplydo;

import org.assertj.core.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cucumber.api.java.en.Then;
import jcucumberng.api.Selenium;
import project.hooks.ScenarioHook;

public class HomePageSteps {

	private static final Logger LOGGER = LoggerFactory.getLogger(HomePageSteps.class);
	private Selenium selenium = null;

	// PicoContainer injects ScenarioHook object
	public HomePageSteps(ScenarioHook scenarioHook) {
		selenium = scenarioHook.getSelenium();
	}

	@Then("I Should See Page Title: {string}")
	public void I_Should_See_Page_Title(String expected) throws Throwable {
		String actual = selenium.getPageTitle();
		LOGGER.debug("Page Title={}", actual);
		Assertions.assertThat(actual).isEqualTo(expected);
	}

}
