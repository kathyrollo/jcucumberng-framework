package jcucumberng.steps.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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
	private By incNameTxtBy = ByAngular.model("income.name");
	private By incAmtTxtBy = ByAngular.model("income.amount");
	private By incFreqDropMenuBy = ByAngular.model("income.frequency");
	private By addExpenseBy = By.cssSelector("button[ng-click='addExpense();']");
	private By expNameTxtBy = ByAngular.model("expense.name");
	private By expAmtTxtBy = ByAngular.model("expense.amount");
	private By expFreqDropMenuBy = ByAngular.model("expense.frequency");
	private By netPerMonthBy = ByAngular.binding("roundDown(monthlyNet())");
	private By netPerYearBy = ByAngular.binding("roundDown(monthlyNet()*12)+tallyTransactions()");

	private WebElement netPerMonthEl = null;
	private WebElement netPerYearEl = null;

	public HomePage(WebDriver driver) {
		this.driver = driver;

		setNetPerMonthEl(driver.findElement(netPerMonthBy));
		setNetPerYearEl(driver.findElement(netPerYearBy));
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
			Selenium.enterText(driver, driver.findElements(incNameTxtBy).get(ctr), incomes.get(ctr).getName());
			Selenium.enterText(driver, driver.findElements(incAmtTxtBy).get(ctr), incomes.get(ctr).getAmount());
			Selenium.selectFromDropMenuByText(driver, Selenium.getSelectElements(driver, incFreqDropMenuBy).get(ctr),
					incomes.get(ctr).getFrequency());
		}
	}

	public void enterRegularExpenses(List<Expense> expenses) {
		for (int ctr = 0; ctr < expenses.size() - 1; ctr++) {
			Selenium.clickElement(driver, addExpenseBy);
		}

		for (int ctr = 0; ctr < expenses.size(); ctr++) {
			Selenium.enterText(driver, driver.findElements(expNameTxtBy).get(ctr), expenses.get(ctr).getName());
			Selenium.enterText(driver, driver.findElements(expAmtTxtBy).get(ctr), expenses.get(ctr).getAmount());
			Selenium.selectFromDropMenuByText(driver, Selenium.getSelectElements(driver, expFreqDropMenuBy).get(ctr),
					expenses.get(ctr).getFrequency());
		}
	}

	public WebElement getNetPerMonthEl() {
		return netPerMonthEl;
	}

	public void setNetPerMonthEl(WebElement netPerMonthEl) {
		this.netPerMonthEl = netPerMonthEl;
	}

	public WebElement getNetPerYearEl() {
		return netPerYearEl;
	}

	public void setNetPerYearEl(WebElement netPerYearEl) {
		this.netPerYearEl = netPerYearEl;
	}

}
