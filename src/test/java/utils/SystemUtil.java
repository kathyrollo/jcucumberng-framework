package utils;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.IOException;

/**
 * This class handles actions for generating native system inputs from devices
 * such as mouse or keyboard.
 * 
 * @author Katherine Rollo (rollo.katherine@gmail.com)
 */
public final class SystemUtil {

	private SystemUtil() {
		// Prevent instantiation
	}

	/**
	 * Accepts a single key entry. The key is pressed and released immediately.
	 * 
	 * @param key
	 *            KeyEvent constant from java.awt.event.KeyEvent
	 * @throws AWTException
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	public static void pressKey(int key) throws AWTException, NumberFormatException, IOException {
		Robot robot = new Robot();
		robot.keyPress(key);
		robot.keyRelease(key);
		robot.delay(Integer.parseInt(ConfigUtil.readKey("keypress_wait")));
		robot = null; // Destroy robot
	}

	/**
	 * Accepts multiple key entries (e.g. shortcut command). The keys are pressed
	 * simultaneously and released in reverse order.
	 * 
	 * @param keys
	 *            an array of KeyEvent constants from java.awt.event.KeyEvent,
	 *            specify keys in order of entry
	 * @throws AWTException
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	public static void pressKeys(int[] keys) throws AWTException, NumberFormatException, IOException {
		Robot robot = new Robot();
		for (int ctr = 0; ctr < keys.length; ctr++) {
			robot.keyPress(keys[ctr]); // Press and hold keys
		}
		robot.delay(Integer.parseInt(ConfigUtil.readKey("keypress_wait")));
		for (int ctr = keys.length - 1; ctr > -1; ctr--) {
			robot.keyRelease(keys[ctr]); // Release keys in reverse order
		}
		robot = null;
	}

}
