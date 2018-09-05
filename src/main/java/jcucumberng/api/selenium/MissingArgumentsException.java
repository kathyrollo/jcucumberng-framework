package jcucumberng.api.selenium;

/**
 * {@code MissingArgumentsException} is thrown when the arbitrary parameters or
 * varargs of a method is empty (length = 0).
 * 
 * @author Kat Rollo {@literal <rollo.katherine@gmail.com>}
 */
@SuppressWarnings("serial")
public class MissingArgumentsException extends RuntimeException {
	public MissingArgumentsException(String message) {
		super(message);
	}
}
