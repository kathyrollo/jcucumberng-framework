package jcucumberng.framework.utils;

/**
 * {@code LoggerConfigException} is thrown when an exception is encountered
 * while loading the log4j2 config file from a specified directory.
 * 
 * @author Kat Rollo &lt;rollo.katherine@gmail.com&gt;
 */
@SuppressWarnings("serial")
public class LoggerConfigException extends RuntimeException {
	public LoggerConfigException(String message) {
		super(message);
	}
}
