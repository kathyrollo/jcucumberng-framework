package jcucumberng.steps.defs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.WebDriver;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import jcucumberng.api.PropsLoader;
import jcucumberng.steps.hooks.ScenarioHook;

public class HomePageNavigationSteps {
	private static final Logger logger = LogManager.getLogger(HomePageNavigationSteps.class);
	private WebDriver driver = null;

	// PicoContainer injects ScenarioHook class
	public HomePageNavigationSteps(ScenarioHook scenarioHook) {
		driver = scenarioHook.getDriver();
	}

	@Given("I Am At The Home Page")
	public void I_Am_At_The_Home_Page() throws Throwable {
		String baseUrl = PropsLoader.readConfig("base.url");
		logger.debug("Navigating to website=" + baseUrl);
		driver.get(baseUrl);
	}

	@Then("I Should See Page Title {string}")
	public void I_Should_See_Page_Title(String pageTitle) throws Throwable {
		String windowTitle = driver.getTitle();
		logger.debug("Window Title=" + windowTitle);
		Assertions.assertThat(windowTitle).isEqualTo(pageTitle);
	}

}
