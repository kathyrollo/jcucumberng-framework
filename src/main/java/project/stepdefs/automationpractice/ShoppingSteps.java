package project.stepdefs.automationpractice;

import java.io.IOException;

import org.assertj.core.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import jcucumberng.api.Selenium;
import project.hooks.ScenarioHook;

public class ShoppingSteps {

	private static final Logger LOGGER = LoggerFactory.getLogger(ShoppingSteps.class);
	private Selenium selenium = null;

	// PicoContainer injects ScenarioHook object
	public ShoppingSteps(ScenarioHook scenarioHook) {
		selenium = scenarioHook.getSelenium();
	}

	@Given("I Navigate To Dresses > Summer Dress")
	public void I_Navigate_To_Dresses_Summer_Dress() throws Throwable {
		selenium.navigate("ap.menu.dresses");
		selenium.click("ap.dresses.summer");
	}

}
