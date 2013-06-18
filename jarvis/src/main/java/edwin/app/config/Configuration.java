package edwin.app.config;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.ini4j.InvalidFileFormatException;
import org.ini4j.Wini;

public class Configuration {

	private static final Logger LOGGER = Logger.getLogger(Configuration.class);

	// Config file
	private static final String CONFIG_FILE = "config/config.ini";

	// INI constants
	private static final String INI_SECTION_GENERAL = "General";
	private static final String INI_PARAM_BOTNAME = "botName";

	private static Configuration s_Instance = null;

	public static synchronized Configuration getInstance() {
		if (s_Instance == null) {
			s_Instance = new Configuration();
		}

		return s_Instance;
	}

	private Configuration() {
		// Read ini file
		try {
			m_IniFile = new Wini(new File(CONFIG_FILE));
		} catch (InvalidFileFormatException e) {
			LOGGER.error(e, e);
		} catch (IOException e) {
			LOGGER.error(e, e);
		}
	}

	public String getBotName() {
		return getParam(INI_PARAM_BOTNAME);
	}

	public String getParam(String paramName) {
		return m_IniFile.get(INI_SECTION_GENERAL, paramName);
	}

	public String getParam(String section, String paramName) {
		return m_IniFile.get(section, paramName);
	}

	private Wini m_IniFile = null;
}
