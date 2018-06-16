package jcucumberng.framework.api;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ByChained;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.paulhammant.ngwebdriver.ByAngular;

import cucumber.api.Scenario;
import jcucumberng.framework.constants.ExceptionMessages;
import jcucumberng.framework.exceptions.MissingArgumentsException;

/**
 * {@code Selenium} handles actions for interacting with web applications using
 * the Selenium WebDriver.
 * 
 * @author Kat Rollo <rollo.katherine@gmail.com>
 */
public final class Selenium {
	private Selenium() {
		// Prevent instantiation
	}

	/**
	 * Returns the By object based on the value of the key from the
	 * {@code ui-map.properties}.<br>
	 * <br>
	 * Example:<br>
	 * <br>
	 * {@code key = expense.name.txt}<br>
	 * <br>
	 * {@code value = by-model:expense.name}<br>
	 * <br>
	 * {@code by = ByAngular.model()}<br>
	 * <br>
	 * The colon ({@code :}) is the delimiter between the by-type (e.g.
	 * {@code by-model}) and the locator (e.g. {@code expense.name}) or substring
	 * after the colon.
	 * 
	 * @param key
	 *            the key from the {@code ui-map.properties}
	 * @return By - the By object
	 * @throws IOException
	 */
	public static By by(String key) throws IOException {
		String value = PropsLoader.readUiMap(key);
		String locator = value.substring(value.lastIndexOf(":") + 1);
		By by = null;
		// TODO Add By-types as needed
		if (value.contains("by-classname")) {
			by = By.className(locator);
		} else if (value.contains("by-css")) {
			by = By.cssSelector(locator);
		} else if (value.contains("by-model")) {
			by = ByAngular.model(locator);
		} else if (value.contains("by-binding")) {
			by = ByAngular.binding(locator);
		}
		return by;
	}

	/**
	 * Clicks an element on the web page.
	 * 
	 * @param driver
	 *            the Selenium WebDriver
	 * @param keys
	 *            the key(s) from the {@code ui-map.properties}
	 * @return WebElement - the clickable element
	 * @throws IOException
	 */
	public static WebElement clickElement(WebDriver driver, String... keys) throws IOException {
		By[] bys = Selenium.getBys(keys);
		WebElement clickableElement = driver.findElement(new ByChained(bys));
		clickableElement.click();
		return clickableElement;
	}

	/**
	 * Enters text into a textfield or textarea.
	 * 
	 * @param driver
	 *            the Selenium WebDriver
	 * @param text
	 *            the text to be entered
	 * @param keys
	 *            the key(s) from the {@code ui-map.properties}
	 * @return WebElement - the textfield or textarea element
	 * @throws IOException
	 */
	public static WebElement enterText(WebDriver driver, String text, String... keys) throws IOException {
		By[] bys = Selenium.getBys(keys);
		WebElement field = driver.findElement(new ByChained(bys));
		field.clear();
		field.sendKeys(text);
		return field;
	}

	/**
	 * Enters text into a textfield or textarea.
	 * 
	 * @param driver
	 *            the Selenium WebDriver
	 * @param text
	 *            the text to be entered
	 * @param field
	 *            the textfield or textarea element
	 * @throws IOException
	 */
	public static void enterText(WebDriver driver, String text, WebElement field) throws IOException {
		field.clear();
		field.sendKeys(text);
	}

	/**
	 * Returns a List of all Select elements.
	 * 
	 * @param driver
	 *            the Selenium WebDriver
	 * @param keys
	 *            the key(s) from the {@code ui-map.properties}
	 * @return List - the List of Select elements
	 * @throws IOException
	 */
	public static List<Select> getSelectElements(WebDriver driver, String... keys) throws IOException {
		By[] bys = Selenium.getBys(keys);
		List<WebElement> elements = driver.findElements(new ByChained(bys));
		List<Select> selectElements = new ArrayList<>();
		for (WebElement element : elements) {
			selectElements.add(new Select(element));
		}
		return selectElements;
	}

	/**
	 * Selects value from a dropdown list by visible text.
	 * 
	 * @param driver
	 *            the Selenium WebDriver
	 * @param text
	 *            the text to be selected from the dropdown menu
	 * @param select
	 *            the Select element of the dropdown menu
	 */
	public static void selectByText(WebDriver driver, String text, Select select) {
		select.selectByVisibleText(text);
	}

