package project.stepdefs.mystore;

import org.assertj.core.api.Assertions;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import jcucumberng.api.Selenium;
import project.stepdefs.ContextSteps;

public class ShoppingSteps {

	private static final Logger LOGGER = LoggerFactory.getLogger(ShoppingSteps.class);
	private Selenium selenium = null;

	public ShoppingSteps(ContextSteps contextSteps) {
		selenium = contextSteps.getSelenium();
	}

	@When("I Add Item To Cart: {string}")
	public void I_Add_Item_To_Cart(String name) throws Throwable {
		selenium.getDriver().findElement(By.partialLinkText(name)).click();
		selenium.click("ap.add.to.cart");
		LOGGER.debug("Items={}", selenium.getVisibleElement("ap.checkout.items").getText());
	}

	@When("I Proceed To Checkout")
	public void I_Proceed_To_Checkout() throws Throwable {
		selenium.click("ap.checkout");
	}

	@Then("I Should See The Cart Summary: {string} {string} {string}")
	public void I_Should_See_The_Cart_Summary(String name, String color, String qty) throws Throwable {
		String cartDesc = selenium.getVisibleElement("ap.cart.desc").getText().replaceAll("[\r\n]+", " | ");
		String itemQty = selenium.getVisibleElement("ap.cart.qty").getAttribute("value");

		LOGGER.debug("Cart Desc={}", cartDesc);
		LOGGER.debug("Qty={}", itemQty);

		Assertions.assertThat(cartDesc).contains(name);
		Assertions.assertThat(cartDesc).contains(color);
		Assertions.assertThat(itemQty).isEqualTo(qty);
	}

}
