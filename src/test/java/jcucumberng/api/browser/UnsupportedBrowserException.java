package jcucumberng.api.browser;

/**
 * {@code UnsupportedBrowserException} is thrown when the specified
 * {@code browser} is empty or invalid in {@code framework.properties}.
 * 
 * @author Kat Rollo &lt;rollo.katherine@gmail.com&gt;
 */
@SuppressWarnings("serial")
public class UnsupportedBrowserException extends RuntimeException {
	public UnsupportedBrowserException(String message) {
		super(message);
	}
}
