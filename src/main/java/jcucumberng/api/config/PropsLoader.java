package jcucumberng.api.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

/**
 * {@code PropsLoader} handles actions for reading {@code .properties} files.
 * 
 * @author Kat Rollo {@literal <rollo.katherine@gmail.com>}
 */
public final class PropsLoader {

	private PropsLoader() {
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
		return loadProps("framework.properties", key);
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
		return loadProps("project.properties", key);
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
		return loadProps("ui-map.properties", key);
	}

	/**
	 * Loads a {@code .properties} file.
	 * 
	 * @param propsFile the filename with extension (Example:
	 *                  {@code config.properties})
	 * @param key       the key from the given propsFile
	 * @return String - the value of the given key
	 * @throws IOException
	 */
	private static String loadProps(String propsFile, String key) throws IOException {
		StringBuilder builder = new StringBuilder();
		builder.append(StringUtils.replace(System.getProperty("user.dir"), "\\", "/"));
		builder.append("/src/main/resources/");
		builder.append(propsFile);

		InputStream inputStream = new FileInputStream(builder.toString());
		Properties props = new Properties();
		props.load(inputStream);

		String value = props.getProperty(key);
		if (StringUtils.isBlank(value)) {
			builder.setLength(0);
			builder.append(propsFile + ": " + key);
			throw new NoSuchKeyException("Key not found in " + builder.toString());
		}

		return StringUtils.trim(value);
	}

}
