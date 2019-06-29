package project.hooks;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import jcucumberng.api.Browser;
import jcucumberng.api.Configuration;
import jcucumberng.api.Selenium;
import jcucumberng.utils.SystemUtil;

public class ScenarioHook {

	private static final Logger LOGGER = LoggerFactory.getLogger(ScenarioHook.class);
	private WebDriver driver = null;
	private Selenium selenium = null;

	@Before(order = 1)
	public void setUp(Scenario scenario) throws Throwable {
		LOGGER.info("BEGIN TEST -> {}", scenario.getName());

		String webBrowser = Configuration.framework("web.browser");
		LOGGER.info("Browser={}", webBrowser);

		driver = Browser.getInstance(webBrowser);
		if (Boolean.parseBoolean("implicit.wait")) {
			long time = Long.parseLong(Configuration.framework("implicit.timeout"));
			driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
		}
		selenium = new Selenium(driver, scenario);
		if (Boolean.parseBoolean(Configuration.framework("wait.for.angular"))) {
			selenium.waitForAngular();
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

	public Selenium getSelenium() {
		return selenium;
	}

}
