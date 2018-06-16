package jcucumberng.api;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * {@code PropsLoader} handles actions for configuring the test framework.
 * 
 * @author Kat Rollo <rollo.katherine@gmail.com>
 */
public final class PropsLoader {

	private PropsLoader() {
		// Prevent instantiation
	}

	/**
	 * Reads a {@code config.properties} file by passing the key of a setting to
	 * configure properties of the project. The file must be located in
	 * {@code /src/test/resources/}.
	 * 
	 * @param key
	 *            the name corresponding to the value in the key-value pair
	 * @return String - the value corresponding to the given key
	 * @throws IOException
	 */
	public static String readConfig(String key) throws IOException {
		StringBuilder builder = new StringBuilder();
		builder.append(System.getProperty("user.dir").replace("\\", "/"));
		builder.append("/src/test/resources/config.properties");

		InputStream inputStream = new FileInputStream(builder.toString());
		Properties props = new Properties();
		props.load(inputStream);

		return props.getProperty(key).trim();
	}

	/**
	 * Reads a {@code ui-map.properties} file by passing the key of a locator used
	 * to find an element on a web page. The file must be located in
	 * {@code /src/test/resources/}.
	 * 
	 * @param key
	 *            the name corresponding to the value in the key-value pair
	 * @return String - the value corresponding to the given key
	 * @throws IOException
	 */
	public static String readUiMap(String key) throws IOException {
		StringBuilder builder = new StringBuilder();
		builder.append(System.getProperty("user.dir").replace("\\", "/"));
		builder.append("/src/test/resources/ui-map.properties");

		InputStream inputStream = new FileInputStream(builder.toString());
		Properties props = new Properties();
		props.load(inputStream);

		return props.getProperty(key).trim();
	}

}
