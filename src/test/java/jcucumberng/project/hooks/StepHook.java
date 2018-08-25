package jcucumberng.project.hooks;

import cucumber.api.java.AfterStep;
import jcucumberng.framework.api.Config;
import jcucumberng.framework.api.Selenium;

public class StepHook {

	private Selenium selenium = null;

	// PicoContainer injects ScenarioHook object
	public StepHook(ScenarioHook scenarioHook) {
		this.selenium = scenarioHook.getSelenium();
	}

	@AfterStep
	public void afterStep() throws Throwable {
		if (!Boolean.parseBoolean(Config.framework("screenshot.on.fail"))) {
			this.selenium.embedScreenshot();
		}
	}

}
