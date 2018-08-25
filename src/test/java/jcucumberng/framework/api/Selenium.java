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
import jcucumberng.framework.factory.ByFactory;
import jcucumberng.framework.strings.Messages;

/**
 * {@code Selenium} handles actions for interacting with web applications using
 * the Selenium WebDriver.
 * 
 * @author Kat Rollo <rollo.katherine@gmail.com>
 */
public final class Selenium {

	private final WebDriver driver;
	private final Scenario scenario;

	public Selenium(WebDriver driver, Scenario scenario) {
		this.driver = driver;
		this.scenario = scenario;
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
		return ByFactory.getInstance(key);
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
			by = this.by(keys[ctr]);
			bys[ctr] = by;
		}
		return bys;
	}

	/**
	 * Returns a visible web element. Uses explicit wait.
	 * 
	 * @param keys the key(s) from {@code ui-map.properties}
	 * @return WebElement - the web element found
	 * @throws IOException
	 */
	public WebElement getVisibleElement(String... keys) throws IOException {
		By[] bys = this.getBys(keys);
		int timeOut = Integer.parseInt(Config.framework("webdriver.wait"));
		WebElement element = this.wait(timeOut)
				.until(ExpectedConditions.visibilityOfElementLocated(new ByChained(bys)));
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
		By[] bys = this.getBys(keys);
		int timeOut = Integer.parseInt(Config.framework("webdriver.wait"));
		List<WebElement> elements = this.wait(timeOut)
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
		List<WebElement> elements = this.getVisibleElements(keys);
		List<Select> selectElements = new ArrayList<>();
		for (WebElement element : elements) {
			selectElements.add(new Select(element));
		}
		return selectElements;
	}

	/**
	 * Returns all text inside the body tag in HTML.
	 * 
	 * @return String - the text within HTML body tags
	 */
	public String getBodyText() {
		return this.driver.findElement(By.tagName("body")).getText();
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
		List<WebElement> elements = this.getVisibleElements(keys);
		return 0 < elements.size() ? true : false;
	}

	/**
	 * Clicks an element on the web page.
	 * 
	 * @param keys the key(s) from {@code ui-map.properties}
	 * @return WebElement - the clickable element
	 * @throws IOException
	 */
	public WebElement click(String... keys) throws IOException {
		WebElement element = this.getVisibleElement(keys);
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
		WebElement field = this.getVisibleElement(keys);
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
	 * Opens a new window by clicking an element and switches to that window.
	 * 
	 * @param keys the key(s) from {@code ui-map.properties}
	 * @return String - the handle of the parent window
	 * @throws IOException
	 */
	public String openWindowByElement(String... keys) throws IOException {
		String parentHandle = this.driver.getWindowHandle(); // Save parent window
		this.click(keys); // Open child window
		int timeOut = Integer.parseInt(Config.framework("webdriver.wait"));
		boolean isChildWindowOpen = this.wait(timeOut).until(ExpectedConditions.numberOfWindowsToBe(2));
		if (isChildWindowOpen) {
			Set<String> handles = this.driver.getWindowHandles();
			// Switch to child window
			for (String handle : handles) {
				if (StringUtils.equals(parentHandle, handle)) {
					this.driver.switchTo().window(handle);
					break;
				}
			}
			this.driver.manage().window().maximize();
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
		String parentHandle = this.driver.getWindowHandle();
		this.driver.get(url);
		int timeOut = Integer.parseInt(Config.framework("webdriver.wait"));
		boolean isChildWindowOpen = this.wait(timeOut).until(ExpectedConditions.numberOfWindowsToBe(2));
		if (isChildWindowOpen) {
			Set<String> handles = this.driver.getWindowHandles();
			for (String handle : handles) {
				if (StringUtils.equals(parentHandle, handle)) {
					this.driver.switchTo().window(handle);
					break;
				}
			}
			this.driver.manage().window().maximize();
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
		Set<String> handles = this.driver.getWindowHandles();
		String parentHandle = this.driver.getWindowHandle();
		if (1 < handles.size()) {
			for (String handle : handles) {
				this.driver.switchTo().window(handle);
				if (StringUtils.equalsIgnoreCase(windowTitle, this.driver.getTitle())) {
					break;
				}
			}
			this.driver.manage().window().maximize();
		}
		return parentHandle;
	}

	/**
	 * Scroll the screen left or right.
	 * 
	 * @param xPos negative value to scroll left, positive value to scroll right
	 */
	public void scrollHorizontal(int xPos) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) this.driver;
		jsExecutor.executeScript("scroll(" + xPos + ", 0);");
	}

	/**
	 * Scroll the screen up or down.
	 * 
	 * @param yPos positive value to scroll down, negative value to scroll up
	 */
	public void scrollVertical(int yPos) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) this.driver;
		jsExecutor.executeScript("scroll(0, " + yPos + ");");
	}

	/**
	 * Scroll to specific element on the web page.
	 * 
	 * @param keys the key(s) from {@code ui-map.properties}
	 * @throws IOException
	 */
	public void scrollToElement(String... keys) throws IOException {
		By[] bys = this.getBys(keys);
		WebElement element = this.driver.findElement(new ByChained(bys));
		JavascriptExecutor jsExecutor = (JavascriptExecutor) this.driver;
		jsExecutor.executeScript("arguments[0].scrollIntoView();", element);
	}

	/**
	 * Scroll to specific element on the web page.
	 * 
	 * @param element the element to scroll to
	 */
	public void scrollToElement(WebElement element) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) this.driver;
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
		File srcFile = ((TakesScreenshot) this.driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(srcFile, new File(screenshot));
	}

	/**
	 * Captures and embeds screenshot in generated HTML report. Reports can be found
	 * in {@code /target/}.
	 * 
	 * @param scenario the Scenario object
	 */
	public void embedScreenshot() {
		byte[] srcBytes = ((TakesScreenshot) this.driver).getScreenshotAs(OutputType.BYTES);
		this.scenario.embed(srcBytes, "image/png");
	}

	/**
	 * Returns the explicit wait object.
	 * 
	 * @param timeOut the wait time in seconds
	 * @return WebDriverWait - the wait object
	 */
	private WebDriverWait wait(int timeOut) {
		return new WebDriverWait(this.driver, timeOut);
	}

	public WebDriver getDriver() {
		return this.driver;
	}

	public Scenario getScenario() {
		return this.scenario;
	}

}
