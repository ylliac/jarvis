package edwin.plugins.translate.service;

import org.apache.log4j.Logger;

import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;

import edwin.app.config.Configuration;
import edwin.core.service.Service;

public class TranslateService implements Service {

	//Configuration
	private static final String INI_SECTION = "Translate";
	private static final String INI_PARAM_CLIENT_ID = "clientId";
	private static final String INI_PARAM_CLIENT_SECRET = "clientSecret";

	private static final Logger LOGGER = Logger
			.getLogger(TranslateService.class);

	public TranslateService() {
	}

	public String translate(String text, Language source, Language destination) {
		String result = null;

		try {
			result = Translate.execute(text, source, destination);
		} catch (Exception e) {
			LOGGER.error(e, e);
		}

		return result;
	}

	public void enable() {
		String clientId = Configuration.getInstance().getParam(INI_SECTION,
				INI_PARAM_CLIENT_ID);
		String clientSecret = Configuration.getInstance().getParam(INI_SECTION,
				INI_PARAM_CLIENT_SECRET);
		Translate.setClientId(clientId);
		Translate.setClientSecret(clientSecret);
	}

	public void disable() {
	}

	public void ready() {
	}

}
