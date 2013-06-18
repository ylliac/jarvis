package edwin.plugins.speech.action;

import org.apache.log4j.Logger;

import edwin.app.Edwin;
import edwin.app.config.Configuration;
import edwin.core.action.Action;
import edwin.core.variable.VariableRepository;
import edwin.core.variable.VariableUtilities;
import edwin.plugins.speech.service.SpeechService;

public class SpeechAction implements Action {

	private static final Logger LOGGER = Logger.getLogger(SpeechAction.class);

	public SpeechAction(String message) {
		this.message = message;
	}

	public void execute(VariableRepository localVars, Edwin edwin) {

		// Replace variables in message...
		//... first with local vars
		String newMessage = VariableUtilities.replaceVariablesByTheirValues(
				message, localVars);
		//.. then with global vars
		newMessage = VariableUtilities.replaceVariablesByTheirValues(
				newMessage, edwin.getGlobalVariables());

		SpeechService speechService = edwin.getService(SpeechService.class);
		speechService.say(newMessage);
		
		LOGGER.debug(Configuration.getInstance().getBotName() + "> " + newMessage);
	}

	private String message = "";

}
