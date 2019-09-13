package project.stepdefs;

import org.assertj.core.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import jcucumberng.api.Selenium;

/**
 * Define reusable steps for any application.
 */
public class GlobalSteps {

	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalSteps.class);
	private Selenium selenium = null;

	public GlobalSteps(ContextSteps contextSteps) {
		selenium = contextSteps.getSelenium();
	}

	@Given("I Am At Page: {string}")
	public void I_Am_At_Page(String key) throws Throwable {
		String url = selenium.navigate(key);
		LOGGER.debug("Page URL={}", url);
	}

	@Then("I Should See Page Title: {string}")
	public void I_Should_See_Page_Title(String expected) throws Throwable {
		String actual = selenium.getPageTitle();
		LOGGER.debug("Page Title={}", actual);
		Assertions.assertThat(actual).isEqualTo(expected);
	}

	@Then("I Should See Text: {string}")
	public void I_Should_See_Text(String text) {
		Assertions.assertThat(selenium.getBodyText().contains(text)).isTrue();
	}

}
