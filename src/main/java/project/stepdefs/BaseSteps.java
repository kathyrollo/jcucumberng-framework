package project.stepdefs;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.AfterStep;
import cucumber.api.java.Before;
import jcucumberng.api.Browser;
import jcucumberng.api.Configuration;
import jcucumberng.api.Selenium;
import jcucumberng.utils.SystemUtil;

/**
 * Define hooks for scenarios and steps.
 */
public class BaseSteps {

	private static final Logger LOGGER = LoggerFactory.getLogger(BaseSteps.class);
	private WebDriver driver = null;
	private Selenium selenium = null;

	@Before(order = 1)
	public void setUp(Scenario scenario) throws Throwable {
		LOGGER.info("BEGIN TEST -> {}", scenario.getName());

		String webBrowser = Configuration.framework("web.browser");
		LOGGER.info("Browser={}", webBrowser);

		driver = Browser.getInstance(webBrowser);
		selenium = new Selenium(driver, scenario);
		if (Boolean.parseBoolean(Configuration.framework("wait.for.angular"))) {
			selenium.waitForAngular();
		}
		if (Boolean.parseBoolean("wait.for.pageload")) {
			long time = Long.parseLong(Configuration.framework("pageload.timeout"));
			driver.manage().timeouts().pageLoadTimeout(time, TimeUnit.SECONDS);
		}

		Dimension dimension = SystemUtil.getDimension();
		driver.manage().window().setSize(dimension);
		LOGGER.info("Screen Resolution (WxH)={}x{}", dimension.getWidth(), dimension.getHeight());
	}

	@After(order = 1)
	public void tearDown(Scenario scenario) throws Throwable {
		if (!Boolean.parseBoolean(Configuration.framework("screenshot.off"))) {
			if (Boolean.parseBoolean(Configuration.framework("screenshot.on.fail"))) {
				if (scenario.isFailed()) {
					selenium.embedScreenshot();
				}
			}
		}
		LOGGER.info("END TEST -> {} - {}", scenario.getName(), scenario.getStatus());
		driver.quit();
	}

	@AfterStep(order = 1)
	public void capture() throws Throwable {
		if (!Boolean.parseBoolean(Configuration.framework("screenshot.off"))) {
			if (!Boolean.parseBoolean(Configuration.framework("screenshot.on.fail"))) {
				selenium.embedScreenshot();
			}
		}
	}

	public Selenium getSelenium() {
		return selenium;
	}

}
