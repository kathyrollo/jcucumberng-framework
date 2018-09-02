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

/**
 * {@code BrowserFactory} handles actions for instantiating the Selenium
 * WebDriver.
 * 
 * @author Kat Rollo &lt;rollo.katherine@gmail.com&gt;
 */
public final class BrowserFactory {

	public enum Browser {
		CHROME32, CHROME32_NOHEAD, FF32, FF32_NOHEAD, FF64, FF64_NOHEAD, EDGE, IE32, IE64
	}

	private static final String CHROME_DRIVER = "webdriver.chrome.driver";
	private static final String GECKO_DRIVER = "webdriver.gecko.driver";
	private static final String EDGE_DRIVER = "webdriver.edge.driver";
	private static final String IE_DRIVER = "webdriver.ie.driver";

	private static final String CHROME32_BIN = "chromedriver_win32.exe";
	private static final String FF32_BIN = "geckodriver_win32.exe";
	private static final String FF64_BIN = "geckodriver_win64.exe";
	private static final String EDGE_BIN = "MicrosoftWebDriver.exe";
	private static final String IE32_BIN = "IEDriverServer_win32.exe";
	private static final String IE64_BIN = "IEDriverServer_win64.exe";

	private BrowserFactory() {
		// Prevent instantiation
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

		StringBuilder builder = new StringBuilder();
		builder.append(StringUtils.replace(System.getProperty("user.dir"), "\\", "/"));
		builder.append("/src/main/resources/drivers/");
		String driverPath = builder.toString();

		try {
			Browser browser = Browser.valueOf(StringUtils.upperCase(browserConfig));
			switch (browser) {
			case CHROME32:
				System.setProperty(CHROME_DRIVER, driverPath + CHROME32_BIN);
				driver = new ChromeDriver();
				break;
			case CHROME32_NOHEAD:
				System.setProperty(CHROME_DRIVER, driverPath + CHROME32_BIN);
				ChromeOptions chromeOpts = new ChromeOptions();
				chromeOpts.addArguments("--headless");
				driver = new ChromeDriver(chromeOpts);
				break;
			case FF32:
				System.setProperty(GECKO_DRIVER, driverPath + FF32_BIN);
				driver = new FirefoxDriver();
				break;
			case FF32_NOHEAD:
				driver = BrowserFactory.initFirefoxNoHead(driverPath, FF32_BIN);
				break;
			case FF64:
				System.setProperty(GECKO_DRIVER, driverPath + FF64_BIN);
				driver = new FirefoxDriver();
				break;
			case FF64_NOHEAD:
				driver = BrowserFactory.initFirefoxNoHead(driverPath, FF64_BIN);
				break;
			case EDGE:
				System.setProperty(EDGE_DRIVER, driverPath + EDGE_BIN);
				driver = new EdgeDriver();
				break;
			case IE32:
				System.setProperty(IE_DRIVER, driverPath + IE32_BIN);
				driver = new InternetExplorerDriver();
				break;
			case IE64:
				System.setProperty(IE_DRIVER, driverPath + IE64_BIN);
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

	private static WebDriver initFirefoxNoHead(String driverPath, String driverBinary) {
		WebDriver driver = null;
		System.setProperty(GECKO_DRIVER, driverPath + driverBinary);
		FirefoxBinary ffBin = new FirefoxBinary();
		ffBin.addCommandLineOptions("--headless");
		FirefoxOptions ffOpts = new FirefoxOptions();
		ffOpts.setBinary(ffBin);
		ffOpts.setLogLevel(FirefoxDriverLogLevel.WARN);
		driver = new FirefoxDriver(ffOpts);
		return driver;
	}

}
