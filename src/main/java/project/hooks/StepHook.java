package project.hooks;

import cucumber.api.java.AfterStep;
import jcucumberng.api.properties.PropsLoader;
import jcucumberng.api.selenium.Selenium;

public class StepHook {

	private Selenium selenium = null;

	// PicoContainer injects ScenarioHook object
	public StepHook(ScenarioHook scenarioHook) {
		selenium = scenarioHook.getSelenium();
	}

	@AfterStep
	public void afterStep() throws Throwable {
		if (!Boolean.parseBoolean(PropsLoader.framework("screenshot.off"))) {
			if (!Boolean.parseBoolean(PropsLoader.framework("screenshot.on.fail"))) {
				selenium.embedScreenshot();
			}
		}
	}

}
