package jcucumberng.project.stepdefs;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;
import jcucumberng.framework.api.Selenium;
import jcucumberng.project.domain.Transaction;
import jcucumberng.project.hooks.ScenarioHook;

public class NetIncomeProjectorSteps {
	private static final Logger LOGGER = LoggerFactory.getLogger(NetIncomeProjectorSteps.class);
	private WebDriver driver = null;

	// PicoContainer injects ScenarioHook class
	public NetIncomeProjectorSteps(ScenarioHook scenarioHook) {
		driver = scenarioHook.getDriver();
	}

	@When("I Enter My Start Balance: {word}")
	public void I_Enter_My_Start_Balance(String value) throws Throwable {
		Selenium.type(driver, value, "start.balance");
		LOGGER.debug("Start Balance=" + value);
		this.scrollToDivBox(0);
	}

	@When("I Enter My Regular Income Sources")
	public void I_Enter_My_Regular_Income_Sources(DataTable table) throws Throwable {
		List<Transaction> txns = table.asList(Transaction.class);
		this.enterTransaction(txns, "income.add", "income.name", "income.amount", "income.freq");
		this.scrollToDivBox(1);
	}

	@When("I Enter My Regular Expenses")
	public void I_Enter_My_Regular_Expenses(DataTable table) throws Throwable {
		List<Transaction> txns = table.asList(Transaction.class);
		this.enterTransaction(txns, "expense.add", "expense.name", "expense.amount", "expense.freq");
		this.scrollToDivBox(2);
	}

	@Then("I Should See Net Income Per Month: {word}")
	public void I_Should_See_Net_Income_Per_Month(String expected) throws Throwable {
		WebElement netPerMonth = Selenium.getVisibleElement(driver, "net.per.month");
		String actual = netPerMonth.getText();
		Assertions.assertThat(actual).isEqualTo(expected);
		LOGGER.debug("Net Per Month=" + actual);
		Selenium.scrollToElement(driver, netPerMonth);
	}

	@Then("I Should See Net Income Per Year: {word}")
	public void I_Should_See_Net_Income_Per_Year(String expected) throws Throwable {
		WebElement netPerYear = Selenium.getVisibleElement(driver, "net.per.year");
		String actual = netPerYear.getText();
		Assertions.assertThat(actual).isEqualTo(expected);
		LOGGER.debug("Net Per Year=" + actual);
		Selenium.scrollToElement(driver, netPerYear);
	}

	private void enterTransaction(List<Transaction> txns, String add, String name, String amount, String freq)
			throws Throwable {
		// Click Add button
		for (int ctr = 0; ctr < txns.size() - 1; ctr++) {
			Selenium.click(driver, add);
		}
		// Enter details
		List<WebElement> names = Selenium.getVisibleElements(driver, name);
		List<WebElement> amounts = Selenium.getVisibleElements(driver, amount);
		List<Select> freqs = Selenium.getSelectElements(driver, freq);
		for (int ctr = 0; ctr < txns.size(); ctr++) {
			Selenium.type(driver, txns.get(ctr).getName(), names.get(ctr));
			Selenium.type(driver, txns.get(ctr).getAmount(), amounts.get(ctr));
			freqs.get(ctr).selectByVisibleText(txns.get(ctr).getFrequency());
			LOGGER.debug(txns.get(ctr).toString());
		}
	}

	private void scrollToDivBox(int index) throws Throwable {
		List<WebElement> divBoxes = Selenium.getVisibleElements(driver, "div.boxes");
		Selenium.scrollToElement(driver, divBoxes.get(index));
	}

}
