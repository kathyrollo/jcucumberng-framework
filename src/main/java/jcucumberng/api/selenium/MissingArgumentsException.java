package jcucumberng.api.selenium;

/**
 * {@code MissingArgumentsException} is thrown when the arbitrary parameters or
 * varargs of a method is empty (length = 0).
 * 
 * @author Kat Rollo &lt;rollo.katherine@gmail.com&gt;
 */
@SuppressWarnings("serial")
public class MissingArgumentsException extends RuntimeException {
	public MissingArgumentsException(String message) {
		super(message);
	}
}
