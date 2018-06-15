package jcucumberng.steps.defs;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ByChained;
import org.openqa.selenium.support.ui.Select;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;
import jcucumberng.api.Selenium;
import jcucumberng.steps.domain.RegularTransaction;
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
		Selenium.enterText(driver, startBalance, "start.balance.txt");
		logger.debug("Start Balance=" + startBalance);
		this.scrollToDivBox(0);
	}

	@When("I Enter My Regular Income Sources")
	public void I_Enter_My_Regular_Income_Sources(DataTable table) throws Throwable {
		List<RegularTransaction> txns = table.asList(RegularTransaction.class);
		this.enterRegularTransactions(txns, "income.add.btn", "income.name.txt", "income.amount.txt",
				"income.freq.select");
		this.scrollToDivBox(1);
	}

	@When("I Enter My Regular Expenses")
	public void I_Enter_My_Regular_Expenses(DataTable table) throws Throwable {
		List<RegularTransaction> txns = table.asList(RegularTransaction.class);
		this.enterRegularTransactions(txns, "expense.add.btn", "expense.name.txt", "expense.amount.txt",
				"expense.freq.select");
		this.scrollToDivBox(2);
	}

	@Then("I Should See Net Income Per Month: {word}")
	public void I_Should_See_Net_Income_Per_Month(String netPerMonth) throws Throwable {
		WebElement netPerMonthTd = driver.findElement(Selenium.by("net.per.month.td"));
		String netPerMonthText = netPerMonthTd.getText();
		Assertions.assertThat(netPerMonthText).isEqualTo(netPerMonth);
		logger.debug("Net Per Month=" + netPerMonthText);
		Selenium.scrollToElement(driver, netPerMonthTd);
	}

	@Then("I Should See Net Income Per Year: {word}")
	public void I_Should_See_Net_Income_Per_Year(String netPerYear) throws Throwable {
		WebElement netPerYearTd = driver.findElement(Selenium.by("net.per.year.td"));
		String netPerYearText = netPerYearTd.getText();
		Assertions.assertThat(netPerYearText).isEqualTo(netPerYear);
		logger.debug("Net Per Year=" + netPerYearText);
		Selenium.scrollToElement(driver, netPerYearTd);
	}

	private void enterRegularTransactions(List<RegularTransaction> txns, String addBtnKey, String nameFldKey,
			String amtFldKey, String freqSelectKey) throws IOException {
		// Click Add button
		for (int ctr = 0; ctr < txns.size() - 1; ctr++) {
			Selenium.clickElement(driver, addBtnKey);
		}
		// Enter details
		List<WebElement> nameFields = driver.findElements(Selenium.by(nameFldKey));
		List<WebElement> amtFields = driver.findElements(Selenium.by(amtFldKey));
		List<Select> freqSelects = Selenium.getSelectElements(driver, freqSelectKey);
		for (int ctr = 0; ctr < txns.size(); ctr++) {
			Selenium.enterText(driver, txns.get(ctr).getName(), nameFields.get(ctr));
			Selenium.enterText(driver, txns.get(ctr).getAmount(), amtFields.get(ctr));
			Selenium.selectByText(driver, txns.get(ctr).getFrequency(), freqSelects.get(ctr));
			logger.debug(txns.get(ctr).toString());
		}
	}

	private void scrollToDivBox(int index) throws IOException {
		List<WebElement> divBoxes = driver
				.findElements(new ByChained(Selenium.by("page.div.span7"), Selenium.by("page.div.box")));
		Selenium.scrollToElement(driver, divBoxes.get(index));
	}

}
