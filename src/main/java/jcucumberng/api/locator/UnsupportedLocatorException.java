package jcucumberng.api.locator;

/**
 * {@code UnsupportedLocatorException} is thrown when the locator in
 * {@code ui-map.properties} is unrecognized in the key-value pair.
 * 
 * @author Kat Rollo &lt;rollo.katherine@gmail.com&gt;
 */
@SuppressWarnings("serial")
public class UnsupportedLocatorException extends RuntimeException {
	public UnsupportedLocatorException(String message) {
		super(message);
	}
}
