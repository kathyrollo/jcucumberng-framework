package jcucumberng.framework.exceptions;

/**
 * {@code UnsupportedBrowserException} is thrown when the specified browser is
 * empty or invalid in {@code config.properties}.
 * 
 * @author Kat Rollo <rollo.katherine@gmail.com>
 */
@SuppressWarnings("serial")
public class UnsupportedBrowserException extends RuntimeException {
	public UnsupportedBrowserException(String message) {
		super(message);
	}
}
