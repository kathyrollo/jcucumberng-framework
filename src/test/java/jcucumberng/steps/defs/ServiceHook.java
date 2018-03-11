package jcucumberng.steps.defs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import jcucumberng.api.Configuration;
import jcucumberng.api.LocalSystem;

public class ServiceHook {
	private static final Logger logger = LogManager.getLogger(ServiceHook.class);

	private WebDriver driver = null;

	@Before
	public void setUp() throws Throwable {
		StringBuilder builder = new StringBuilder();
		builder.append(System.getProperty("user.dir").replace("\\", "/"));
		builder.append("/src/test/resources/webdrivers/");
		String driverPath = builder.toString().trim();

		FirefoxBinary ffBin = null;
		FirefoxOptions ffOpts = null;

		logger.info("Setting up driver...");
		String browser = Configuration.readKey("browser").toLowerCase();
		logger.info("Browser: " + browser);

		switch (browser) {
		case "chrome":
			System.setProperty("webdriver.chrome.driver", driverPath + "chromedriver_win32.exe");
			driver = new ChromeDriver();
			break;
		case "chrome-nohead":
			setDefaultDriver(driverPath);
			break;
		case "ff32":
			System.setProperty("webdriver.gecko.driver", driverPath + "geckodriver_win32.exe");
			driver = new FirefoxDriver();
			break;
		case "ff32-nohead":
			System.setProperty("webdriver.gecko.driver", driverPath + "geckodriver_win32.exe");
			ffBin = new FirefoxBinary();
			ffBin.addCommandLineOptions("--headless");
			ffOpts = new FirefoxOptions();
			ffOpts.setBinary(ffBin);
			ffOpts.setLogLevel(FirefoxDriverLogLevel.INFO);
			driver = new FirefoxDriver(ffOpts);
			break;
		case "ff64":
			System.setProperty("webdriver.gecko.driver", driverPath + "geckodriver_win64.exe");
			driver = new FirefoxDriver();
			break;
		case "ff64-nohead":
			System.setProperty("webdriver.gecko.driver", driverPath + "geckodriver_win64.exe");
			ffBin = new FirefoxBinary();
			ffBin.addCommandLineOptions("--headless");
			ffOpts = new FirefoxOptions();
			ffOpts.setBinary(ffBin);
			ffOpts.setLogLevel(FirefoxDriverLogLevel.INFO);
			driver = new FirefoxDriver(ffOpts);
			break;
		case "edge":
			System.setProperty("webdriver.edge.driver", driverPath + "MicrosoftWebDriver.exe");
			driver = new EdgeDriver();
			break;
		case "ie32":
			System.setProperty("webdriver.ie.driver", driverPath + "IEDriverServer_win32.exe");
			driver = new InternetExplorerDriver();
			break;
		case "ie64":
			System.setProperty("webdriver.ie.driver", driverPath + "IEDriverServer_win64.exe");
			driver = new InternetExplorerDriver();
			break;
		default:
			logger.error("Invalid browser specified. Using default chrome-nohead.");
			setDefaultDriver(driverPath);
			break;
		}
	}

	@After
	public void tearDown() throws Throwable {
		logger.info("Cleaning up...");
		driver.quit();
	}

	public WebDriver getDriver() {
		return driver;
	}

	private void setDefaultDriver(String driverPath) {
		System.setProperty("webdriver.chrome.driver", driverPath + "chromedriver_win32.exe");
		ChromeOptions chromeOpts = new ChromeOptions();
		chromeOpts.addArguments("--headless");
		chromeOpts.addArguments("--window-size=" + LocalSystem.getNativeResolution());
		driver = new ChromeDriver(chromeOpts);
	}

}
