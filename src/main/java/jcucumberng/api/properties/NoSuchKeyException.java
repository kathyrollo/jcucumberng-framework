package jcucumberng.api.properties;

/**
 * {@code NoSuchKeyException} is thrown when the expected key is not found in a
 * {@code .properties} file.
 * 
 * @author Kat Rollo {@literal <rollo.katherine@gmail.com>}
 */
@SuppressWarnings("serial")
public class NoSuchKeyException extends RuntimeException {
	public NoSuchKeyException(String message) {
		super(message);
	}
}
