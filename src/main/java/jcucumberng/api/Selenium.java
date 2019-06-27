package jcucumberng.api;

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

import com.paulhammant.ngwebdriver.NgWebDriver;

import cucumber.api.Scenario;

/**
 * {@code Selenium} handles actions for interacting with web applications using
 * the Selenium WebDriver.
 * 
 * @author Kat Rollo {@literal <rollo.katherine@gmail.com>}
 */
public final class Selenium {

	private int timeOut = 0;
	private WebDriver driver = null;
	private NgWebDriver ngWebDriver = null;
	private Scenario scenario = null;

	public Selenium() {
		// Empty constructor
	}

	public Selenium(WebDriver driver, Scenario scenario) throws Throwable {
		this.timeOut = Integer.parseInt(Configuration.framework("explicit.timeout"));
		this.driver = driver;
		this.ngWebDriver = new NgWebDriver((JavascriptExecutor) driver);
		this.scenario = scenario;
	}

	/**
	 * Returns the {@code By} object based on the {@code locator} and
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
	 * locator = css
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
		return Locator.getInstance(key);
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
			throw new NullPointerException(
					"No arguments found for arbitrary parameters. Length must not be equal to 0.");
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
	 * @return WebElement - the visible web element
	 * @throws IOException
	 */
	public WebElement getVisibleElement(String... keys) throws IOException {
		return explicitWait().until(ExpectedConditions.visibilityOfElementLocated(new ByChained(getBys(keys))));
	}

	/**
	 * Returns visible web elements. Uses explicit wait.
	 * 
	 * @param keys the key(s) from {@code ui-map.properties}
	 * @return List - the List of visible web elements
	 * @throws IOException
	 */
	public List<WebElement> getVisibleElements(String... keys) throws IOException {
		return explicitWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(new ByChained(getBys(keys))));
	}

	/**
	 * Returns a clickable element. Uses explicit wait.
	 * 
	 * @param keys the key(s) from {@code ui-map.properties}
	 * @return WebElement - the clickable web element
	 * @throws IOException
	 */
	public WebElement getClickableElement(String... keys) throws IOException {
		return explicitWait().until(ExpectedConditions.elementToBeClickable(new ByChained(getBys(keys))));
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
	 * @return boolean - {@code true}, if at least one matching element is found on
	 *         the web page
	 * @throws IOException
	 */
	public boolean isElementPresent(String... keys) throws IOException {
		return !getVisibleElements(keys).isEmpty();
	}

	/**
	 * Wait for Angular requests to finish.
	 */
	public void waitForAngular() {
		ngWebDriver.waitForAngularRequestsToFinish();
	}

	/**
	 * Navigates to specified URL.
	 * 
	 * @param key the key from {@code project.properties}
	 * @return String - the URL
	 * @throws IOException
	 */
	public String navigate(String key) throws IOException {
		String url = Configuration.project(key);
		driver.navigate().to(url);
		return url;
	}

	/**
	 * Waits for a web element to refresh and become visible.
	 * 
	 * @param keys the key(s) from {@code ui-map.properties}
	 * @return WebElement - the refreshed element
	 * @throws IOException
	 */
	public WebElement waitForRefreshAndVisibleElement(String... keys) throws IOException {
		WebElement element = explicitWait().until(ExpectedConditions
				.refreshed(ExpectedConditions.visibilityOfElementLocated(new ByChained(getBys(keys)))));
		return element;
	}

	/**
	 * Waits for a web element to refresh and become clickable.
	 * 
	 * @param keys the key(s) from {@code ui-map.properties}
	 * @return WebElement - the refreshed element
	 * @throws IOException
	 */
	public WebElement waitForRefreshAndClickableElement(String... keys) throws IOException {
		WebElement element = explicitWait().until(
				ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(new ByChained(getBys(keys)))));
		return element;
	}

	/**
	 * Gets the page title of the current window.
	 */
	public String getPageTitle() {
		return driver.getTitle();
	}

	/**
	 * Clicks an element on the web page.
	 * 
	 * @param keys the key(s) from {@code ui-map.properties}
	 * @return WebElement - the clickable element
	 * @throws IOException
	 */
	public WebElement click(String... keys) throws IOException {
		WebElement element = getClickableElement(keys);
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
	 * Selects dropdown value by visible text.
	 * 
	 * @param text   the visible text from the dropdown options
	 * @param select the Select element
	 */
	public void selectByVisibleText(String text, Select select) {
		select.selectByVisibleText(text);
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
	public String openWindowByLink(String url) {
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
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("scroll(" + xPos + ", 0);");
	}

	/**
	 * Scroll the screen up or down.
	 * 
	 * @param yPos positive value to scroll down, negative value to scroll up
	 */
	public void scrollVertical(int yPos) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("scroll(0, " + yPos + ");");
	}

	/**
	 * Scroll to specific element on the web page.
	 * 
	 * @param keys the key(s) from {@code ui-map.properties}
	 * @return WebElement - the element
	 * @throws IOException
	 */
	public WebElement scrollToElement(String... keys) throws IOException {
		WebElement element = getVisibleElement(keys);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", element);
		return element;
	}

	/**
	 * Scroll to specific element on the web page.
	 * 
	 * @param element the element to scroll to
	 */
	public void scrollToElement(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", element);
	}

	/**
	 * Captures and saves screenshot in PNG format. Images are stored in
	 * {@code /target/cucumber-screenshots/}.
	 * 
	 * @return String - the absolute path to the saved screenshot
	 * @throws IOException
	 */
	public String captureScreenshot() throws IOException {
		StringBuilder builder = new StringBuilder();
		builder.append(StringUtils.replace(System.getProperty("user.dir"), "\\", "/"));
		builder.append("/target/cucumber-screenshots/sshot_");
		builder.append(System.currentTimeMillis());
		builder.append(".png");
		String screenshot = builder.toString();
		File imgFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(imgFile, new File(screenshot));
		return screenshot;
	}

	/**
	 * Captures and embeds screenshot in generated HTML report. Reports can be found
	 * in {@code /target/}.
	 * 
	 * @return byte[ ] - the screenshot in byte array
	 */
	public byte[] embedScreenshot() {
		byte[] imgBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
		scenario.embed(imgBytes, "image/png");
		return imgBytes;
	}

}
