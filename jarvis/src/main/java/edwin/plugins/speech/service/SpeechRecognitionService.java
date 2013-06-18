package edwin.plugins.speech.service;

import java.beans.PropertyVetoException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.speech.AudioException;
import javax.speech.Central;
import javax.speech.EngineException;
import javax.speech.EngineModeDesc;
import javax.speech.EngineStateError;
import javax.speech.recognition.FinalRuleResult;
import javax.speech.recognition.GrammarException;
import javax.speech.recognition.Recognizer;
import javax.speech.recognition.RecognizerProperties;
import javax.speech.recognition.ResultEvent;
import javax.speech.recognition.ResultListener;
import javax.speech.recognition.ResultToken;
import javax.speech.recognition.RuleGrammar;
import javax.speech.recognition.SpeakerManager;
import javax.speech.recognition.SpeakerProfile;

import org.apache.log4j.Logger;

import edwin.app.config.Configuration;
import edwin.core.service.Service;

public class SpeechRecognitionService implements Service, ResultListener {

	private static final Logger LOGGER = Logger
			.getLogger(SpeechRecognitionService.class);

	public void ready() {
		commitAndResume();
	}

	public synchronized void enable() {

		try {

			// TODO ACY DEBUG Choix du profile
			// RecognizerModeDesc desc = new
			// RecognizerModeDesc(null,Boolean.TRUE);
			// SpeechEngineChooser chooser =
			// SpeechEngineChooser.getRecognizerDialog(desc);
			// chooser.show();

			speechRecognizer = Central.createRecognizer(new EngineModeDesc(
					Locale.ROOT));

			// Add ResultListener
			speechRecognizer.addResultListener(this);

			speechRecognizer.allocate();
			speechRecognizer.waitEngineState(Recognizer.ALLOCATED);

			// Properties
			RecognizerProperties props = speechRecognizer
					.getRecognizerProperties();
			props.setNumResultAlternatives(5);
			props.setResultAudioProvided(true);

			// Set SpeakerProfile
			SpeakerManager speakerManager = speechRecognizer
					.getSpeakerManager();
			SpeakerProfile profile = new SpeakerProfile();
			SpeakerProfile[] profs = speakerManager.listKnownSpeakers();
			for (int i = 0; i < profs.length; i++) {
				profile = profs[i];
			}
			speakerManager.setCurrentSpeaker(profile);

			// Commit changes
			speechRecognizer.suspend();
			speechRecognizer.waitEngineState(Recognizer.SUSPENDED);
			speechRecognizer.commitChanges();
			speechRecognizer.waitEngineState(Recognizer.LISTENING);

			speechRecognizer.requestFocus();
			speechRecognizer.waitEngineState(Recognizer.FOCUS_ON);
			speechRecognizer.resume();
			speechRecognizer.waitEngineState(Recognizer.RESUMED);

			LOGGER.debug("Using profile "
					+ speakerManager.getCurrentSpeaker().getName());

		} catch (IllegalArgumentException e) {
			LOGGER.error(e, e);
		} catch (EngineException e) {
			LOGGER.error(e, e);
		} catch (SecurityException e) {
			LOGGER.error(e, e);
		} catch (InterruptedException e) {
			LOGGER.error(e, e);
		} catch (PropertyVetoException e) {
			LOGGER.error(e, e);
		} catch (NullPointerException e) {
			LOGGER.error(e, e);
		} catch (GrammarException e) {
			LOGGER.error(e, e);
		} catch (AudioException e) {
			LOGGER.error(e, e);
		} catch (EngineStateError e) {
			LOGGER.error(e, e);
		}
	}

	public synchronized void disable() {

		Recognizer recognizer = getRecognizer();

		try {
			recognizer.deallocate();
			recognizer.waitEngineState(Recognizer.DEALLOCATED);
		} catch (EngineException e) {
			LOGGER.error(e, e);
		} catch (EngineStateError e) {
			LOGGER.error(e, e);
		} catch (IllegalArgumentException e) {
			LOGGER.error(e, e);
		} catch (InterruptedException e) {
			LOGGER.error(e, e);
		}
	}

	public void addGrammar(String id, String grammar) {
		String[] grammars = new String[]{ grammar};
		addGrammar(id, grammars);
	}
	
	public void addGrammar(String id, String[] grammars) {

		Recognizer recognizer = getRecognizer();

		try {
			RuleGrammar newGrammar = recognizer.newRuleGrammar(id);
			String name = Configuration.getInstance().getBotName();
			for (String grammar : grammars) {
				newGrammar.setRule(id,
						newGrammar.ruleForJSGF(name + " " + grammar), true);				
			}
			newGrammar.setEnabled(true);
		} catch (IllegalArgumentException e) {
			LOGGER.error(e, e);
		} catch (NullPointerException e) {
			LOGGER.error(e, e);
		} catch (GrammarException e) {
			LOGGER.error(e, e);
		} catch (EngineStateError e) {
			LOGGER.error(e, e);
		}
	}

	public void pause() {

		Recognizer recognizer = getRecognizer();

		try {
			recognizer.suspend();
			recognizer.waitEngineState(Recognizer.SUSPENDED);
		} catch (InterruptedException e) {
			LOGGER.error(e, e);
		} catch (EngineStateError e) {
			LOGGER.error(e, e);
		}
	}

