package jcucumberng.steps.defs;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import cucumber.api.DataTable;
import cucumber.api.Scenario;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import jcucumberng.api.Selenium;
import jcucumberng.steps.hooks.BaseHook;
import jcucumberng.steps.pages.HomePage;
import jcucumberng.steps.pojos.Expense;
import jcucumberng.steps.pojos.Income;

public class NetIncomeTest {
	private static final Logger logger = LogManager.getLogger(NetIncomeTest.class);
	private Scenario scenario = null;
	private WebDriver driver = null;
	private HomePage homePage = null;

	// PicoContainer injects BaseHook class
	public NetIncomeTest(BaseHook baseHook) {
		this.scenario = baseHook.getScenario();
		this.driver = baseHook.getDriver();
		homePage = PageFactory.initElements(driver, HomePage.class);
	}

	@When("^I Enter My Start Balance: (.*)$")
	public void I_Enter_My_Start_Balance(String startBalance) throws Throwable {
		homePage.enterStartBalance(startBalance);

		byte[] srcBytes = Selenium.captureScreenAsBytes(driver);
		scenario.embed(srcBytes, "image/png");
	}

	@When("^I Enter My Regular Income Sources$")
	public void I_Enter_My_Regular_Income_Sources(DataTable dataTable) throws Throwable {
		List<Income> incomes = dataTable.asList(Income.class);
		homePage.enterRegularIncomeSources(incomes);

		byte[] srcBytes = Selenium.captureScreenAsBytes(driver);
		scenario.embed(srcBytes, "image/png");
	}

	@When("^I Enter My Regular Expenses$")
	public void I_Enter_My_Regular_Expenses(DataTable dataTable) throws Throwable {
		List<Expense> expenses = dataTable.asList(Expense.class);
		homePage.enterRegularExpenses(expenses);

		byte[] srcBytes = Selenium.captureScreenAsBytes(driver);
		scenario.embed(srcBytes, "image/png");
	}

	@Then("^I Should See Net Income: (.*) (.*)$")
	public void I_Should_See_Net_Income(String netPerMonth, String netPerYear) throws Throwable {
		String netPerMonthText = homePage.getNetPerMonthTd().getText();
		String netPerYearText = homePage.getNetPerYearTd().getText();

		logger.debug("Net Per Month: " + netPerMonthText);
		logger.debug("Net Per Year: " + netPerYearText);

		Assert.assertEquals(netPerMonthText, netPerMonth);
		Assert.assertEquals(netPerYearText, netPerYear);

		Selenium.scrollVertical(driver, 500);
		byte[] srcBytes = Selenium.captureScreenAsBytes(driver);
		scenario.embed(srcBytes, "image/png");
	}

}
