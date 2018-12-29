package project.stepdefs;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cucumber.api.Transpose;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;
import jcucumberng.api.Selenium;
import project.dataobjects.Transaction;
import project.hooks.ScenarioHook;

public class NetIncomeProjectorSteps {

	private static final String DIV_BOXES = "div.boxes";

	private static final Logger LOGGER = LoggerFactory.getLogger(NetIncomeProjectorSteps.class);
	private Selenium selenium = null;

	// PicoContainer injects ScenarioHook object
	public NetIncomeProjectorSteps(ScenarioHook scenarioHook) {
		selenium = scenarioHook.getSelenium();
	}

	@When("I Enter My Start Balance: {word}")
	public void I_Enter_My_Start_Balance(String startBalance) throws Throwable {
		selenium.type(startBalance, "start.balance");
		LOGGER.debug("Start Balance={}", startBalance);
		selenium.scrollToElement(selenium.getVisibleElements(DIV_BOXES).get(0));
	}

	@When("I Enter My Regular Income Sources")
	public void I_Enter_My_Regular_Income_Sources(@Transpose Transaction transaction) throws Throwable {
		selenium.type(transaction.getName(), "income.name");
		selenium.type(transaction.getAmount(), "income.amount");
		selenium.selectByVisibleText(transaction.getFrequency(), "income.freq");
		LOGGER.debug("{}", transaction);
		selenium.scrollToElement(selenium.getVisibleElements(DIV_BOXES).get(1));
	}

	@When("I Enter My Regular Expenses")
	public void I_Enter_My_Regular_Expenses(DataTable table) throws Throwable {
		List<Transaction> transactions = table.asList(Transaction.class);
		// Click add button
		for (int ctr = 0; ctr < transactions.size() - 1; ctr++) {
			selenium.click("expense.add");
		}
		// Enter expenses
		List<WebElement> names = selenium.getVisibleElements("expense.name");
		List<WebElement> amounts = selenium.getVisibleElements("expense.amount");
		List<Select> freqs = selenium.getSelectElements("expense.freq");
		for (int ctr = 0; ctr < transactions.size(); ctr++) {
			selenium.type(transactions.get(ctr).getName(), names.get(ctr));
			selenium.type(transactions.get(ctr).getAmount(), amounts.get(ctr));
			selenium.selectByVisibleText(transactions.get(ctr).getFrequency(), freqs.get(ctr));
			LOGGER.debug("{}", transactions.get(ctr));
		}
		selenium.scrollToElement(selenium.getVisibleElements(DIV_BOXES).get(2));
	}

	@Then("I Should See Net Income Per Month: {word}")
	public void I_Should_See_Net_Income_Per_Month(String expected) throws Throwable {
		WebElement netPerMonth = selenium.getVisibleElement("net.per.month");
		String actual = netPerMonth.getText();
		Assertions.assertThat(actual).isEqualTo(expected);
		LOGGER.debug("Net Per Month={}", actual);
		selenium.scrollToElement(netPerMonth);
	}

	@Then("I Should See Net Income Per Year: {word}")
	public void I_Should_See_Net_Income_Per_Year(String expected) throws Throwable {
		WebElement netPerYear = selenium.getVisibleElement("net.per.year");
		String actual = netPerYear.getText();
		Assertions.assertThat(actual).isEqualTo(expected);
		LOGGER.debug("Net Per Year={}", actual);
		selenium.scrollToElement(netPerYear);
	}

}
