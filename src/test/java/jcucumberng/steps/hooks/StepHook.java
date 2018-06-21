package jcucumberng.steps.hooks;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cucumber.api.Scenario;
import cucumber.api.java.AfterStep;
import jcucumberng.framework.api.Selenium;

public class StepHook {
	@SuppressWarnings("unused") // TODO Remove suppress warning when used
	private static final Logger logger = LoggerFactory.getLogger(StepHook.class);
	private Scenario scenario = null;
	private WebDriver driver = null;

	// PicoContainer injects ScenarioHook class
	public StepHook(ScenarioHook scenarioHook) {
		scenario = scenarioHook.getScenario();
		driver = scenarioHook.getDriver();
	}

	@AfterStep
	public void afterStep() throws Throwable {
		Selenium.embedScreenshot(driver, scenario);
	}

}
