package jcucumberng.framework.api;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import jcucumberng.framework.exceptions.NoSuchKeyException;
import jcucumberng.framework.strings.Messages;

/**
 * {@code PropsLoader} handles actions for reading {@code .properties} files.
 * 
 * @author Kat Rollo <rollo.katherine@gmail.com>
 */
public final class PropsLoader {

	private PropsLoader() {
		// Prevent instantiation
	}

	/**
	 * Reads framework config from {@code framework.properties}.
	 * 
	 * @param key
	 *            the config name (Example: {@code browser=CHROME32}, key =
	 *            {@code browser})
	 * @return String - the value corresponding to the given key (Example:
	 *         {@code browser=CHROME32}, value = {@code CHROME32})
	 * @throws IOException
	 */
	public static String configFramework(String key) throws IOException {
		String propsFileName = "framework.properties";
		StringBuilder builder = new StringBuilder();
		builder.append(System.getProperty("user.dir").replace("\\", "/"));
		builder.append("/src/test/resources/jcucumberng/framework/");
		builder.append(propsFileName);

		InputStream inputStream = new FileInputStream(builder.toString());
		Properties props = new Properties();
		props.load(inputStream);

		String value = props.getProperty(key);
		if (null == value) {
			builder.setLength(0);
			builder.append(propsFileName + ": " + key);
			throw new NoSuchKeyException(Messages.NO_SUCH_KEY + builder.toString());
		}
		return value.trim();
	}

	/**
	 * Reads project config from {@code project.properties}.
	 * 
	 * @param key
	 *            the config name (Example: {@code base.url=www.google.com}, key =
	 *            {@code base.url})
	 * @return String - the value corresponding to the given key (Example:
	 *         {@code base.url=www.google.com}, value = {@code www.google.com})
	 * @throws IOException
	 */
	public static String configProject(String key) throws IOException {
		String propsFileName = "project.properties";
		StringBuilder builder = new StringBuilder();
		builder.append(System.getProperty("user.dir").replace("\\", "/"));
		builder.append("/src/test/resources/jcucumberng/project/");
		builder.append(propsFileName);

		InputStream inputStream = new FileInputStream(builder.toString());
		Properties props = new Properties();
		props.load(inputStream);

		String value = props.getProperty(key);
		if (null == value) {
			builder.setLength(0);
			builder.append(propsFileName + ": " + key);
			throw new NoSuchKeyException(Messages.NO_SUCH_KEY + builder.toString());
		}
		return value.trim();
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

		String value = props.getProperty(key);
		if (null == value) {
			throw new NoSuchKeyException(Messages.NO_SUCH_KEY_UIMAP + key);
		}
		return value.trim();
	}

}
