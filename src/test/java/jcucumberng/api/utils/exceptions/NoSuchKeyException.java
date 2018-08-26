package jcucumberng.api.utils.exceptions;

/**
 * {@code NoSuchKeyException} is thrown when the expected key is not found in a
 * {@code .properties} file.
 * 
 * @author Kat Rollo &lt;rollo.katherine@gmail.com&gt;
 */
@SuppressWarnings("serial")
public class NoSuchKeyException extends RuntimeException {
	public NoSuchKeyException(String message) {
		super(message);
	}
}
