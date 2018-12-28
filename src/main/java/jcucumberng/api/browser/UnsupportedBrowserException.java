package jcucumberng.api.browser;

/**
 * {@code UnsupportedBrowserException} is thrown when the specified
 * {@code browser} is invalid in {@code framework.properties}.
 * 
 * @author Kat Rollo {@literal <rollo.katherine@gmail.com>}
 */
@SuppressWarnings("serial")
public class UnsupportedBrowserException extends RuntimeException {
	public UnsupportedBrowserException(String message) {
		super(message);
	}
}
