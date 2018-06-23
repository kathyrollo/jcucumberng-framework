package jcucumberng.project.hooks;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import jcucumberng.framework.api.LocalMachine;
import jcucumberng.framework.api.PropsLoader;
import jcucumberng.framework.enums.Browser;
import jcucumberng.framework.factory.BrowserFactory;
import jcucumberng.framework.strings.Messages;

public class ScenarioHook {
	private static final Logger LOGGER = LoggerFactory.getLogger(ScenarioHook.class);
	private Scenario scenario = null;
	private WebDriver driver = null;

	@Before
	public void beforeScenario(Scenario scenario) throws Throwable {
		this.scenario = scenario;
		LOGGER.info("BEGIN TEST -> " + scenario.getName());
		LOGGER.info("Id=" + scenario.getId());

		String browserConfig = PropsLoader.readConfig("browser");
		Browser browser = null;
		try {
			browser = Browser.valueOf(browserConfig.toUpperCase());
		} catch (IllegalArgumentException iae) {
			LOGGER.error(Messages.UNSUPPORTED_BROWSER);
			browser = Browser.CHROME32_NOHEAD; // Set default browser if invalid
		}
		driver = BrowserFactory.getBrowser(browser);
		LOGGER.info("Browser=" + browser);

		Dimension dimension = LocalMachine.getDimension();
		driver.manage().window().setSize(dimension);
		LOGGER.info("Screen Resolution (WxH)=" + dimension.getWidth() + "x" + dimension.getHeight());
	}

	@After
	public void afterScenario() {
		LOGGER.info("END TEST -> " + scenario.getName() + " - " + scenario.getStatus());
		BrowserFactory.quitBrowser(driver);
	}

	public Scenario getScenario() {
		return scenario;
	}

	public WebDriver getDriver() {
		return driver;
	}

}
