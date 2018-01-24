package stepdefs;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import utils.ConfigUtil;

public class ServiceHook {
	private WebDriver driver = null;

	@Before
	public void setUp() throws Throwable {
		StringBuilder builder = new StringBuilder();
		builder.append(System.getProperty("user.dir").replace("\\", "/"));
		builder.append("/src/test/resources/webdrivers/");
		String driverPath = builder.toString().trim();

		String browser = ConfigUtil.readKey("browser").toLowerCase();
		switch (browser) {
		case "chrome":
			System.setProperty("webdriver.chrome.driver", driverPath + "chromedriver_win32.exe");
			driver = new ChromeDriver();
			break;
		case "ff-32":
			System.setProperty("webdriver.gecko.driver", driverPath + "geckodriver_win32.exe");
			driver = new FirefoxDriver();
			break;
		case "ff-64":
			System.setProperty("webdriver.gecko.driver", driverPath + "geckodriver_win64.exe");
			driver = new FirefoxDriver();
			break;
		case "edge":
			System.setProperty("webdriver.edge.driver", driverPath + "MicrosoftWebDriver.exe");
			driver = new EdgeDriver();
			break;
		case "ie-32":
			System.setProperty("webdriver.ie.driver", driverPath + "IEDriverServer_win32.exe");
			driver = new InternetExplorerDriver();
			break;
		case "ie-64":
			System.setProperty("webdriver.ie.driver", driverPath + "IEDriverServer_win64.exe");
			driver = new InternetExplorerDriver();
			break;
		default:
			System.setProperty("webdriver.chrome.driver", driverPath + "chromedriver_win32.exe");
			driver = new ChromeDriver();
			break;
		}
	}

	@After
	public void tearDown() throws Throwable {
		driver.quit();
	}

	public WebDriver getDriver() {
		return driver;
	}

}
