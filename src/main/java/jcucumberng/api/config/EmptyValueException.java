package jcucumberng.api.config;

/**
 * {@code EmptyValueException} is thrown when the value of a specified key is
 * empty in a {@code .properties} file.
 * 
 * @author Kat Rollo {@literal <rollo.katherine@gmail.com>}
 */
@SuppressWarnings("serial")
public class EmptyValueException extends RuntimeException {
	public EmptyValueException(String message) {
		super(message);
	}
}
