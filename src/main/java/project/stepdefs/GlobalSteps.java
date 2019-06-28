package project.stepdefs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cucumber.api.java.en.Given;
import jcucumberng.api.Selenium;
import project.hooks.ScenarioHook;

public class GlobalSteps {

	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalSteps.class);
	private Selenium selenium = null;

	// PicoContainer injects ScenarioHook object
	public GlobalSteps(ScenarioHook scenarioHook) {
		selenium = scenarioHook.getSelenium();
	}

	@Given("I Am At Page: {word}")
	public void I_Am_At_Page(String key) throws Throwable {
		String url = selenium.navigate(key);
		LOGGER.debug("Page URL={}", url);
	}

}
