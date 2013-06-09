package edwin.speech.action;

import edwin.common.VariableRepository;
import edwin.common.VariableUtilities;
import edwin.core.action.Action;
import edwin.speech.service.SpeechService;

public class SpeechAction implements Action {

	public SpeechAction(String message) {
		this.message = message;
	}

	public void execute(VariableRepository localVars) {

		// Replace variables in message
		String newMessage = VariableUtilities.replaceVariablesByTheirValues(
				message, localVars);

		SpeechService.getInstance().say(newMessage);
	}

	private String message = "";

}
