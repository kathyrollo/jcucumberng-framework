package jcucumberng.api.browser;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * {@code BrowserFactory} handles actions for instantiating the Selenium
 * WebDriver.
 * 
 * @author Kat Rollo {@literal <rollo.katherine@gmail.com>}
 */
public final class BrowserFactory {

	public enum Browser {
		CHROME32, CHROME32_NOHEAD, FF32, FF32_NOHEAD, FF64, FF64_NOHEAD, EDGE, IE32, IE64
	}

	private BrowserFactory() {
		throw new IllegalStateException("Class must not be instantiated.");
	}

	/**
	 * Gets the Selenium WebDriver instance.
	 * 
	 * @param browserConfig the {@code browser} specified in
	 *                      {@code framework.properties}
	 * @return WebDriver - the Selenium WebDriver
	 */
	public static WebDriver getInstance(String browserConfig) {
		WebDriver driver = null;

		try {
			Browser browser = Browser.valueOf(StringUtils.upperCase(browserConfig));
			switch (browser) {
			case CHROME32:
				WebDriverManager.chromedriver().arch32().setup();
				driver = new ChromeDriver();
				break;
			case CHROME32_NOHEAD:
				WebDriverManager.chromedriver().arch32().setup();
				ChromeOptions chromeOpts = new ChromeOptions();
				chromeOpts.addArguments("--headless");
				driver = new ChromeDriver(chromeOpts);
				break;
			case FF32:
				WebDriverManager.firefoxdriver().arch32().setup();
				driver = new FirefoxDriver();
				break;
			case FF32_NOHEAD:
				driver = BrowserFactory.initFirefoxNoHead(32);
				break;
			case FF64:
				WebDriverManager.firefoxdriver().arch64().setup();
				driver = new FirefoxDriver();
				break;
			case FF64_NOHEAD:
				driver = BrowserFactory.initFirefoxNoHead(64);
				break;
			case EDGE:
				WebDriverManager.edgedriver().setup();
				driver = new EdgeDriver();
				break;
			case IE32:
				WebDriverManager.iedriver().arch32().setup();
				driver = new InternetExplorerDriver();
				break;
			case IE64:
				WebDriverManager.iedriver().arch64().setup();
				driver = new InternetExplorerDriver();
				break;
			default:
				// Handled in try-catch
				break;
			}
		} catch (IllegalArgumentException | NullPointerException e) {
			if (StringUtils.isBlank(browserConfig)) {
				browserConfig = "BLANK";
			}
			throw new UnsupportedBrowserException(
					"Unsupported browser specified in framework.properties: " + browserConfig);
		}

		return driver;
	}

	private static WebDriver initFirefoxNoHead(int arch) {
		if (32 == arch) {
			WebDriverManager.firefoxdriver().arch32().setup();
		}
		if (64 == arch) {
			WebDriverManager.firefoxdriver().arch64().setup();
		}
		FirefoxBinary ffBin = new FirefoxBinary();
		ffBin.addCommandLineOptions("--headless");
		FirefoxOptions ffOpts = new FirefoxOptions();
		ffOpts.setBinary(ffBin);
		ffOpts.setLogLevel(FirefoxDriverLogLevel.WARN);
		return new FirefoxDriver(ffOpts);
	}

}
