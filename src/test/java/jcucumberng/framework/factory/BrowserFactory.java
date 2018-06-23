package jcucumberng.framework.factory;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;

public final class BrowserFactory {
	private static Map<String, WebDriver> drivers = new HashMap<>();

	public static WebDriver getBrowser(String browser) {
		WebDriver driver = null;

		StringBuilder builder = new StringBuilder();
		builder.append(System.getProperty("user.dir").replace("\\", "/"));
		builder.append("/src/test/resources/webdrivers/");
		String driverPath = builder.toString().trim();

		ChromeOptions chromeOpts = null;
		FirefoxOptions ffOpts = null;

		switch (browser.toUpperCase()) {
		case "CHROME32":
			System.setProperty("webdriver.chrome.driver", driverPath + "chromedriver_win32.exe");
			driver = new ChromeDriver();
			break;
		case "CHROME32_NOHEAD":
			chromeOpts = BrowserFactory.setChromeNoHead(driverPath, "chromedriver_win32.exe");
			driver = new ChromeDriver(chromeOpts);
			break;
		case "FF32":
			System.setProperty("webdriver.gecko.driver", driverPath + "geckodriver_win32.exe");
			driver = new FirefoxDriver();
			break;
		case "FF32_NOHEAD":
			ffOpts = BrowserFactory.setFirefoxNoHead(driverPath, "geckodriver_win32.exe");
			driver = new FirefoxDriver(ffOpts);
			break;
		case "FF64":
			System.setProperty("webdriver.gecko.driver", driverPath + "geckodriver_win64.exe");
			driver = new FirefoxDriver();
			break;
		case "FF64_NOHEAD":
			ffOpts = BrowserFactory.setFirefoxNoHead(driverPath, "geckodriver_win64.exe");
			driver = new FirefoxDriver(ffOpts);
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
			return null;
		}

		return driver;
	}

	private static ChromeOptions setChromeNoHead(String driverPath, String driverBinary) {
		System.setProperty("webdriver.chrome.driver", driverPath + driverBinary);
		ChromeOptions chromeOpts = new ChromeOptions();
		chromeOpts.addArguments("--headless");
		return chromeOpts;
	}

	private static FirefoxOptions setFirefoxNoHead(String driverPath, String driverBinary) {
		System.setProperty("webdriver.gecko.driver", driverPath + driverBinary);
		FirefoxBinary ffBin = new FirefoxBinary();
		ffBin.addCommandLineOptions("--headless");
		FirefoxOptions ffOpts = new FirefoxOptions();
		ffOpts.setBinary(ffBin);
		ffOpts.setLogLevel(FirefoxDriverLogLevel.INFO);
		return ffOpts;
	}

}
