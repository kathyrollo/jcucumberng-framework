package jcucumberng.framework.factory;

import java.io.IOException;

import org.openqa.selenium.By;

import com.paulhammant.ngwebdriver.ByAngular;

import jcucumberng.framework.api.ConfigLoader;
import jcucumberng.framework.enums.ByMethod;
import jcucumberng.framework.exceptions.UnsupportedByMethodException;
import jcucumberng.framework.strings.Messages;

/**
 * {@code ByMethodFactory} handles actions for manipulating the Selenium
 * {@code By} object.
 * 
 * @author Kat Rollo <rollo.katherine@gmail.com>
 */
public final class ByMethodFactory {

	private ByMethodFactory() {
		// Prevent instantiation
	}

	/**
	 * Gets the Selenium {@code By} object.
	 * 
	 * @param key the key from {@code ui-map.properties}
	 * @return By - the {@code By} object
	 * @throws IOException
	 */
	public static By getBy(String key) throws IOException {
		String value = ConfigLoader.uiMap(key);
		String method = value.substring(0, value.lastIndexOf(":")).toUpperCase();
		String selector = value.substring(value.lastIndexOf(":") + 1);

		By by = null;
		try {
			ByMethod byMethod = ByMethod.valueOf(method);
			switch (byMethod) {
			case ID:
				by = By.id(selector);
				break;
			case NAME:
				by = By.name(selector);
				break;
			case LINK_TEXT:
				by = By.linkText(selector);
				break;
			case PARTIAL_LINK_TEXT:
				by = By.partialLinkText(selector);
				break;
			case TAG:
				by = By.tagName(selector);
				break;
			case CLASS:
				by = By.className(selector);
				break;
			case CSS:
				by = By.cssSelector(selector);
				break;
			case XPATH:
				by = By.xpath(selector);
				break;
			case BINDING:
				by = ByAngular.binding(selector);
				break;
			case MODEL:
				by = ByAngular.model(selector);
				break;
			case BUTTON_TEXT:
				by = ByAngular.buttonText(selector);
				break;
			case CSS_CONTAINING_TEXT:
				// TODO Implement cssContainingText
				break;
			case EXACT_BINDING:
				by = ByAngular.exactBinding(selector);
				break;
			case EXACT_REPEATER:
				by = ByAngular.exactRepeater(selector);
				break;
			case OPTIONS:
				by = ByAngular.options(selector);
				break;
			case PARTIAL_BUTTON_TEXT:
				by = ByAngular.partialButtonText(selector);
				break;
			case REPEATER:
				by = ByAngular.repeater(selector);
				break;
			default:
				// Handled in try-catch
				break;
			}
		} catch (IllegalArgumentException iae) {
			throw new UnsupportedByMethodException(Messages.UNSUPPORTED_BY_METHOD + method);
		}

		return by;
	}

}
