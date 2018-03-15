package jcucumberng.api;

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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * This class handles actions for interacting with web applications using the
 * Selenium WebDriver.
 */
public final class Selenium {

	private Selenium() {
		// Prevent instantiation
	}

	/**
	 * Opens a new window and switches to that window.
	 * 
	 * @param driver
	 *            the Selenium WebDriver
	 * @param clickableLocator
	 *            the locator of the clickable element that opens the child window
	 * @return String - the handle of the parent window before opening the child
	 *         window
	 * 
	 * @author Katherine Rollo (rollo.katherine@gmail.com)
	 */
	public static String openWindow(WebDriver driver, By clickableLocator) {
		String parentHandle = driver.getWindowHandle(); // Save parent window
		WebElement clickableElement = driver.findElement(clickableLocator);
		clickableElement.click(); // Open child window
		WebDriverWait wait = new WebDriverWait(driver, 10);
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
	 * Opens a new window and switches to that window.
	 * 
	 * @param driver
	 *            the Selenium WebDriver
	 * @param url
	 *            the String URL that opens the child window
	 * @return String - the handle of the parent window before opening the child
	 *         window
	 * 
	 * @author Katherine Rollo (rollo.katherine@gmail.com)
	 */
	public static String openWindow(WebDriver driver, String url) {
		String parentHandle = driver.getWindowHandle();
		driver.get(url);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		boolean isChildWindowOpen = wait.until(ExpectedConditions.numberOfWindowsToBe(2));
		if (isChildWindowOpen) {
			Set<String> handles = driver.getWindowHandles();
			for (String handle : handles) {
				driver.switchTo().window(handle);
				if (!parentHandle.equals(handle)) {
					break;
				}
			}
			driver.manage().window().maximize();
		}
		return parentHandle;
	}

	/**
	 * Switches to an existing open window by window title.
	 * 
	 * @param driver
	 *            the Selenium WebDriver
	 * @param windowTitle
	 *            the title of the window
	 * @return String - the handle of the parent window before opening the child
	 *         window
	 * 
	 * @author Katherine Rollo (rollo.katherine@gmail.com)
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
	 * Returns a List of all Select elements.
	 * 
	 * @param driver
	 *            the Selenium WebDriver
	 * @param selectLocator
	 *            the locator of the Select elements
	 * @return List - the List of Select elements
	 * 
	 * @author Katherine Rollo (rollo.katherine@gmail.com)
	 */
	public static List<Select> getSelectElements(WebDriver driver, By selectLocator) {
		List<WebElement> elements = driver.findElements(selectLocator);
		List<Select> selectElements = new ArrayList<>();
		for (WebElement element : elements) {
			selectElements.add(new Select(element));
		}
		return selectElements;
	}

	/**
	 * Returns a List of all Select elements.
	 * 
	 * @param driver
	 *            the Selenium WebDriver
	 * @param webElements
	 *            the List of Select web elements
	 * @return List - the List of Select elements
	 * 
	 * @author Katherine Rollo (rollo.katherine@gmail.com)
	 */
	public static List<Select> getSelectElements(WebDriver driver, List<WebElement> webElements) {
		List<Select> selectElements = new ArrayList<>();
		for (WebElement element : webElements) {
			selectElements.add(new Select(element));
		}
		return selectElements;
	}

	/**
	 * Returns all text inside the body tag in HTML.
	 * 
	 * @param driver
	 *            the Selenium WebDriver
	 * @return String - the text within HTML body tags
	 * 
	 * @author Katherine Rollo (rollo.katherine@gmail.com)
	 */
	public static String getBodyText(WebDriver driver) {
		return driver.findElement(By.tagName("body")).getText();
	}

	/**
	 * Scroll the screen vertically.
	 * 
	 * @param driver
	 *            the Selenium WebDriver
	 * @param yPosition
	 *            positive value to scroll down, negative value to scroll up
	 * 
	 * @author Katherine Rollo (rollo.katherine@gmail.com)
	 */
	public static void scrollVertical(WebDriver driver, int yPosition) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("scroll(0, " + yPosition + "500);");
	}

	/**
	 * Enters text into a textfield or textarea.
	 * 
	 * @param driver
	 *            the Selenium WebDriver
	 * @param fieldLocator
	 *            the locator of the textfield or textarea
	 * @param text
	 *            the text to be entered
	 * 
	 * @author Katherine Rollo (rollo.katherine@gmail.com)
	 */
	public static void enterText(WebDriver driver, By fieldLocator, String text) {
		WebElement field = driver.findElement(fieldLocator);
		field.clear();
		field.sendKeys(text);
	}

