package jcucumberng.api.config;

/**
 * {@code NoSuchKeyOrValueException} is thrown when the expected key is not
 * found or the value is empty in a {@code .properties} file.
 * 
 * @author Kat Rollo {@literal <rollo.katherine@gmail.com>}
 */
@SuppressWarnings("serial")
public class NoSuchKeyOrValueException extends RuntimeException {
	public NoSuchKeyOrValueException(String message) {
		super(message);
	}
}
