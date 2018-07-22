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
	 * Converts seconds to millis with specified range.
	 * 
	 * @param waitTime value in seconds from a {@code .properties} file
	 * @param begin    lowerbound value of the range in seconds, inclusive
	 * @param end      upperbound value of the range in seconds, inclusive
	 * 
	 * @return int - value in millis
	 */
	public static int convertSecondsToMillisWithRange(String waitTime, int begin, int end) {
		int millis = Integer.parseInt(waitTime) * 1000;
		begin *= 1000;
		end *= 1000;
		if (!(millis >= begin && millis <= end)) {
			throw new OutOfRangeException(Messages.OUT_OF_RANGE + millis);
		}
		return millis;
	}

}
