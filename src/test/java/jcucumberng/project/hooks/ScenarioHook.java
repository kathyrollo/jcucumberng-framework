package jcucumberng.project.hooks;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.paulhammant.ngwebdriver.NgWebDriver;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import jcucumberng.framework.api.Config;
import jcucumberng.framework.api.Selenium;
import jcucumberng.framework.api.SystemIO;
import jcucumberng.framework.factory.BrowserFactory;

public class ScenarioHook {
	private static final Logger LOGGER = LoggerFactory.getLogger(ScenarioHook.class);
	private Scenario scenario = null;
	private WebDriver driver = null;

	@Before
	public void beforeScenario(Scenario scenario) throws Throwable {
		this.scenario = scenario;
		LOGGER.info("BEGIN TEST -> " + scenario.getName());

		String browserConfig = Config.framework("browser");
		driver = BrowserFactory.getInstance(browserConfig);
		if (Boolean.parseBoolean(Config.framework("wait.for.angular"))) {
			NgWebDriver ngWebDriver = new NgWebDriver((JavascriptExecutor) driver);
			ngWebDriver.waitForAngularRequestsToFinish();
		}
		LOGGER.info("Browser=" + browserConfig);

		Dimension dimension = SystemIO.getDimension();
		driver.manage().window().setSize(dimension);
		LOGGER.info("Screen Resolution (WxH)=" + dimension.getWidth() + "x" + dimension.getHeight());
	}

	@After
	public void afterScenario() throws Throwable {
		if (Boolean.parseBoolean(Config.framework("screenshot.on.fail"))) {
			if (scenario.isFailed()) {
				Selenium.embedScreenshot(driver, scenario);
			}
		}
		LOGGER.info("END TEST -> " + scenario.getName() + " - " + scenario.getStatus());
		driver.quit();
	}

	public Scenario getScenario() {
		return scenario;
	}

	public WebDriver getDriver() {
		return driver;
	}

}
