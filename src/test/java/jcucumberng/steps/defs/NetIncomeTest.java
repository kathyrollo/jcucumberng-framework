package jcucumberng.steps.defs;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import cucumber.api.DataTable;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import jcucumberng.api.Selenium;
import jcucumberng.steps.pages.HomePage;
import jcucumberng.steps.pojos.Expense;
import jcucumberng.steps.pojos.Income;

public class NetIncomeTest {
	private static final Logger logger = LogManager.getLogger(NetIncomeTest.class);

	private WebDriver driver = null;

	// PicoContainer injects ServiceHook class
	public NetIncomeTest(ServiceHook serviceHook) {
		this.driver = serviceHook.getDriver();
	}

	@When("^I Enter My Start Balance: (.*)$")
	public void I_Enter_My_Start_Balance(String startBalance) throws Throwable {
		HomePage homePage = new HomePage(driver);
		homePage.enterStartBalance(startBalance);
		Selenium.captureScreen(driver);
	}

	@When("^I Enter My Regular Income Sources$")
	public void I_Enter_My_Regular_Income_Sources(DataTable dataTable) throws Throwable {
		List<Income> incomes = dataTable.asList(Income.class);
		HomePage homePage = new HomePage(driver);
		homePage.enterRegularIncomeSources(incomes);
		Selenium.captureScreen(driver);
	}

	@When("^I Enter My Regular Expenses$")
	public void I_Enter_My_Regular_Expenses(DataTable dataTable) throws Throwable {
		List<Expense> expenses = dataTable.asList(Expense.class);
		HomePage homePage = new HomePage(driver);
		homePage.enterRegularExpenses(expenses);
		Selenium.captureScreen(driver);
	}

	@Then("^I Should See Net Income: (.*) (.*)$")
	public void I_Should_See_Net_Income(String netIncomePerMonth, String netIncomePerYear) throws Throwable {
		HomePage homePage = new HomePage(driver);
		String netIncomePerMonthText = homePage.getNetIncomePerMonthText();
		String netIncomePerYearText = homePage.getNetIncomePerYearText();

		logger.debug("Net Income Per Month: " + netIncomePerMonthText);
		logger.debug("Net Income Per Year: " + netIncomePerYearText);

		Assert.assertEquals(netIncomePerMonthText, netIncomePerMonth);
		Assert.assertEquals(netIncomePerYearText, netIncomePerYear);

		Selenium.scrollVertical(driver, 500);
		Selenium.captureScreen(driver);
	}

}
