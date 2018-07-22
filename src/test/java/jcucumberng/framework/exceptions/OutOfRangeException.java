package jcucumberng.framework.exceptions;

/**
 * {@code OutOfRangeException} is thrown when the specified value is not within
 * the accepted range.
 * 
 * @author Kat Rollo <rollo.katherine@gmail.com>
 */
@SuppressWarnings("serial")
public class OutOfRangeException extends RuntimeException {
	public OutOfRangeException(String message) {
		super(message);
	}
}