	/**
	 * Enters text into a textfield or textarea.
	 * 
	 * @param driver
	 *            the Selenium WebDriver
	 * @param field
	 *            the WebElement of the textfield or textarea
	 * @param text
	 *            the text to be entered
	 * 
	 * @author Katherine Rollo (rollo.katherine@gmail.com)
	 */
	public static void enterText(WebDriver driver, WebElement field, String text) {
		field.clear();
		field.sendKeys(text);
	}

	/**
	 * Selects value from a dropdown list by visible text.
	 * 
	 * @param driver
	 *            the Selenium WebDriver
	 * @param selectLocator
	 *            the locator of the dropdown menu
	 * @param text
	 *            the text to be selected from the dropdown menu
	 * 
	 * @author Katherine Rollo (rollo.katherine@gmail.com)
	 */
	public static void selectFromDropMenuByText(WebDriver driver, By selectLocator, String text) {
		Select select = new Select(driver.findElement(selectLocator));
		select.selectByVisibleText(text);
	}

	/**
	 * Selects value from a dropdown list by visible text.
	 * 
	 * @param driver
	 *            the Selenium WebDriver
	 * @param select
	 *            the Select element of the dropdown menu
	 * @param text
	 *            the text to be selected from the dropdown menu
	 * 
	 * @author Katherine Rollo (rollo.katherine@gmail.com)
	 */
	public static void selectFromDropMenuByText(WebDriver driver, Select select, String text) {
		select.selectByVisibleText(text);
	}

	/**
	 * Clicks an element in the web page.
	 * 
	 * @param driver
	 *            the Selenium WebDriver
	 * @param clickableLocator
	 *            the locator of the clickable element
	 * 
	 * @author Katherine Rollo (rollo.katherine@gmail.com)
	 */
	public static void clickElement(WebDriver driver, By clickableLocator) {
		WebElement clickableElement = driver.findElement(clickableLocator);
		clickableElement.click();
	}

	/**
	 * Clicks an element in the web page.
	 * 
	 * @param driver
	 *            the Selenium WebDriver
	 * @param clickableElement
	 *            the WebElement of the clickable item
	 * 
	 * @author Katherine Rollo (rollo.katherine@gmail.com)
	 */
	public static void clickElement(WebDriver driver, WebElement clickableElement) {
		clickableElement.click();
	}

	/**
	 * Clicks a child element of a parent element.
	 * 
	 * @param driver
	 *            the Selenium WebDriver
	 * @param parentLocator
	 *            the locator of the parent element
	 * @param childLocator
	 *            the locator of the child element
	 * 
	 * @author Katherine Rollo (rollo.katherine@gmail.com)
	 */
	public static void clickChildElement(WebDriver driver, By parentLocator, By childLocator) {
		WebElement parentElement = driver.findElement(parentLocator);
		WebElement childElement = parentElement.findElement(childLocator);
		childElement.click();
	}

	/**
	 * Clicks a child element of a parent element.
	 * 
	 * @param driver
	 *            the Selenium WebDriver
	 * @param parentElement
	 *            the WebElement of the parent
	 * @param childLocator
	 *            the locator of the child element
	 * 
	 * @author Katherine Rollo (rollo.katherine@gmail.com)
	 */
	public static void clickChildElement(WebDriver driver, WebElement parentElement, By childLocator) {
		WebElement childElement = parentElement.findElement(childLocator);
		childElement.click();
	}

	/**
	 * Captures the current screen. Stores images in /target/cucumber-screenshots/
	 * in PNG format.
	 * 
	 * @param driver
	 *            the Selenium WebDriver
	 * @throws IOException
	 * 
	 * @author Katherine Rollo (rollo.katherine@gmail.com)
	 */
	public static void captureScreen(WebDriver driver) throws IOException {
		StringBuilder builder = new StringBuilder();
		builder.append(System.getProperty("user.dir").replace("\\", "/"));
		builder.append("/target/cucumber-screenshots/sshot_");
		builder.append(System.currentTimeMillis());
		builder.append(".png");
		String screenshot = builder.toString();

		File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(srcFile, new File(screenshot));
	}

}
