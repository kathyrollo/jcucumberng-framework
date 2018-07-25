package jcucumberng.framework.factory;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ByIdOrName;
import org.openqa.selenium.support.pagefactory.ByAll;
import org.openqa.selenium.support.pagefactory.ByChained;

import com.paulhammant.ngwebdriver.ByAngular;

import jcucumberng.framework.api.ConfigLoader;
import jcucumberng.framework.api.Selenium;
import jcucumberng.framework.enums.ByMethod;
import jcucumberng.framework.exceptions.InvalidPatternException;
import jcucumberng.framework.exceptions.UnsupportedByMethodException;
import jcucumberng.framework.strings.Messages;
import jcucumberng.framework.strings.Text;

/**
 * {@code ByFactory} handles actions for manipulating the Selenium {@code By}
 * object.
 * 
 * @author Kat Rollo <rollo.katherine@gmail.com>
 */
public final class ByFactory {

	private ByFactory() {
		// Prevent instantiation
	}

	/**
	 * Gets the Selenium {@code By} object.
	 * 
	 * @param key the key from {@code ui-map.properties}
	 * @return By - the {@code By} object
	 * @throws IOException
	 */
	public static By getInstance(String key) throws IOException {
		String method = null;
		String selector = null;
		String text = null;
		String keys[] = {};
		By[] bys = null;

		String value = ConfigLoader.uiMap(key);
		if (StringUtils.isBlank(value)) {
			value = Text.BLANK;
		}
		if (!value.matches(".+:.+")) {
			throw new InvalidPatternException(Messages.INVALID_UI_PATTERN + value);
		}

		method = StringUtils.substringBefore(value, ":");

		selector = StringUtils.substringAfter(value, ":");
		if (StringUtils.contains(selector, "|")) {
			if (StringUtils.containsIgnoreCase(value, ByMethod.BY_ALL.toString())) {
				keys = StringUtils.split(StringUtils.substringAfter(value, ":"), "|");
				bys = Selenium.getBys(keys);
				selector = null;
			}
			if (StringUtils.containsIgnoreCase(value, ByMethod.BY_CHAINED.toString())) {
				keys = StringUtils.split(StringUtils.substringAfter(value, ":"), "|");
				bys = Selenium.getBys(keys);
				selector = null;
			}
			if (StringUtils.containsIgnoreCase(value, ByMethod.CSS_CONTAINING_TEXT.toString())) {
				text = StringUtils.substringAfter(selector, "|");
				selector = StringUtils.substringBefore(selector, "|");
			}
		}

		By by = null;
		try {
			ByMethod byMethod = ByMethod.valueOf(StringUtils.upperCase(method));
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
			case BY_ALL:
				by = new ByAll(bys);
				break;
			case BY_CHAINED:
				by = new ByChained(bys);
				break;
			case BY_ID_OR_NAME:
				by = new ByIdOrName(selector);
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
				by = ByAngular.cssContainingText(selector, text);
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
		} catch (IllegalArgumentException | NullPointerException ex) {
			if (StringUtils.isBlank(method)) {
				method = Text.BLANK;
			}
			throw new UnsupportedByMethodException(Messages.UNSUPPORTED_BY_METHOD + method);
		}

		return by;
	}

}
