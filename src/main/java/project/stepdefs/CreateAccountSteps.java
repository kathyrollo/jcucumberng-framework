package project.stepdefs;

import org.assertj.core.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import jcucumberng.api.Selenium;
import project.hooks.ScenarioHook;

public class CreateAccountSteps {

	private static final Logger LOGGER = LoggerFactory.getLogger(CreateAccountSteps.class);
	private Selenium selenium = null;

	// PicoContainer injects ScenarioHook object
	public CreateAccountSteps(ScenarioHook scenarioHook) {
		selenium = scenarioHook.getSelenium();
	}

	@Given("I Am At The Authentication Page")
	public void I_Am_At_The_Authentication_Page() {
		// Write code here that turns the phrase above into concrete actions
		throw new cucumber.api.PendingException();
	}

	@When("I Enter A Valid Email: {word}")
	public void I_Enter_A_Valid_Email(String email) {
		// Write code here that turns the phrase above into concrete actions
		throw new cucumber.api.PendingException();
	}

	@Then("I Should See The Create An Account Page")
	public void I_Should_See_The_Create_An_Account_Page() {
		// Write code here that turns the phrase above into concrete actions
		throw new cucumber.api.PendingException();
	}

}