	private void commitAndResume() {

		Recognizer recognizer = getRecognizer();

		try {
			// Commit changes
			recognizer.suspend();
			recognizer.waitEngineState(Recognizer.SUSPENDED);
			recognizer.commitChanges();
			recognizer.waitEngineState(Recognizer.LISTENING);

			recognizer.requestFocus();
			recognizer.waitEngineState(Recognizer.FOCUS_ON);
			recognizer.resume();
			recognizer.waitEngineState(Recognizer.RESUMED);
		} catch (InterruptedException e) {
			LOGGER.error(e, e);
		} catch (AudioException e) {
			LOGGER.error(e, e);
		} catch (GrammarException e) {
			LOGGER.error(e, e);
		} catch (EngineStateError e) {
			LOGGER.error(e, e);
		}
	}

	public void removeGrammar(String id) {

		Recognizer recognizer = getRecognizer();

		try {
			RuleGrammar grammar = recognizer.getRuleGrammar(id);
			grammar.setEnabled(false);

			// Commit changes
			recognizer.suspend();
			recognizer.waitEngineState(Recognizer.SUSPENDED);
			recognizer.commitChanges();
			recognizer.waitEngineState(Recognizer.LISTENING);

			recognizer.requestFocus();
			recognizer.waitEngineState(Recognizer.FOCUS_ON);
			recognizer.resume();
			recognizer.waitEngineState(Recognizer.RESUMED);
		} catch (IllegalArgumentException e) {
			LOGGER.error(e, e);
		} catch (InterruptedException e) {
			LOGGER.error(e, e);
		} catch (NullPointerException e) {
			LOGGER.error(e, e);
		} catch (GrammarException e) {
			LOGGER.error(e, e);
		} catch (AudioException e) {
			LOGGER.error(e, e);
		} catch (EngineStateError e) {
			LOGGER.error(e, e);
		}
	}

	public void waitWhileEnabled() {
		Recognizer recognizer = getRecognizer();

		try {
			recognizer.waitEngineState(Recognizer.DEALLOCATED);
		} catch (EngineStateError e) {
			LOGGER.error(e, e);
		} catch (IllegalArgumentException e) {
			LOGGER.error(e, e);
		} catch (InterruptedException e) {
			LOGGER.error(e, e);
		}
	}

	/**
	 * Lazy initialize the recognizer.
	 */
	private synchronized Recognizer getRecognizer() {

		if (speechRecognizer == null) {
			enable();
		}

		return speechRecognizer;
	}

	public void audioReleased(ResultEvent e) {
	}

	public void grammarFinalized(ResultEvent e) {
	}

	public void resultAccepted(ResultEvent e) {

		Recognizer recognizer = getRecognizer();

		try {
			FinalRuleResult res = (FinalRuleResult) (e.getSource());

			// Pause the recognizer to avoid interferences
			recognizer.pause();
			recognizer.waitEngineState(Recognizer.PAUSED);

			// We get the best tokens
			ResultToken tokens[] = res.getBestTokens();

			// Get the sentence
			String sentence = "";
			String currentToken = "";
			for (int i = 0; i < tokens.length; i++) {
				currentToken = tokens[i].getSpokenText();
				sentence += currentToken + " ";
			}
			sentence = sentence.trim();

			// Remove the name
			String name = Configuration.getInstance().getBotName();
			sentence = sentence.replaceFirst(name, "");
			sentence = sentence.trim();

			// Read the audio
			// java.applet.AudioClip clip = null;
			// if (tokens != null && tokens.length > 0) {
			// clip = res.getAudio(tokens[0], tokens[tokens.length - 1]);
			// if (clip != null) {
			// clip.play();
			// }
			// } else {
			// System.out.println("Audio clip is null - can't play");
			// }

			// Fire new result
			String grammarName = res.getGrammar().getName();
			String[] tags = res.getTags();
			LOGGER.debug("Grammar : " + grammarName + " | Sentence : "
					+ sentence);
			fireNewResult(grammarName, sentence, tags);

			// Release audio so it doesn't waste space
			res.releaseAudio();

			// Resume the service
			recognizer.resume();
			recognizer.waitEngineState(Recognizer.RESUMED);

		} catch (EngineStateError ex) {
			LOGGER.error(ex, ex);
		} catch (AudioException ex) {
			LOGGER.error(ex, ex);
		} catch (IllegalArgumentException ex) {
			LOGGER.error(ex, ex);
		} catch (InterruptedException ex) {
			LOGGER.error(ex, ex);
		}
	}

	public void resultCreated(ResultEvent e) {
	}

	public void resultRejected(ResultEvent e) {
	}

	public void resultUpdated(ResultEvent e) {
	}

	public void trainingInfoReleased(ResultEvent e) {
	}

	public void addListener(SpeechRecognitionListener listener) {
		listeners.add(listener);
	}

	public void removeListener(SpeechRecognitionListener listener) {
		listeners.remove(listener);
	}

	private void fireNewResult(String grammarName, String sentence,
			String[] tags) {
		for (SpeechRecognitionListener listener : listeners) {
			listener.newRecognition(grammarName, sentence, tags);
		}
	}

	/**
	 * Recognizer.
	 */
	private Recognizer speechRecognizer = null;

	/**
	 * Listeners.
	 */
	private Set<SpeechRecognitionListener> listeners = new HashSet<SpeechRecognitionListener>();

}
