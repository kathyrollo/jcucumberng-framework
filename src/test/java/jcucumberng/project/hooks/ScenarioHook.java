package jcucumberng.project.hooks;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import jcucumberng.framework.api.LocalMachine;
import jcucumberng.framework.api.PropsLoader;
import jcucumberng.framework.exceptions.LoggerConfigException;
import jcucumberng.framework.factory.BrowserFactory;
import jcucumberng.framework.strings.Messages;

public class ScenarioHook {
	// Set logger config
	static {
		StringBuilder builder = new StringBuilder();
		builder.append(System.getProperty("user.dir").replace("\\", "/"));
		builder.append("/src/test/resources/jcucumberng/framework/log4j2.xml");

		try {
			InputStream inputStream = new FileInputStream(builder.toString());
			ConfigurationSource source = new ConfigurationSource(inputStream);
			Configurator.initialize(null, source);
		} catch (Exception ex) {
			throw new LoggerConfigException(Messages.LOGGER_CONFIG_FAIL);
		}
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(ScenarioHook.class);
	private Scenario scenario = null;
	private WebDriver driver = null;

	@Before
	public void beforeScenario(Scenario scenario) throws Throwable {
		this.scenario = scenario;
		LOGGER.info("BEGIN TEST -> " + scenario.getName());

		String browserConfig = PropsLoader.configFramework("browser");
		driver = BrowserFactory.getBrowser(browserConfig);
		LOGGER.info("Browser=" + browserConfig);

		Dimension dimension = LocalMachine.getDimension();
		driver.manage().window().setSize(dimension);
		LOGGER.info("Screen Resolution (WxH)=" + dimension.getWidth() + "x" + dimension.getHeight());
	}

	@After
	public void afterScenario() throws Throwable {
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
