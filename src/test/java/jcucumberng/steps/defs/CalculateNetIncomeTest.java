package jcucumberng.steps.defs;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import cucumber.api.DataTable;
import cucumber.api.Scenario;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import jcucumberng.api.Selenium;
import jcucumberng.steps.business.NetIncome;
import jcucumberng.steps.hooks.BaseHook;
import jcucumberng.steps.pojos.Expense;
import jcucumberng.steps.pojos.Income;

public class CalculateNetIncomeTest {
	private static final Logger logger = LogManager.getLogger(CalculateNetIncomeTest.class);
	private Scenario scenario = null;
	private WebDriver driver = null;
	private NetIncome netIncome = null;

	// PicoContainer injects BaseHook class
	public CalculateNetIncomeTest(BaseHook baseHook) {
		scenario = baseHook.getScenario();
		driver = baseHook.getDriver();
		netIncome = PageFactory.initElements(driver, NetIncome.class);
	}

	@When("^I Enter My Start Balance: (.*)$")
	public void I_Enter_My_Start_Balance(String startBalance) {
		netIncome.enterStartBalance(startBalance);
		Selenium.embedScreenshot(driver, scenario);
	}

	@When("^I Enter My Regular Income Sources$")
	public void I_Enter_My_Regular_Income_Sources(DataTable dataTable) {
		List<Income> incomes = dataTable.asList(Income.class);
		netIncome.enterRegularIncomeSources(incomes);
		Selenium.embedScreenshot(driver, scenario);
	}

	@When("^I Enter My Regular Expenses$")
	public void I_Enter_My_Regular_Expenses(DataTable dataTable) {
		List<Expense> expenses = dataTable.asList(Expense.class);
		netIncome.enterRegularExpenses(expenses);
		Selenium.embedScreenshot(driver, scenario);
	}

	@Then("^I Should See Net Income Per Month: (.*)$")
	public void I_Should_See_Net_Income_Per_Month(String netPerMonth) {
		String netPerMonthText = netIncome.getNetPerMonthTd().getText();
		logger.debug("Net Per Month: " + netPerMonthText);
		Assertions.assertThat(netPerMonthText).isEqualTo(netPerMonth);

		Selenium.scrollVertical(driver, 500);
		Selenium.embedScreenshot(driver, scenario);
	}

	@Then("^I Should See Net Income Per Year: (.*)$")
	public void I_Should_See_Net_Income_Per_Year(String netPerYear) {
		String netPerYearText = netIncome.getNetPerYearTd().getText();
		logger.debug("Net Per Year: " + netPerYearText);
		Assertions.assertThat(netPerYearText).isEqualTo(netPerYear);

		Selenium.scrollVertical(driver, 500);
		Selenium.embedScreenshot(driver, scenario);
	}

}
