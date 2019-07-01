package project.stepdefs.automationpractice;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	@When("^I Add Item To Cart: (.*)$")
	public void I_Add_Item_To_Cart(String itemName) throws Throwable {
		selenium.getDriver().findElement(By.partialLinkText(itemName)).click();
		selenium.click("ap.add.to.cart");
		LOGGER.debug("Items={}", selenium.getVisibleElement("ap.checkout.items").getText());
	}

	@When("^I Proceed To Checkout$")
	public void I_Proceed_To_Checkout() throws Throwable {		
		selenium.click("ap.checkout");
	}

}
