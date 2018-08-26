package jcucumberng.framework.utils;

/**
 * {@code DateTime} handles actions for manipulating date and time.
 * 
 * @author Kat Rollo &lt;rollo.katherine@gmail.com&gt;
 */
public final class DateTime {

	private DateTime() {
		// Prevent instantiation
	}

	/**
	 * Converts seconds to millis with specified range. Defaults to given
	 * {@code begin} or {@code end} if {@code waitTime} is less than or greater than
	 * respectively.
	 * 
	 * @param waitTime value in seconds from a {@code .properties} file
	 * @param begin    lowerbound value of the range in seconds, inclusive
	 * @param end      upperbound value of the range in seconds, inclusive
	 * @return int - value in millis
	 */
	public static int convertSecsToMillisWithRange(int waitTime, int begin, int end) {
		if (waitTime < begin) {
			waitTime = begin;
		}
		if (waitTime > end) {
			waitTime = end;
		}
		return waitTime * 1000;
	}

}
