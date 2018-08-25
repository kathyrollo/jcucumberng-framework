package jcucumberng.project.hooks;

import cucumber.api.java.AfterStep;
import jcucumberng.framework.api.Selenium;
import jcucumberng.framework.utils.Config;

public class StepHook {

	private Selenium selenium = null;

	// PicoContainer injects ScenarioHook object
	public StepHook(ScenarioHook scenarioHook) {
		selenium = scenarioHook.getSelenium();
	}

	@AfterStep
	public void afterStep() throws Throwable {
		if (!Boolean.parseBoolean(Config.framework("screenshot.on.fail"))) {
			selenium.embedScreenshot();
		}
	}

}
