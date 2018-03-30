package jcucumberng.steps.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.Select;

import com.paulhammant.ngwebdriver.ByAngularBinding;
import com.paulhammant.ngwebdriver.ByAngularModel;

import jcucumberng.api.Selenium;
import jcucumberng.steps.pojos.Expense;
import jcucumberng.steps.pojos.Income;

/**
 * This is a template of a page object for the Page Object Model (POM). Declare
 * page elements as private fields. Implement page actions as public methods.
 * 
 * @author Kat Rollo (rollo.katherine@gmail.com)
 */
public class HomePage {
	private final WebDriver driver;

	@ByAngularModel.FindBy(rootSelector = "input", model = "startBalance")
	private WebElement startBalanceTxt = null;

	@FindBy(how = How.CSS, using = "button[ng-click='addIncome();']")
	private WebElement addIncomeBtn = null;

	@FindAll(value = { @FindBy(css = "input[ng-model='income.name']") })
	private List<WebElement> incomeNameTxts = null;

	@FindAll(value = { @FindBy(css = "input[ng-model='income.amount']") })
	private List<WebElement> incomeAmountTxts = null;

	@FindAll(value = { @FindBy(css = "select[ng-model='income.frequency']") })
	private List<WebElement> incomeFreqDropMenus = null;

	@FindBy(how = How.CSS, using = "button[ng-click='addExpense();']")
	private WebElement addExpenseBtn = null;

	@FindAll(value = { @FindBy(css = "input[ng-model='expense.name']") })
	private List<WebElement> expenseNameTxts = null;

	@FindAll(value = { @FindBy(css = "input[ng-model='expense.amount']") })
	private List<WebElement> expenseAmountTxts = null;

	@FindAll(value = { @FindBy(css = "select[ng-model='expense.frequency']") })
	private List<WebElement> expenseFreqDropMenus = null;

	@ByAngularBinding.FindBy(rootSelector = "td", binding = "roundDown(monthlyNet())")
	private WebElement netPerMonthTd = null;

	@ByAngularBinding.FindBy(rootSelector = "td", binding = "roundDown(monthlyNet()*12)+tallyTransactions()")
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

		List<Select> incomeFreqSelects = Selenium.getSelectElements(driver, incomeFreqDropMenus);
		for (int ctr = 0; ctr < incomes.size(); ctr++) {
			Selenium.enterText(driver, incomeNameTxts.get(ctr), incomes.get(ctr).getName());
			Selenium.enterText(driver, incomeAmountTxts.get(ctr), incomes.get(ctr).getAmount());
			Selenium.selectFromDropMenuByText(driver, incomeFreqSelects.get(ctr), incomes.get(ctr).getFrequency());
		}
	}

	public void enterRegularExpenses(List<Expense> expenses) {
		for (int ctr = 0; ctr < expenses.size() - 1; ctr++) {
			Selenium.clickElement(driver, addExpenseBtn);
		}

		List<Select> expenseFreqSelects = Selenium.getSelectElements(driver, expenseFreqDropMenus);
		for (int ctr = 0; ctr < expenses.size(); ctr++) {
			Selenium.enterText(driver, expenseNameTxts.get(ctr), expenses.get(ctr).getName());
			Selenium.enterText(driver, expenseAmountTxts.get(ctr), expenses.get(ctr).getAmount());
			Selenium.selectFromDropMenuByText(driver, expenseFreqSelects.get(ctr), expenses.get(ctr).getFrequency());
		}
	}

	public WebElement getNetPerMonthTd() {
		return netPerMonthTd;
	}

	public WebElement getNetPerYearTd() {
		return netPerYearTd;
	}

}
