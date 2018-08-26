package jcucumberng.framework.api;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
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

import cucumber.api.Scenario;
import jcucumberng.framework.exceptions.MissingArgumentsException;
import jcucumberng.framework.strings.Messages;
import jcucumberng.framework.utils.Config;

/**
 * {@code Selenium} handles actions for interacting with web applications using
 * the Selenium WebDriver.
 * 
 * @author Kat Rollo &lt;rollo.katherine@gmail.com&gt;
 */
public final class Selenium {

	private WebDriver driver = null;
	private Scenario scenario = null;
	private int timeOut = 0;

	public Selenium() {
		// Empty constructor
	}

	public Selenium(WebDriver driver, Scenario scenario) throws Throwable {
		this.driver = driver;
		this.scenario = scenario;
		this.timeOut = Integer.parseInt(Config.framework("webdriver.wait"));
	}

	/**
	 * Returns the {@code By} object based on the {@code method} and
	 * {@code selector} delimited by a colon ({@code :}) from
	 * {@code ui-map.properties}.<br>
	 * <br>
	 * Example:
	 * 
	 * <pre>
	 * {@code
	 * ui-map.properties:
	 * income.add=css:button[ng-click='addIncome();']
	 * 
	 * Where:
	 * method = css
	 * selector = button[ng-click='addIncome();']
	 * 
	 * Therefore:
	 * By = By.cssSelector()
	 * }
	 * </pre>
	 * 
	 * @param key the key from {@code ui-map.properties}
	 * @return By - the {@code By} object
	 * @throws IOException
	 */
	public By by(String key) throws IOException {
		return LocatorFactory.getInstance(key);
	}

	/**
	 * Returns arbitrary {@code String... keys} as {@code By} array.
	 * 
	 * @param keys the key(s) from {@code ui-map.properties}
	 * @return By[ ] - the By array
	 * @throws IOException
	 */
	public By[] getBys(String... keys) throws IOException {
		if (0 == keys.length) {
			throw new MissingArgumentsException(Messages.MISSING_ARGS);
		}
		By[] bys = new By[keys.length];
		By by = null;
		for (int ctr = 0; ctr < bys.length; ctr++) {
			by = by(keys[ctr]);
			bys[ctr] = by;
		}
		return bys;
	}

	/**
	 * Returns the explicit wait object.
	 * 
	 * @param timeOut the wait time in seconds
	 * @return WebDriverWait - the wait object
	 */
	public WebDriverWait explicitWait() {
		return new WebDriverWait(driver, timeOut);
	}

	/**
	 * Returns a visible web element. Uses explicit wait.
	 * 
	 * @param keys the key(s) from {@code ui-map.properties}
	 * @return WebElement - the web element found
	 * @throws IOException
	 */
	public WebElement getVisibleElement(String... keys) throws IOException {
		By[] bys = getBys(keys);
		WebElement element = explicitWait().until(ExpectedConditions.visibilityOfElementLocated(new ByChained(bys)));
		return element;
	}

