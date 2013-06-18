package edwin;

import org.apache.log4j.Logger;

import edwin.app.Edwin;
import edwin.core.rule.Rule;
import edwin.plugins.base.action.CurrentDateAction;
import edwin.plugins.base.action.CurrentTimeAction;
import edwin.plugins.base.action.PauseAction;
import edwin.plugins.base.action.ReplaceInLastOutput;
import edwin.plugins.base.action.ShutdownAction;
import edwin.plugins.base.trigger.EdwinReadyTrigger;
import edwin.plugins.freebase.action.SearchLastOutputInFreebase;
import edwin.plugins.freebase.service.FreebaseService;
import edwin.plugins.rivescript.action.BotReplyAction;
import edwin.plugins.rivescript.trigger.BotTrigger;
import edwin.plugins.speech.action.SayLastOutput;
import edwin.plugins.speech.action.SpeechAction;
import edwin.plugins.speech.trigger.SpeechTrigger;
import edwin.plugins.translate.action.TranslateLastOutputFromFrenchToEnglish;
import edwin.plugins.translate.service.TranslateService;
import edwin.plugins.weather.action.GetWeatherAction;
import edwin.plugins.weather.service.WeatherService;

public class Main {

	private static final Logger LOGGER = Logger.getLogger(Main.class);

	public static void main(String[] args) {

		// EDWIN : Create
		Edwin edwin = new Edwin();

		// EDWIN : Services
		edwin.addService(new TranslateService());
		edwin.addService(new FreebaseService());
		edwin.addService(new WeatherService());

		// RULE : Ready --> "Bonjour"
		edwin.addRule(new Rule("Hello", new EdwinReadyTrigger(),
				new SpeechAction("Bonjour")));

		// RULE : "Stop" --> Shutdown
		edwin.addRule(new Rule("stop", new SpeechTrigger("stop"),
				new SpeechAction("Au revoir"), new ShutdownAction()));

		// RULE : Current time
		edwin.addRule(new Rule("currentTime", new SpeechTrigger(
				"Quelle heure il est", "Quelle heure est il"),
				new CurrentTimeAction(), new SayLastOutput()));

		// RULE : Current date
		edwin.addRule(new Rule("currentDate", new SpeechTrigger(
				"Quelle jour on est", "Quelle est la date d'aujourd'hui"),
				new CurrentDateAction(), new SayLastOutput()));

		// RULE : Say hello
		edwin.addRule(new Rule("sayHello", new SpeechTrigger("dis bonjour"),
				new SpeechAction("Bonjour")));

		// RULE : RiveScript bot
		edwin.addRule(new Rule("RiveScript", new BotTrigger(),
				new BotReplyAction(), new SayLastOutput()));

		// RULE : Pause
		edwin.addRule(new Rule("Pause", new SpeechTrigger("Pause"),
				new PauseAction()));

		// RULE : Translate
		edwin.addRule(new Rule("Translate", new SpeechTrigger(
				"traduit <DICTATION>"),
				new ReplaceInLastOutput("traduit ", ""),
				new TranslateLastOutputFromFrenchToEnglish(),
				new SayLastOutput()));

		// RULE : Weather
		edwin.addRule(new Rule("Weather", new SpeechTrigger(
				"météo"),
				new GetWeatherAction(3),
				new SayLastOutput()));

		// RULE : Search In Freebase
		edwin.addRule(new Rule("Freebase", new SpeechTrigger(
				"cherche Cee Lo Green"),
				new ReplaceInLastOutput("cherche ", ""),
				new SearchLastOutputInFreebase(), new SayLastOutput()));

		// EDWIN : Start
		edwin.start();
	}
}
