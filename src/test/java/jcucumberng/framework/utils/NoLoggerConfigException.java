package jcucumberng.framework.utils;

/**
 * {@code NoLoggerConfigException} is thrown when no log4j2 config file is
 * found.
 * 
 * @author Kat Rollo &lt;rollo.katherine@gmail.com&gt;
 */
@SuppressWarnings("serial")
public class NoLoggerConfigException extends RuntimeException {
	public NoLoggerConfigException(String message) {
		super(message);
	}
}
