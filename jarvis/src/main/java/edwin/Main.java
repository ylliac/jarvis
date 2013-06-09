package edwin;

import org.apache.log4j.Logger;

import edwin.base.action.ShutdownAction;
import edwin.core.rule.Rule;
import edwin.speech.action.SpeechAction;
import edwin.speech.service.SpeechRecognitionService;
import edwin.speech.service.SpeechService;
import edwin.speech.trigger.SpeechTrigger;

public class Main {

	private static final Logger LOGGER = Logger.getLogger(Main.class);
	
	public static void main(String[] args) {

		// Speech service
		SpeechService speechService = SpeechService.getInstance();
		speechService.enable();

		// Speech recognition service
		SpeechRecognitionService speechRecognitionService = SpeechRecognitionService
				.getInstance();
		speechRecognitionService.enable();

		// Rules
		SpeechTrigger sayStopTrigger = new SpeechTrigger("stop");
		ShutdownAction shutdownAction = new ShutdownAction();
		Rule sayStop = new Rule("stop", sayStopTrigger, shutdownAction);
		sayStop.enable();
		
		SpeechTrigger sayHelloTrigger = new SpeechTrigger(
				"dis bonjour {text:vous avez dit}");
		SpeechAction sayHelloAction = new SpeechAction("%text %_command");
		Rule sayHello = new Rule("sayHello", sayHelloTrigger, sayHelloAction);
		sayHello.enable();
		
		LOGGER.info("Ready");

		speechRecognitionService.waitWhileEnabled();
	}

}
