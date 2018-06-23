package jcucumberng.project.hooks;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import jcucumberng.framework.api.LocalMachine;
import jcucumberng.framework.api.PropsLoader;
import jcucumberng.framework.constants.ErrorMessages;

public class ScenarioHook {
	private static final Logger logger = LoggerFactory.getLogger(ScenarioHook.class);
	private Scenario scenario = null;
	private WebDriver driver = null;

	@Before
	public void beforeScenario(Scenario scenario) throws Throwable {
		this.scenario = scenario;
		logger.info("BEGIN TEST -> " + scenario.getName());
		logger.info("Id=" + scenario.getId());

		StringBuilder builder = new StringBuilder();
		builder.append(System.getProperty("user.dir").replace("\\", "/"));
		builder.append("/src/test/resources/webdrivers/");
		String driverPath = builder.toString().trim();

		String browser = PropsLoader.readConfig("browser");
		if (StringUtils.isBlank(browser)) {
			logger.error(ErrorMessages.NO_BROWSER);
			browser = "CHROME32_NOHEAD";
		}

		switch (browser.toUpperCase()) {
		case "CHROME32":
			System.setProperty("webdriver.chrome.driver", driverPath + "chromedriver_win32.exe");
			driver = new ChromeDriver();
			break;
		case "CHROME32_NOHEAD":
			this.setChromeNoHead(driverPath, "chromedriver_win32.exe");
			break;
		case "FF32":
			System.setProperty("webdriver.gecko.driver", driverPath + "geckodriver_win32.exe");
			driver = new FirefoxDriver();
			break;
		case "FF32_NOHEAD":
			this.setFirefoxNoHead(driverPath, "geckodriver_win32.exe");
			break;
		case "FF64":
			System.setProperty("webdriver.gecko.driver", driverPath + "geckodriver_win64.exe");
			driver = new FirefoxDriver();
			break;
		case "FF64_NOHEAD":
			this.setFirefoxNoHead(driverPath, "geckodriver_win64.exe");
			break;
		case "EDGE":
			System.setProperty("webdriver.edge.driver", driverPath + "MicrosoftWebDriver.exe");
			driver = new EdgeDriver();
			break;
		case "IE32":
			System.setProperty("webdriver.ie.driver", driverPath + "IEDriverServer_win32.exe");
			driver = new InternetExplorerDriver();
			break;
		case "IE64":
			System.setProperty("webdriver.ie.driver", driverPath + "IEDriverServer_win64.exe");
			driver = new InternetExplorerDriver();
			break;
		default:
			logger.error(ErrorMessages.UNSUPPORTED_BROWSER);
			this.setChromeNoHead(driverPath, "chromedriver_win32.exe");
			break;
		}
		logger.info("Browser=" + browser);

		Dimension dimension = LocalMachine.getDimension();
		driver.manage().window().setSize(dimension);
		logger.info("Screen Resolution (WxH)=" + dimension.getWidth() + "x" + dimension.getHeight());
	}

	@After
	public void afterScenario() {
		logger.info("END TEST -> " + scenario.getName() + " - " + scenario.getStatus());
		driver.quit();
	}

	public Scenario getScenario() {
		return scenario;
	}

	public WebDriver getDriver() {
		return driver;
	}

	private void setChromeNoHead(String driverPath, String driverBinary) {
		System.setProperty("webdriver.chrome.driver", driverPath + driverBinary);
		ChromeOptions chromeOpts = new ChromeOptions();
		chromeOpts.addArguments("--headless");
		driver = new ChromeDriver(chromeOpts);
	}

	private void setFirefoxNoHead(String driverPath, String driverBinary) {
		System.setProperty("webdriver.gecko.driver", driverPath + driverBinary);
		FirefoxBinary ffBin = new FirefoxBinary();
		ffBin.addCommandLineOptions("--headless");
		FirefoxOptions ffOpts = new FirefoxOptions();
		ffOpts.setBinary(ffBin);
		ffOpts.setLogLevel(FirefoxDriverLogLevel.INFO);
		driver = new FirefoxDriver(ffOpts);
	}

}
