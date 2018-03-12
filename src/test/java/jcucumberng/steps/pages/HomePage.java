package jcucumberng.steps.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.paulhammant.ngwebdriver.ByAngular;

import jcucumberng.api.Selenium;
import jcucumberng.steps.pojos.Expense;
import jcucumberng.steps.pojos.Income;

/**
 * This is a template of a page object for the Page Object Model (POM). Declare
 * page element locators as private fields. Implement page actions as public
 * methods.
 * 
 * @author Katherine Rollo (rollo.katherine@gmail.com)
 */
public class HomePage {
	private final WebDriver driver;

	private By startBalanceBy = ByAngular.model("startBalance");
	private By addIncomeBy = By.cssSelector("button[ng-click='addIncome();']");
	private By incNameFieldBy = ByAngular.model("income.name");
	private By incAmountFieldBy = ByAngular.model("income.amount");
	private By incFreqDropMenuBy = ByAngular.model("income.frequency");
	private By addExpenseBy = By.cssSelector("button[ng-click='addExpense();']");
	private By expNameFieldBy = ByAngular.model("expense.name");
	private By expAmountFieldBy = ByAngular.model("expense.amount");
	private By expFreqDropMenuBy = ByAngular.model("expense.frequency");
	private By netIncomePerMonthBy = ByAngular.binding("roundDown(monthlyNet())");
	private By netIncomePerYearBy = ByAngular.binding("roundDown(monthlyNet()*12)+tallyTransactions()");

	private String netIncomePerMonthText = null;
	private String netIncomePerYearText = null;

	public HomePage(WebDriver driver) {
		this.driver = driver;

		setNetIncomePerMonthText(driver.findElement(netIncomePerMonthBy).getText());
		setNetIncomePerYearText(driver.findElement(netIncomePerYearBy).getText());
	}

	public void enterStartBalance(String startBalance) {
		Selenium.enterText(driver, startBalanceBy, startBalance);
	}

	public void enterRegularIncomeSources(List<Income> incomes) {
		// Click add button based on number of items
		for (int ctr = 0; ctr < incomes.size() - 1; ctr++) {
			Selenium.clickElement(driver, addIncomeBy);
		}

		for (int ctr = 0; ctr < incomes.size(); ctr++) {
			Selenium.enterText(driver, driver.findElements(incNameFieldBy).get(ctr), incomes.get(ctr).getName());
			Selenium.enterText(driver, driver.findElements(incAmountFieldBy).get(ctr), incomes.get(ctr).getAmount());
			Selenium.selectFromDropMenuByText(driver, Selenium.getSelectElements(driver, incFreqDropMenuBy).get(ctr),
					incomes.get(ctr).getFrequency());
		}
	}

	public void enterRegularExpenses(List<Expense> expenses) {
		for (int ctr = 0; ctr < expenses.size() - 1; ctr++) {
			Selenium.clickElement(driver, addExpenseBy);
		}

		for (int ctr = 0; ctr < expenses.size(); ctr++) {
			Selenium.enterText(driver, driver.findElements(expNameFieldBy).get(ctr), expenses.get(ctr).getName());
			Selenium.enterText(driver, driver.findElements(expAmountFieldBy).get(ctr), expenses.get(ctr).getAmount());
			Selenium.selectFromDropMenuByText(driver, Selenium.getSelectElements(driver, expFreqDropMenuBy).get(ctr),
					expenses.get(ctr).getFrequency());
		}
	}

	public String getNetIncomePerMonthText() {
		return netIncomePerMonthText;
	}

	public void setNetIncomePerMonthText(String netIncomePerMonthText) {
		this.netIncomePerMonthText = netIncomePerMonthText;
	}

	public String getNetIncomePerYearText() {
		return netIncomePerYearText;
	}

	public void setNetIncomePerYearText(String netIncomePerYearText) {
		this.netIncomePerYearText = netIncomePerYearText;
	}

}
