package edwin.speech.service;

import java.util.Locale;

import javax.speech.AudioException;
import javax.speech.Central;
import javax.speech.EngineException;
import javax.speech.EngineStateError;
import javax.speech.synthesis.JSMLException;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;
import javax.speech.synthesis.SynthesizerProperties;
import javax.speech.synthesis.Voice;

import org.apache.log4j.Logger;

import edwin.core.service.Service;

public class SpeechService implements Service {

	private static final Logger LOGGER = Logger.getLogger(SpeechService.class);

	private static SpeechService s_Instance = null;
	
	public static final synchronized SpeechService getInstance(){
		if(s_Instance == null){
			s_Instance = new SpeechService();
		}
		
		return s_Instance;
	}
	
	public synchronized void enable() {

		try {
			// SynthesizerModeDesc desc = null;
			// SpeechEngineChooser chooser = null;
			// try {
			// chooser = SpeechEngineChooser.getSynthesizerDialog();
			// chooser.show();
			// desc = chooser.getSynthesizerModeDesc();
			// } catch (NoClassDefFoundError e) {
			// System.out
			// .println("Can't find Swing - try using Java 2 to see the SpeechEngineChooser");
			// }
			// s_Synthesizer = Central.createSynthesizer(desc);
			speechSynthesizer = Central.createSynthesizer(new SynthesizerModeDesc(
					Locale.ROOT));

			((com.cloudgarden.speech.CGEngineProperties) speechSynthesizer
					.getSynthesizerProperties()).setEventsInNewThread(false);

			speechSynthesizer.allocate();
			speechSynthesizer.waitEngineState(Synthesizer.ALLOCATED);

			Voice v = new Voice(null, Voice.GENDER_DONT_CARE, Voice.AGE_DONT_CARE,
					null);

			SynthesizerProperties properties = speechSynthesizer
					.getSynthesizerProperties();
			properties.setVoice(v);
			properties.setVolume(1.0f);
			properties.setSpeakingRate(200.0f);

			LOGGER.debug("Using voice " + v);
			
		} catch (IllegalArgumentException e) {
			LOGGER.error(e, e);
		} catch (EngineException e) {
			LOGGER.error(e, e);
		} catch (EngineStateError e) {
			LOGGER.error(e,e);
		} catch (InterruptedException e) {
			LOGGER.error(e,e);
		}
	}

	public synchronized void disable() {
		Synthesizer synthesizer = getSynthesizer();

		try {
			synthesizer.deallocate();
			synthesizer.waitEngineState(Synthesizer.DEALLOCATED);
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

	public void say(String message) {

		Synthesizer synthesizer = getSynthesizer();

		try {
			synthesizer.resume();
			synthesizer.waitEngineState(Synthesizer.RESUMED);

			synthesizer.speak(message, null);
			synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);

			synthesizer.pause();
			synthesizer.waitEngineState(Synthesizer.PAUSED);
		} catch (AudioException e) {
			LOGGER.error(e, e);
		} catch (EngineStateError e) {
			LOGGER.error(e, e);
		} catch (IllegalArgumentException e) {
			LOGGER.error(e, e);
		} catch (InterruptedException e) {
			LOGGER.error(e, e);
		} catch (JSMLException e) {
			LOGGER.error(e, e);
		}
	}

	/**
	 * Lazy initialize the synthesizer.
	 */
	private synchronized Synthesizer getSynthesizer() {

		if (speechSynthesizer == null) {
			enable();
		}

		return speechSynthesizer;
	}
	

	/**
	 * Synthesizer.
	 */
	private Synthesizer speechSynthesizer = null;

}
