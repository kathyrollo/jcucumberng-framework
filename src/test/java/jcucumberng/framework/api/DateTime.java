package jcucumberng.framework.api;

import jcucumberng.framework.exceptions.OutOfRangeException;
import jcucumberng.framework.strings.Messages;

/**
 * {@code DateTime} handles actions for manipulating date and time.
 * 
 * @author Kat Rollo <rollo.katherine@gmail.com>
 */
public final class DateTime {

	private DateTime() {
		// Prevent instantiation
	}

	/**
	 * Converts seconds to millis.
	 * 
	 * @param waitTime value in seconds
	 * @return int - the value in millis
	 */
	public static int convertSecondsToMillis(String waitTime) {
		int secs = Integer.parseInt(waitTime);
		int millis = secs * 1000;
		if (!(millis >= 1000 && millis <= 60000)) {
			throw new OutOfRangeException(Messages.OUT_OF_RANGE + millis);
		}
		return millis;
	}

}
