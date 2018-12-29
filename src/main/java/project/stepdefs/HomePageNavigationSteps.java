package project.stepdefs;

import org.assertj.core.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import jcucumberng.api.props.Loader;
import jcucumberng.api.selenium.Selenium;
import project.hooks.ScenarioHook;

public class HomePageNavigationSteps {

	private static final Logger LOGGER = LoggerFactory.getLogger(HomePageNavigationSteps.class);
	private Selenium selenium = null;

	// PicoContainer injects ScenarioHook object
	public HomePageNavigationSteps(ScenarioHook scenarioHook) {
		selenium = scenarioHook.getSelenium();
	}

	@Given("I Am At The Home Page")
	public void I_Am_At_The_Home_Page() throws Throwable {
		String baseUrl = Loader.project("base.url");
		selenium.getDriver().get(baseUrl);
		LOGGER.debug("Base URL={}", baseUrl);
	}

	@Then("I Should See Page Title: {string}")
	public void I_Should_See_Page_Title(String pageTitle) throws Throwable {
		String windowTitle = selenium.getDriver().getTitle();
		Assertions.assertThat(windowTitle).isEqualTo(pageTitle);
		LOGGER.debug("Window Title={}", windowTitle);
	}

}
