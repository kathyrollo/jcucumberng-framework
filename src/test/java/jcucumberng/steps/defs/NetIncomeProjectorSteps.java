package jcucumberng.steps.defs;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;
import jcucumberng.api.Selenium;
import jcucumberng.steps.domain.Expense;
import jcucumberng.steps.domain.Income;
import jcucumberng.steps.hooks.ScenarioHook;

public class NetIncomeProjectorSteps {
	private static final Logger logger = LogManager.getLogger(NetIncomeProjectorSteps.class);
	private WebDriver driver = null;

	// PicoContainer injects ScenarioHook class
	public NetIncomeProjectorSteps(ScenarioHook scenarioHook) {
		driver = scenarioHook.getDriver();
	}

	@When("I Enter My Start Balance: {word}")
	public void I_Enter_My_Start_Balance(String startBalance) throws Throwable {
		Selenium.enterText(driver, "start.balance", startBalance);
		logger.debug("Start Balance=" + startBalance);
	}

	@When("I Enter My Regular Income Sources")
	public void I_Enter_My_Regular_Income_Sources(DataTable dataTable) throws Throwable {
		List<Income> incomes = dataTable.asList(Income.class);
		for (int ctr = 0; ctr < incomes.size() - 1; ctr++) {
			Selenium.clickElement(driver, "income.add.btn");
		}
		List<WebElement> incomeNameTextFields = driver.findElements(Selenium.by("income.name.txt"));
		List<WebElement> incomeAmountTextFields = driver.findElements(Selenium.by("income.amount.txt"));
		List<Select> incomeFreqDropMenus = Selenium.getSelectElements(driver, "income.freq.drop");
		for (int ctr = 0; ctr < incomes.size(); ctr++) {
			Selenium.enterText(driver, incomeNameTextFields.get(ctr), incomes.get(ctr).getName());
			Selenium.enterText(driver, incomeAmountTextFields.get(ctr), incomes.get(ctr).getAmount());
			Selenium.selectFromDropMenuByText(driver, incomeFreqDropMenus.get(ctr), incomes.get(ctr).getFrequency());
			logger.debug(incomes.get(ctr).toString());
		}
	}

	@When("I Enter My Regular Expenses")
	public void I_Enter_My_Regular_Expenses(DataTable dataTable) throws Throwable {
		List<Expense> expenses = dataTable.asList(Expense.class);
		for (int ctr = 0; ctr < expenses.size() - 1; ctr++) {
			Selenium.clickElement(driver, "expense.add.btn");
		}
		List<WebElement> expenseNameTextFields = driver.findElements(Selenium.by("expense.name.txt"));
		List<WebElement> expenseAmountTextFields = driver.findElements(Selenium.by("expense.amount.txt"));
		List<Select> expenseFreqDropMenus = Selenium.getSelectElements(driver, "expense.freq.drop");
		for (int ctr = 0; ctr < expenses.size(); ctr++) {
			Selenium.enterText(driver, expenseNameTextFields.get(ctr), expenses.get(ctr).getName());
			Selenium.enterText(driver, expenseAmountTextFields.get(ctr), expenses.get(ctr).getAmount());
			Selenium.selectFromDropMenuByText(driver, expenseFreqDropMenus.get(ctr), expenses.get(ctr).getFrequency());
			logger.debug(expenses.get(ctr).toString());
		}
	}

	@Then("I Should See Net Income Per Month: {word}")
	public void I_Should_See_Net_Income_Per_Month(String netPerMonth) throws Throwable {
		WebElement netPerMonthTd = driver.findElement(Selenium.by("net.per.month"));
		String netPerMonthText = netPerMonthTd.getText();
		Assertions.assertThat(netPerMonthText).isEqualTo(netPerMonth);
		logger.debug("Net Per Month=" + netPerMonthText);
		Selenium.scrollVertical(driver, 500);
	}

	@Then("I Should See Net Income Per Year: {word}")
	public void I_Should_See_Net_Income_Per_Year(String netPerYear) throws Throwable {
		WebElement netPerYearTd = driver.findElement(Selenium.by("net.per.year"));
		String netPerYearText = netPerYearTd.getText();
		Assertions.assertThat(netPerYearText).isEqualTo(netPerYear);
		logger.debug("Net Per Year=" + netPerYearText);
		Selenium.scrollVertical(driver, 500);
	}

}
