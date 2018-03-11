package jcucumberng.steps.pages;

import org.openqa.selenium.WebDriver;

/**
 * This is a template of a page object for the Page Object Model (POM). Advised
 * only when the page object is used locally within a step method.
 * Cucumber/Gherkin inherently has independent steps rendering the POM
 * ineffective most of the time. POM is a recommended design pattern in Selenium
 * scripts that do not use Gherkin/feature-based frameworks.
 * 
 * @author Katherine Rollo (rollo.katherine@gmail.com)
 */
public class HomePage {
	@SuppressWarnings("unused")
	private final WebDriver driver;

	// TODO: Declare page element locators as private fields.

	public HomePage(WebDriver driver) {
		this.driver = driver;
	}

	// TODO: Implement page actions as public methods.

}
