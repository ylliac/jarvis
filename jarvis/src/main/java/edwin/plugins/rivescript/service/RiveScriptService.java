package edwin.plugins.rivescript.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import edwin.app.config.Configuration;
import edwin.core.service.Service;
import edwin.plugins.rivescript.bot.RiveScript;
import edwin.plugins.rivescript.bot.TopicManager;

public class RiveScriptService implements Service {

	private static final Logger LOGGER = Logger
			.getLogger(RiveScriptService.class);

	// Configuration
	private static final String INI_SECTION = "RiveScript";
	private static final String INI_PARAM_DIR = "directory";

	public void enable() {
		String directory = Configuration.getInstance().getParam(INI_SECTION,
				INI_PARAM_DIR);

		m_Bot = new RiveScript(false);
		m_Bot.loadDirectory(directory);
		m_Bot.sortReplies();
	}

	public void ready() {
	}

	public void disable() {
		m_Bot = null;
	}

	public List<String> getTriggers() {

		List<String> result = new ArrayList<String>();

		try {
			Field topicsField = m_Bot.getClass().getDeclaredField("topics");
			topicsField.setAccessible(true);
			TopicManager topicManager = (TopicManager) topicsField.get(m_Bot);

			String[] topics = topicManager.listTopics();
			for (int t = 0; t < topics.length; t++) {
				String topic = topics[t];
				String[] triggers = topicManager.topic(topic).listTriggers();
				for (int i = 0; i < triggers.length; i++) {
					result.add(triggers[i]);
				}
			}

		} catch (SecurityException e) {
			LOGGER.error(e, e);
		} catch (NoSuchFieldException e) {
			LOGGER.error(e, e);
		} catch (IllegalArgumentException e) {
			LOGGER.error(e, e);
		} catch (IllegalAccessException e) {
			LOGGER.error(e, e);
		}

		return result;
	}

	public String reply(String message) {
		return m_Bot.reply("localuser", message);
	}

	private RiveScript m_Bot = null;

}
