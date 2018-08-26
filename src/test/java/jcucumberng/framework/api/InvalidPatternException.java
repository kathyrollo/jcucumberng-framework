package jcucumberng.framework.api;

/**
 * {@code InvalidPatternException} is thrown when the specified pattern does not
 * match the expected pattern in {@code ui-map.properties}.
 * 
 * @author Kat Rollo &lt;rollo.katherine@gmail.com&gt;
 */
@SuppressWarnings("serial")
public class InvalidPatternException extends RuntimeException {
	public InvalidPatternException(String message) {
		super(message);
	}
}
