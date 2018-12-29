package jcucumberng.api.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

/**
 * {@code Loader} handles actions for reading {@code .properties} files.
 * 
 * @author Kat Rollo {@literal <rollo.katherine@gmail.com>}
 */
public final class Loader {

	private Loader() {
		// No instantiation
	}

	/**
	 * Reads framework config from {@code framework.properties}.
	 * 
	 * @param key the config key (Example: {@code browser=CHROME32}, key =
	 *            {@code browser})
	 * @return String - the value of the given key (Example:
	 *         {@code browser=CHROME32}, value = {@code CHROME32})
	 * @throws IOException
	 */
	public static String framework(String key) throws IOException {
		return load("framework.properties", key);
	}

	/**
	 * Reads project config from {@code project.properties}.
	 * 
	 * @param key the config key (Example: {@code base.url=www.google.com}, key =
	 *            {@code base.url})
	 * @return String - the value of the given key (Example:
	 *         {@code base.url=www.google.com}, value = {@code www.google.com})
	 * @throws IOException
	 */
	public static String project(String key) throws IOException {
		return load("project.properties", key);
	}

	/**
	 * Reads web elements from {@code ui-map.properties}.
	 * 
	 * @param key the element key (Example: {@code first.name=id:firstName}, key =
	 *            {@code first.name})
	 * @return String - the value of the given key (Example:
	 *         {@code first.name=id:firstName}, value = {@code id:firstName})
	 * @throws IOException
	 */
	public static String uiMap(String key) throws IOException {
		return load("ui-map.properties", key);
	}

	/**
	 * Loads a {@code .properties} file.
	 * 
	 * @param props the filename with extension (Example: {@code config.properties})
	 * @param key   the key from the given {@code .properties} file
	 * @return String - the value of the given key
	 * @throws IOException
	 */
	private static String load(String props, String key) throws IOException {
		StringBuilder builder = new StringBuilder();
		builder.append(StringUtils.replace(System.getProperty("user.dir"), "\\", "/"));
		builder.append("/src/main/resources/");
		builder.append(props);

		InputStream inputStream = new FileInputStream(builder.toString());
		Properties properties = new Properties();
		properties.load(inputStream);

		builder.setLength(0); // Clear builder
		builder.append(props + ": " + key);
		String value = properties.getProperty(key);
		if (StringUtils.isWhitespace(value)) {
			throw new EmptyValueException("Value is empty or blank in " + builder.toString());
		}
		if (StringUtils.isBlank(value)) {
			throw new NoSuchKeyException("Key not found in " + builder.toString());
		}

		return StringUtils.trim(value);
	}

}
