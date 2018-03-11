package jcucumberng.stepdefs;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import jcucumberng.api.Configuration;
import jcucumberng.api.Selenium;

public class HomePageTest {
	private WebDriver driver = null;

	// PicoContainer injects ServiceHook class
	public HomePageTest(ServiceHook serviceHook) {
		this.driver = serviceHook.getDriver();
	}

	@Given("^I Am At The Home Page$")
	public void I_Am_At_The_Home_Page() throws Throwable {
		driver.get(Configuration.readKey("base_url"));
		driver.manage().window().maximize();
		Selenium.captureScreen(driver);
	}

	@Then("^I Should See Page Title '(.*)'$")
	public void I_Should_See_Page_Title(String pageTitle) throws Throwable {
		Assert.assertEquals(driver.getTitle(), pageTitle);
	}

}
