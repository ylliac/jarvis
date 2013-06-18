package edwin.plugins.speech.trigger;

import edwin.core.trigger.AbstractTrigger;
import edwin.core.variable.InternVariables;
import edwin.core.variable.Variable;
import edwin.core.variable.VariableRepository;
import edwin.plugins.speech.service.SpeechRecognitionListener;
import edwin.plugins.speech.service.SpeechRecognitionService;

public class SpeechTrigger extends AbstractTrigger implements
		SpeechRecognitionListener {

	public SpeechTrigger(String... patterns) {
		this.patterns = patterns;
	}

	protected void enableImpl() {
		SpeechRecognitionService service = getEdwin().getService(
				SpeechRecognitionService.class);
		service.addGrammar(Long.toString(getId()), patterns);
		service.addListener(this);
	}

	protected void disableImpl() {
		SpeechRecognitionService service = getEdwin().getService(
				SpeechRecognitionService.class);
		service.removeListener(this);
		service.removeGrammar(Long.toString(getId()));
	}

	public void newRecognition(String grammarName, String sentence,
			String[] tags) {

		if (Long.toString(getId()).equals(grammarName)) {
			VariableRepository localVars = new VariableRepository();

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

			// Put the sentence in the last output
			InternVariables.setOutput(localVars, sentence);

			setTriggered(true, localVars);
			setTriggered(false, localVars);
		}
	}

	private String[] patterns;
}
