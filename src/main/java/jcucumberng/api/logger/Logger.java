package jcucumberng.api.logger;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import jcucumberng.api.properties.PropsLoader;

/**
 * {@code Logger} handles the logging mechanism of the framework.
 * 
 * @author Kat Rollo {@literal <rollo.katherine@gmail.com>}
 */
public final class Logger {

	private Logger() {
		// No instantiation
	}

	/**
	 * Loads {@code log4j2.conf.file} from {@code framework.properties}.
	 */
	public static void init() {
		String cfgFile = null;
		try {
			cfgFile = PropsLoader.frameworkConf("log4j2.conf.file");
		} catch (IOException ioe) {
			throw new MissingLoggerException("Cannot find logger config file: " + cfgFile);
		}

		StringBuilder builder = new StringBuilder();
		builder.append(StringUtils.replace(System.getProperty("user.dir"), "\\", "/"));
		builder.append("/src/main/resources/");
		builder.append(cfgFile);

		File log4j2File = new File(builder.toString());
		System.setProperty("log4j2.configurationFile", log4j2File.toURI().toString());
	}

}
