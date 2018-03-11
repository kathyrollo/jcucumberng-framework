package jcucumberng.stepdefs;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.paulhammant.ngwebdriver.ByAngular;

import cucumber.api.DataTable;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import jcucumberng.api.Selenium;
import jcucumberng.stepdefs.pojos.Expense;
import jcucumberng.stepdefs.pojos.Income;

public class NetIncomeTest {
	private WebDriver driver = null;

	// PicoContainer injects ServiceHook class
	public NetIncomeTest(ServiceHook serviceHook) {
		this.driver = serviceHook.getDriver();
	}

	@When("^I Enter My Start Balance: (.*)$")
	public void I_Enter_My_Start_Balance(String startBalance) throws Throwable {
		By startBalanceBy = ByAngular.model("startBalance");
		Selenium.enterText(driver, startBalanceBy, startBalance);
		Selenium.captureScreen(driver);
	}

	@When("^I Enter My Regular Income Sources$")
	public void I_Enter_My_Regular_Income_Sources(DataTable dataTable) throws Throwable {
		List<Income> incomes = dataTable.asList(Income.class);

		By addIncomeBy = By.cssSelector("button[ng-click='addIncome();']");
		By nameFieldBy = ByAngular.model("income.name");
		By amountFieldBy = ByAngular.model("income.amount");
		By freqDropMenuBy = ByAngular.model("income.frequency");

		for (int ctr = 0; ctr < incomes.size() - 1; ctr++) {
			Selenium.clickElement(driver, addIncomeBy);
		}

		List<WebElement> nameFields = driver.findElements(nameFieldBy);
		List<WebElement> amountFields = driver.findElements(amountFieldBy);
		List<Select> freqDropMenus = Selenium.getSelectElements(driver, freqDropMenuBy);

		for (int ctr = 0; ctr < incomes.size(); ctr++) {
			Selenium.enterText(driver, nameFields.get(ctr), incomes.get(ctr).getName());
			Selenium.enterText(driver, amountFields.get(ctr), incomes.get(ctr).getAmount());
			Selenium.selectFromDropMenuByText(driver, freqDropMenus.get(ctr), incomes.get(ctr).getFrequency());
		}

		Selenium.captureScreen(driver);
	}

	@When("^I Enter My Regular Expenses$")
	public void I_Enter_My_Regular_Expenses(DataTable dataTable) throws Throwable {
		List<Expense> expenses = dataTable.asList(Expense.class);

		By addExpenseBy = By.cssSelector("button[ng-click='addExpense();']");
		By nameFieldBy = ByAngular.model("expense.name");
		By amountFieldBy = ByAngular.model("expense.amount");
		By freqDropMenuBy = ByAngular.model("expense.frequency");

		for (int ctr = 0; ctr < expenses.size() - 1; ctr++) {
			Selenium.clickElement(driver, addExpenseBy);
		}

		List<WebElement> nameFields = driver.findElements(nameFieldBy);
		List<WebElement> amountFields = driver.findElements(amountFieldBy);
		List<Select> freqDropMenus = Selenium.getSelectElements(driver, freqDropMenuBy);

		for (int ctr = 0; ctr < expenses.size(); ctr++) {
			Selenium.enterText(driver, nameFields.get(ctr), expenses.get(ctr).getName());
			Selenium.enterText(driver, amountFields.get(ctr), expenses.get(ctr).getAmount());
			Selenium.selectFromDropMenuByText(driver, freqDropMenus.get(ctr), expenses.get(ctr).getFrequency());
		}

		Selenium.captureScreen(driver);
	}

	@Then("^I Should See Net Income: (.*) (.*)$")
	public void I_Should_See_Net_Income(String netIncomePerMonth, String netIncomePerYear) throws Throwable {
		By netIncomePerMonthBy = ByAngular.binding("roundDown(monthlyNet())");
		By netIncomePerYearBy = ByAngular.binding("roundDown(monthlyNet()*12)+tallyTransactions()");

		String netIncomePerMonthText = driver.findElement(netIncomePerMonthBy).getText();
		String netIncomePerYearText = driver.findElement(netIncomePerYearBy).getText();

		Assert.assertEquals(netIncomePerMonthText, netIncomePerMonth);
		Assert.assertEquals(netIncomePerYearText, netIncomePerYear);

		Selenium.scrollVertical(driver, 500);
		Selenium.captureScreen(driver);
	}

}
