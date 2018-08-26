package jcucumberng.api.logger;

/**
 * {@code MissingLoggerException} is thrown when no log4j2 config file is
 * found.
 * 
 * @author Kat Rollo &lt;rollo.katherine@gmail.com&gt;
 */
@SuppressWarnings("serial")
public class MissingLoggerException extends RuntimeException {
	public MissingLoggerException(String message) {
		super(message);
	}
}
