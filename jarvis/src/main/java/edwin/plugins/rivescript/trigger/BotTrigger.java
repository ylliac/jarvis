package edwin.plugins.rivescript.trigger;

import java.util.List;

import edwin.core.trigger.AbstractTrigger;
import edwin.core.trigger.Trigger;
import edwin.core.trigger.TriggerListener;
import edwin.core.variable.VariableRepository;
import edwin.plugins.rivescript.service.RiveScriptService;
import edwin.plugins.speech.trigger.SpeechTrigger;

public class BotTrigger extends AbstractTrigger implements TriggerListener {

	@Override
	protected void enableImpl() {
		RiveScriptService botService = getEdwin().getService(
				RiveScriptService.class);

		// Get triggers defined in the script
		List<String> triggers = botService.getTriggers();

		// Convert them into speech triggers
		int i = 0;
		String[] speechTriggers = new String[triggers.size()];
		for (String trigger : triggers) {
			String convertedTrigger = fromRiveScriptToSpeechTrigger(trigger);
			speechTriggers[i++] = convertedTrigger;
		}

		// Create a speech trigger
		speechTrigger = new SpeechTrigger(speechTriggers);
		speechTrigger.enable(getEdwin());
		speechTrigger.addListener(this);
	}

	@Override
	protected void disableImpl() {
		if (speechTrigger != null) {
			speechTrigger.removeListener(this);
			speechTrigger.disable();
		}

		speechTrigger = null;
	}

	String fromRiveScriptToSpeechTrigger(String riveScriptTrigger) {
		// TODO
		return riveScriptTrigger;
	}

	public void triggered(boolean triggered, VariableRepository localVars,
			Trigger source) {
		setTriggered(triggered, localVars);		
	}

	private SpeechTrigger speechTrigger = null;

}
