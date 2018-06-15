package jucucumberng.exceptions;

/**
 * {@code MissingArgumentsException} is thrown when the arbitrary parameters or
 * varargs of a method is empty. All varargs in {@code jcucumber.api} require at
 * least 1 parameter and its length must not be equal to 0.
 * 
 * @author Kat Rollo <rollo.katherine@gmail.com>
 */
@SuppressWarnings("serial")
public class MissingArgumentsException extends RuntimeException {
	public MissingArgumentsException(String message) {
		super(message);
	}
}