	/**
	 * Returns visible web elements. Uses explicit wait.
	 * 
	 * @param keys the key(s) from {@code ui-map.properties}
	 * @return List - the List of web elements found
	 * @throws IOException
	 */
	public List<WebElement> getVisibleElements(String... keys) throws IOException {
		By[] bys = getBys(keys);
		List<WebElement> elements = explicitWait()
				.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(new ByChained(bys)));
		return elements;
	}

	/**
	 * Returns a List of all Select elements.
	 * 
	 * @param keys the key(s) from {@code ui-map.properties}
	 * @return List - the List of Select elements
	 * @throws IOException
	 */
	public List<Select> getSelectElements(String... keys) throws IOException {
		List<Select> selects = new ArrayList<>();
		for (WebElement element : getVisibleElements(keys)) {
			selects.add(new Select(element));
		}
		return selects;
	}

	/**
	 * Returns all text inside the body tag in HTML.
	 * 
	 * @return String - the text within HTML body tags
	 */
	public String getBodyText() {
		return driver.findElement(By.tagName("body")).getText();
	}

	/**
	 * Checks if the element is found on the web page.
	 * 
	 * @param keys the key(s) from {@code ui-map.properties}
	 * @return {@code true} - if at least one matching element is found on the web
	 *         page
	 * @throws IOException
	 */
	public boolean isElementPresent(String... keys) throws IOException {
		return 0 < getVisibleElements(keys).size() ? true : false;
	}

	/**
	 * Clicks an element on the web page.
	 * 
	 * @param keys the key(s) from {@code ui-map.properties}
	 * @return WebElement - the clickable element
	 * @throws IOException
	 */
	public WebElement click(String... keys) throws IOException {
		WebElement element = getVisibleElement(keys);
		element.click();
		return element;
	}

	/**
	 * Enters text into a textfield or textarea.
	 * 
	 * @param text the text to be entered
	 * @param keys the key(s) from {@code ui-map.properties}
	 * @return WebElement - the textfield or textarea element
	 * @throws IOException
	 */
	public WebElement type(String text, String... keys) throws IOException {
		WebElement field = getVisibleElement(keys);
		field.clear();
		field.sendKeys(text);
		return field;
	}

	/**
	 * Enters text into a textfield or textarea.
	 * 
	 * @param text  the text to be entered
	 * @param field the textfield or textarea element
	 */
	public void type(String text, WebElement field) {
		field.clear();
		field.sendKeys(text);
	}

	/**
	 * Selects dropdown value by visible text.
	 * 
	 * @param text the visible text from the dropdown options
	 * @param keys the key(s) from {@code ui-map.properties}
	 * @return Select - the Select object
	 * @throws IOException
	 */
	public Select selectByVisibleText(String text, String... keys) throws IOException {
		Select select = new Select(getVisibleElement(keys));
		select.selectByVisibleText(text);
		return select;
	}

	/**
	 * Opens a new window by clicking an element and switches to that window.
	 * 
	 * @param keys the key(s) from {@code ui-map.properties}
	 * @return String - the handle of the parent window
	 * @throws IOException
	 */
	public String openWindowByElement(String... keys) throws IOException {
		String parentHandle = driver.getWindowHandle(); // Save parent window
		click(keys); // Open child window
		boolean isChildWindowOpen = explicitWait().until(ExpectedConditions.numberOfWindowsToBe(2));
		if (isChildWindowOpen) {
			Set<String> handles = driver.getWindowHandles();
			// Switch to child window
			for (String handle : handles) {
				if (StringUtils.equals(parentHandle, handle)) {
					driver.switchTo().window(handle);
					break;
				}
			}
			driver.manage().window().maximize();
		}
		return parentHandle; // Returns parent window if need to switch back
	}

	/**
	 * Opens a new window by clicking a link and switches to that window.
	 * 
	 * @param url the link to the child window
	 * @return String - the handle of the parent window
	 */
	public String openWindowByLink(String url) throws IOException {
		String parentHandle = driver.getWindowHandle();
		driver.get(url);
		boolean isChildWindowOpen = explicitWait().until(ExpectedConditions.numberOfWindowsToBe(2));
		if (isChildWindowOpen) {
			Set<String> handles = driver.getWindowHandles();
			for (String handle : handles) {
				if (StringUtils.equals(parentHandle, handle)) {
					driver.switchTo().window(handle);
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
	 * @param windowTitle the title of the window
	 * @return String - the handle of the parent window
	 */
	public String switchToWindowByTitle(String windowTitle) {
		Set<String> handles = driver.getWindowHandles();
		String parentHandle = driver.getWindowHandle();
		if (1 < handles.size()) {
			for (String handle : handles) {
				driver.switchTo().window(handle);
				if (StringUtils.equalsIgnoreCase(windowTitle, driver.getTitle())) {
					break;
				}
			}
			driver.manage().window().maximize();
		}
		return parentHandle;
	}

	/**
	 * Scroll the screen left or right.
	 * 
	 * @param xPos negative value to scroll left, positive value to scroll right
	 */
	public void scrollHorizontal(int xPos) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("scroll(" + xPos + ", 0);");
	}

	/**
	 * Scroll the screen up or down.
	 * 
	 * @param yPos positive value to scroll down, negative value to scroll up
	 */
	public void scrollVertical(int yPos) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("scroll(0, " + yPos + ");");
	}

	/**
	 * Scroll to specific element on the web page.
	 * 
	 * @param keys the key(s) from {@code ui-map.properties}
	 * @throws IOException
	 */
	public void scrollToElement(String... keys) throws IOException {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].scrollIntoView();", getVisibleElement(keys));
	}

	/**
	 * Scroll to specific element on the web page.
	 * 
	 * @param element the element to scroll to
	 */
	public void scrollToElement(WebElement element) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].scrollIntoView();", element);
	}

	/**
	 * Captures and saves screenshot in PNG format. Images are stored in
	 * {@code /target/cucumber-sshots/}.
	 * 
	 * @throws IOException
	 */
	public void captureScreenshot() throws IOException {
		StringBuilder builder = new StringBuilder();
		builder.append(StringUtils.replace(System.getProperty("user.dir"), "\\", "/"));
		builder.append("/target/cucumber-sshots/sshot_");
		builder.append(System.currentTimeMillis());
		builder.append(".png");
		String screenshot = builder.toString();
		File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(srcFile, new File(screenshot));
	}

	/**
	 * Captures and embeds screenshot in generated HTML report. Reports can be found
	 * in {@code /target/}.
	 * 
	 * @param scenario the Scenario object
	 */
	public void embedScreenshot() {
		byte[] srcBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
		scenario.embed(srcBytes, "image/png");
	}

	public WebDriver getDriver() {
		return driver;
	}

	public Scenario getScenario() {
		return scenario;
	}

}
