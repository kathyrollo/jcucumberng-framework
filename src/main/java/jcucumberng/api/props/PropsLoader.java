package jcucumberng.api.props;

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

	private static final String USER_DIR = "user.dir";
	private static final String RESOURCES_DIR = "/src/main/resources/";
	private static final String NO_SUCH_KEY = "Key not found in ";

	private PropsLoader() {
		// No instantiation
	}

	/**
	 * Reads framework config from {@code framework.properties}.
	 * 
	 * @param key the config key (Example: {@code browser=CHROME32}, key =
	 *            {@code browser})
	 * @return String - the value corresponding to the given key (Example:
	 *         {@code browser=CHROME32}, value = {@code CHROME32})
	 * @throws IOException
	 */
	public static String frameworkConf(String key) throws IOException {
		String propsFileName = "framework.properties";
		StringBuilder builder = new StringBuilder();
		builder.append(StringUtils.replace(System.getProperty(USER_DIR), "\\", "/"));
		builder.append(RESOURCES_DIR);
		builder.append(propsFileName);

		InputStream inputStream = new FileInputStream(builder.toString());
		Properties props = new Properties();
		props.load(inputStream);

		String value = props.getProperty(key);
		if (StringUtils.isBlank(value)) {
			builder.setLength(0);
			builder.append(propsFileName + ": " + key);
			throw new NoSuchKeyException(NO_SUCH_KEY + builder.toString());
		}

		return StringUtils.trim(value);
	}

	/**
	 * Reads project config from {@code project.properties}.
	 * 
	 * @param key the config key (Example: {@code base.url=www.google.com}, key =
	 *            {@code base.url})
	 * @return String - the value corresponding to the given key (Example:
	 *         {@code base.url=www.google.com}, value = {@code www.google.com})
	 * @throws IOException
	 */
	public static String projectConf(String key) throws IOException {
		String propsFileName = "project.properties";
		StringBuilder builder = new StringBuilder();
		builder.append(StringUtils.replace(System.getProperty(USER_DIR), "\\", "/"));
		builder.append(RESOURCES_DIR);
		builder.append(propsFileName);

		InputStream inputStream = new FileInputStream(builder.toString());
		Properties props = new Properties();
		props.load(inputStream);

		String value = props.getProperty(key);
		if (StringUtils.isBlank(value)) {
			builder.setLength(0);
			builder.append(propsFileName + ": " + key);
			throw new NoSuchKeyException(NO_SUCH_KEY + builder.toString());
		}

		return StringUtils.trim(value);
	}

	/**
	 * Reads web elements from {@code ui-map.properties}.
	 * 
	 * @param key the element key (Example: {@code first.name=id:firstName}, key =
	 *            {@code first.name})
	 * @return String - the value corresponding to the given key (Example:
	 *         {@code first.name=id:firstName}, value = {@code id:firstName})
	 * @throws IOException
	 */
	public static String uiMap(String key) throws IOException {
		String propsFileName = "ui-map.properties";
		StringBuilder builder = new StringBuilder();
		builder.append(StringUtils.replace(System.getProperty(USER_DIR), "\\", "/"));
		builder.append(RESOURCES_DIR);
		builder.append(propsFileName);

		InputStream inputStream = new FileInputStream(builder.toString());
		Properties props = new Properties();
		props.load(inputStream);

		String value = props.getProperty(key);
		if (StringUtils.isBlank(value)) {
			builder.setLength(0);
			builder.append(propsFileName + ": " + key);
			throw new NoSuchKeyException(NO_SUCH_KEY + builder.toString());
		}

		return StringUtils.trim(value);
	}

}
