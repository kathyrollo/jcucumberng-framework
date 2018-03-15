package jcucumberng.api;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * This class handles actions for configuring the test framework.
 */
public final class Configuration {

	private Configuration() {
		// Prevent instantiation
	}

	/**
	 * Reads a config file by passing a specific key. The file must be located in:
	 * /src/test/resources/config.properties
	 * 
	 * @param key
	 *            the name corresponding to the value in the key-value pair
	 * @return String - the value corresponding to the given key
	 * @throws IOException
	 * 
	 * @author Katherine Rollo (rollo.katherine@gmail.com)
	 */
	public static String readKey(String key) throws IOException {
		StringBuilder builder = new StringBuilder();
		builder.append(System.getProperty("user.dir").replace("\\", "/"));
		builder.append("/src/test/resources/config.properties");

		InputStream inStream = new FileInputStream(builder.toString());
		Properties properties = new Properties();
		properties.load(inStream);

		return properties.getProperty(key).trim();
	}

}
