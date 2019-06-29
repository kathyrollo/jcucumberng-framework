package project.hooks;

import cucumber.api.java.AfterStep;
import jcucumberng.api.Configuration;
import jcucumberng.api.Selenium;

public class StepHook {

	private Selenium selenium = null;

	// PicoContainer injects ScenarioHook object
	public StepHook(ScenarioHook scenarioHook) {
		selenium = scenarioHook.getSelenium();
	}

	@AfterStep(order = 1)
	public void capture() throws Throwable {
		if (!Boolean.parseBoolean(Configuration.framework("screenshot.off"))) {
			if (!Boolean.parseBoolean(Configuration.framework("screenshot.on.fail"))) {
				selenium.embedScreenshot();
			}
		}
	}

}
