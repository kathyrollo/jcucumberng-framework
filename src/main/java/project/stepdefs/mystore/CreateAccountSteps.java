package project.stepdefs.mystore;

import org.assertj.core.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import jcucumberng.api.Selenium;
import project.stepdefs.ContextSteps;

public class CreateAccountSteps {

	private static final Logger LOGGER = LoggerFactory.getLogger(CreateAccountSteps.class);
	private Selenium selenium = null;

	public CreateAccountSteps(ContextSteps contextSteps) {
		selenium = contextSteps.getSelenium();
	}

	@When("I Enter Email: {string}")
	public void I_Enter_Email(String email) throws Throwable {
		selenium.type(email, "ap.email.create");
		selenium.click("ap.submit.create");
		LOGGER.debug("Email={}", email);
	}

	@Then("I Should See Page Heading: {string}")
	public void I_Should_See_Page_Heading(String expected) throws Throwable {
		Assertions.assertThat(selenium.refreshAndTextToBePresent(expected, "ap.page.heading")).isTrue();
	}

	@Then("I Should See Error Message: {string}")
	public void I_Should_See_Error_Message(String expected) throws Throwable {
		String actual = selenium.getVisibleElement("ap.create.acct.err").getText();
		LOGGER.debug("Error Message={}", actual);
		Assertions.assertThat(actual).isEqualTo(expected);
	}

}
