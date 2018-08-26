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
import jcucumberng.framework.api.BrowserFactory;
import jcucumberng.framework.api.Selenium;
import jcucumberng.framework.utils.ConfigUtil;
import jcucumberng.framework.utils.SystemIO;

public class ScenarioHook {

	private static final Logger LOGGER = LoggerFactory.getLogger(ScenarioHook.class);
	private Selenium selenium = null;

	@Before
	public void beforeScenario(Scenario scenario) throws Throwable {
		LOGGER.info("BEGIN TEST -> " + scenario.getName());

		String browserConfig = ConfigUtil.framework("browser");
		WebDriver driver = BrowserFactory.getInstance(browserConfig);
		if (Boolean.parseBoolean(ConfigUtil.framework("wait.for.angular"))) {
			NgWebDriver ngWebDriver = new NgWebDriver((JavascriptExecutor) driver);
			ngWebDriver.waitForAngularRequestsToFinish();
		}
		LOGGER.info("Browser=" + browserConfig);

		selenium = new Selenium(driver, scenario);

		Dimension dimension = SystemIO.getDimension();
		selenium.getDriver().manage().window().setSize(dimension);
		LOGGER.info("Screen Resolution (WxH)=" + dimension.getWidth() + "x" + dimension.getHeight());
	}

	@After
	public void afterScenario() throws Throwable {
		if (Boolean.parseBoolean(ConfigUtil.framework("screenshot.on.fail"))) {
			if (selenium.getScenario().isFailed()) {
				selenium.embedScreenshot();
			}
		}
		LOGGER.info("END TEST -> " + selenium.getScenario().getName() + " - " + selenium.getScenario().getStatus());
		selenium.getDriver().quit();
	}

	public Selenium getSelenium() {
		return selenium;
	}

}
