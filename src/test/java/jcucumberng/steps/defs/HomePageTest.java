package jcucumberng.steps.defs;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import cucumber.api.Scenario;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import jcucumberng.api.Configuration;
import jcucumberng.api.Selenium;
import jcucumberng.steps.hooks.BaseHook;

public class HomePageTest {
	private static final Logger logger = LogManager.getLogger(HomePageTest.class);
	private Scenario scenario = null;
	private WebDriver driver = null;

	// PicoContainer injects BaseHook class
	public HomePageTest(BaseHook baseHook) {
		this.scenario = baseHook.getScenario();
		this.driver = baseHook.getDriver();
	}

	@Given("^I Am At The Home Page$")
	public void I_Am_At_The_Home_Page() {
		String baseUrl = null;
		try {
			baseUrl = Configuration.readKey("base_url");
		} catch (IOException ioe) {
			logger.error("Cannot find config.properties file in /src/test/resources/.");
			ioe.printStackTrace();
		}
		logger.debug("Navigating to website: " + baseUrl);
		driver.get(baseUrl);
		Selenium.embedScreenshot(driver, scenario);
	}

	@Then("^I Should See Page Title '(.*)'$")
	public void I_Should_See_Page_Title(String pageTitle) {
		String windowTitle = driver.getTitle();
		logger.debug("Window Title: " + windowTitle);
		Assert.assertEquals(windowTitle, pageTitle);
		Selenium.embedScreenshot(driver, scenario);
	}

}
