package jcucumberng.steps.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.Select;

import jcucumberng.api.Selenium;
import jcucumberng.steps.pojos.Expense;
import jcucumberng.steps.pojos.Income;

/**
 * This is a template of a page object for the Page Object Model (POM). Declare
 * page elements as private fields. Implement page actions as public methods.
 * 
 * @author Katherine Rollo (rollo.katherine@gmail.com)
 */
public class HomePage {
	private final WebDriver driver;

	@FindBy(how = How.CSS, using = "input[ng-model='startBalance']")
	private WebElement startBalanceTxt = null;

	@FindBy(how = How.CSS, using = "button[ng-click='addIncome();']")
	private WebElement addIncomeBtn = null;

	@FindAll(value = { @FindBy(css = "input[ng-model='income.name']") })
	private List<WebElement> incomeNameFlds = null;

	@FindAll(value = { @FindBy(css = "input[ng-model='income.amount']") })
	private List<WebElement> incomeAmountFlds = null;

	@FindAll(value = { @FindBy(css = "select[ng-model='income.frequency']") })
	private List<WebElement> incomeFreqElements = null;

	@FindBy(how = How.CSS, using = "button[ng-click='addExpense();']")
	private WebElement addExpenseBtn = null;

	@FindAll(value = { @FindBy(css = "input[ng-model='expense.name']") })
	private List<WebElement> expenseNameFlds = null;

	@FindAll(value = { @FindBy(css = "input[ng-model='expense.amount']") })
	private List<WebElement> expenseAmountFlds = null;

	@FindAll(value = { @FindBy(css = "select[ng-model='expense.frequency']") })
	private List<WebElement> expenseFreqElements = null;

	@FindBy(how = How.CSS, using = "td[ng-class='positiveNegative(monthlyNet())']")
	private WebElement netPerMonthTd = null;

	@FindBy(how = How.CSS, using = "td[ng-class='positiveNegative((monthlyNet()*12)+tallyTransactions())']")
	private WebElement netPerYearTd = null;

	public HomePage(WebDriver driver) {
		this.driver = driver;
	}

	public void enterStartBalance(String startBalance) {
		Selenium.enterText(driver, startBalanceTxt, startBalance);
	}

	public void enterRegularIncomeSources(List<Income> incomes) {
		// Click add button based on number of items
		for (int ctr = 0; ctr < incomes.size() - 1; ctr++) {
			Selenium.clickElement(driver, addIncomeBtn);
		}

		List<Select> incomeFreqSelects = Selenium.getSelectElements(driver, incomeFreqElements);
		for (int ctr = 0; ctr < incomes.size(); ctr++) {
			Selenium.enterText(driver, incomeNameFlds.get(ctr), incomes.get(ctr).getName());
			Selenium.enterText(driver, incomeAmountFlds.get(ctr), incomes.get(ctr).getAmount());
			Selenium.selectFromDropMenuByText(driver, incomeFreqSelects.get(ctr), incomes.get(ctr).getFrequency());
		}
	}

	public void enterRegularExpenses(List<Expense> expenses) {
		for (int ctr = 0; ctr < expenses.size() - 1; ctr++) {
			Selenium.clickElement(driver, addExpenseBtn);
		}

		List<Select> expenseFreqSelects = Selenium.getSelectElements(driver, expenseFreqElements);
		for (int ctr = 0; ctr < expenses.size(); ctr++) {
			Selenium.enterText(driver, expenseNameFlds.get(ctr), expenses.get(ctr).getName());
			Selenium.enterText(driver, expenseAmountFlds.get(ctr), expenses.get(ctr).getAmount());
			Selenium.selectFromDropMenuByText(driver, expenseFreqSelects.get(ctr), expenses.get(ctr).getFrequency());
		}
	}

	public WebElement getNetPerMonthTd() {
		return netPerMonthTd;
	}

	public void setNetPerMonthTd(WebElement netPerMonthTd) {
		this.netPerMonthTd = netPerMonthTd;
	}

	public WebElement getNetPerYearTd() {
		return netPerYearTd;
	}

	public void setNetPerYearTd(WebElement netPerYearTd) {
		this.netPerYearTd = netPerYearTd;
	}

}
