package jcucumberng.steps.hooks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import cucumber.api.Scenario;
import cucumber.api.java.AfterStep;
import jcucumberng.api.Selenium;

public class StepHook {
	// TODO: Remove suppress warning when used
	@SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(StepHook.class);
	private Scenario scenario = null;
	private WebDriver driver = null;

	// PicoContainer injects ScenarioHook class
	public StepHook(ScenarioHook scenarioHook) {
		scenario = scenarioHook.getScenario();
		driver = scenarioHook.getDriver();
	}

	@AfterStep
	public void afterStep() throws Throwable {
		Selenium.captureScreenshot(driver);
		Selenium.embedScreenshot(driver, scenario);
	}

}
