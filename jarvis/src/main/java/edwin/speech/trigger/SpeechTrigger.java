package edwin.speech.trigger;

import edwin.common.Variable;
import edwin.common.VariableRepository;
import edwin.core.trigger.AbstractTrigger;
import edwin.core.trigger.Trigger;
import edwin.speech.service.SpeechRecognitionListener;
import edwin.speech.service.SpeechRecognitionService;

public class SpeechTrigger extends AbstractTrigger implements
		SpeechRecognitionListener {

	public SpeechTrigger(String pattern) {
		this.pattern = pattern;
	}

	public void enable() {
		SpeechRecognitionService service = SpeechRecognitionService
				.getInstance();
		service.addGrammar(Long.toString(getId()), pattern);
		service.addListener(this);
	}

	public void disable() {
		SpeechRecognitionService service = SpeechRecognitionService
				.getInstance();
		service.removeListener(this);
		service.removeGrammar(Long.toString(getId()));
	}

	public void newRecognition(String grammarName, String sentence,
			String[] tags) {

		if (Long.toString(getId()).equals(grammarName)) {
			VariableRepository localVars = new VariableRepository();

			// Set the command variable
			Variable<String> commandVar = new Variable<String>(
					Trigger.COMMAND_VARIABLE, sentence);
			localVars.add(commandVar);

			// Convert tags into variables
			for (String tag : tags) {

				String[] tokens = tag.split(":");
				if (tokens.length > 1) {

					// If the tag is something like "foo:bar", this is a
					// variable
					// declaration

					String value = tokens[tokens.length - 1];
					for (int i = 0; i < tokens.length - 1; i++) {
						String name = tokens[i];
						Variable<String> tagVar = new Variable<String>(name,
								value);
						localVars.add(tagVar);
					}

				} else {

					// Else, this is a boolean

					Variable<Boolean> tagVar = new Variable<Boolean>(tag, true);
					localVars.add(tagVar);
				}
			}

			setTriggered(true, localVars);
			setTriggered(false, localVars);
		}
	}

	private String pattern;
}