	/**
	 * Returns all text inside the body tag in HTML.
	 * 
	 * @param driver
	 *            the Selenium WebDriver
	 * @return String - the text within HTML body tags
	 */
	public static String getBodyText(WebDriver driver) {
		return driver.findElement(By.tagName("body")).getText();
	}

	/**
	 * Opens a new window by clicking a link or an element and switches to that
	 * window.
	 * 
	 * @param driver
	 *            the Selenium WebDriver
	 * @param args
	 *            the link to the child window or the key(s) from the
	 *            {@code ui-map.properties}
	 * @return String - the handle of the parent window
	 */
	public static String openNewWindow(WebDriver driver, String... args) throws IOException {
		String parentHandle = driver.getWindowHandle(); // Save parent window
		if (0 == args.length) {
			throw new MissingArgumentsException(ExceptionMessages.MISSING_ARGS);
		}
		// Open child window
		if (args[0].matches("http[s]?://.*")) { // Check if valid URL
			driver.get(args[0]);
		} else {
			Selenium.clickElement(driver, args);
		}
		WebDriverWait wait = new WebDriverWait(driver, 10); // Timeout in 10s
		boolean isChildWindowOpen = wait.until(ExpectedConditions.numberOfWindowsToBe(2));
		if (isChildWindowOpen) {
			Set<String> handles = driver.getWindowHandles();
			// Switch to child window
			for (String handle : handles) {
				driver.switchTo().window(handle);
				if (!parentHandle.equals(handle)) {
					break;
				}
			}
			driver.manage().window().maximize();
		}
		return parentHandle; // Returns parent window if need to switch back
	}

	/**
	 * Switches to an existing open window by window title.
	 * 
	 * @param driver
	 *            the Selenium WebDriver
	 * @param windowTitle
	 *            the title of the window
	 * @return String - the handle of the parent window
	 */
	public static String switchToWindowByTitle(WebDriver driver, String windowTitle) {
		Set<String> handles = driver.getWindowHandles();
		String parentHandle = driver.getWindowHandle();
		if (1 < handles.size()) {
			for (String handle : handles) {
				driver.switchTo().window(handle);
				if (windowTitle.equalsIgnoreCase(driver.getTitle())) {
					break;
				}
			}
			driver.manage().window().maximize();
		}
		return parentHandle;
	}

	/**
	 * Scroll the screen vertically.
	 * 
	 * @param driver
	 *            the Selenium WebDriver
	 * @param yPosition
	 *            positive value to scroll down, negative value to scroll up
	 */
	public static void scrollVertical(WebDriver driver, int yPosition) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("scroll(0, " + yPosition + ");");
	}

	// TODO Implement scrollHorizontal()

	/**
	 * Scroll to specific element on web page.
	 * 
	 * @param driver
	 *            the Selenium WebDriver
	 * @param element
	 *            the element to scroll to
	 */
	public static void scrollToElement(WebDriver driver, WebElement element) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].scrollIntoView();", element);
	}

	/**
	 * Captures the current screen. Stores images in
	 * {@code /target/cucumber-screenshots/} in PNG format.
	 * 
	 * @param driver
	 *            the Selenium WebDriver
	 * @throws IOException
	 */
	public static void captureScreenshot(WebDriver driver) throws IOException {
		StringBuilder builder = new StringBuilder();
		builder.append(System.getProperty("user.dir").replace("\\", "/"));
		builder.append("/target/cucumber-screenshots/sshot_");
		builder.append(System.currentTimeMillis());
		builder.append(".png");
		String screenshot = builder.toString();

		File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(srcFile, new File(screenshot));
	}

	/**
	 * Captures and embeds screenshot in generated HTML report.
	 * 
	 * @param scenario
	 *            the Scenario object
	 * @param driver
	 *            the Selenium WebDriver
	 */
	public static void embedScreenshot(WebDriver driver, Scenario scenario) {
		byte[] srcBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
		scenario.embed(srcBytes, "image/png");
	}

	/**
	 * Returns arbitrary {@code String... keys} as By array.
	 * 
	 * @param keys
	 *            the key(s) from the {@code ui-map.properties}
	 * @return By[ ] - the By array
	 * @throws IOException
	 */
	private static By[] getBys(String... keys) throws IOException {
		if (0 == keys.length) {
			throw new MissingArgumentsException(ExceptionMessages.MISSING_ARGS);
		}
		By[] bys = new By[keys.length];
		By by = null;
		for (int ctr = 0; ctr < bys.length; ctr++) {
			by = Selenium.by(keys[ctr]);
			bys[ctr] = by;
		}
		return bys;
	}

}
