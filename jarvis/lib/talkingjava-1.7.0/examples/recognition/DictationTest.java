package examples.recognition;

import examples.*;
import com.cloudgarden.speech.userinterface.*;
import javax.speech.*;
import javax.speech.recognition.*;
import java.io.*;

/**
 * Tests out basic dictation from the default audio device (usually the microphone) -
 * also demonstrates getting the list of speaker profiles and setting the current speaker.
 */
public class DictationTest {
    static Recognizer rec = null;

    public static void main(String[] args) {
	try {
            RecognizerModeDesc desc = new RecognizerModeDesc(null,Boolean.TRUE);
            SpeechEngineChooser chooser = SpeechEngineChooser.getRecognizerDialog(desc);
            chooser.show();
            desc = chooser.getRecognizerModeDesc();

            rec = Central.createRecognizer(desc);
	    rec.addEngineListener(new TestEngineListener());
	    rec.addResultListener(new TestResultListener(rec,3,true));
	    RecognizerAudioAdapter raud = new TestAudioListener();
	    rec.getAudioManager().addAudioListener(raud);

	    rec.allocate();

	    rec.waitEngineState(Recognizer.ALLOCATED);
	    RecognizerProperties props = rec.getRecognizerProperties();
	    props.setNumResultAlternatives(5);

            //Test out RecogniserProperties settings - really only reliable for SAPI4 engines
            try {
                props.setSensitivity(0.5f); //def=0
                props.setSpeedVsAccuracy(0.5f); //def = 1
                props.setConfidenceLevel(0.3f); //def = 0.5
                props.setCompleteTimeout(0.3f); //def = 0.15
                props.setIncompleteTimeout(1.0f); //def = 0.5
                
                System.out.println("Recognizer settings\n===========");
                System.out.println("Sensitivity = "+props.getSensitivity());
                System.out.println("SpeedVsAccuracy = "+props.getSpeedVsAccuracy());
                System.out.println("ConfidenceLevel = "+props.getConfidenceLevel());
                System.out.println("CompleteTimeout = "+props.getCompleteTimeout());
                System.out.println("IncompleteTimeout = "+props.getIncompleteTimeout()+"\n");
            } catch(java.beans.PropertyVetoException ex) {
                ex.printStackTrace();
            }

            //Retain audio so it can be played back later (see TestResultListener)
            props.setResultAudioProvided(true);

            SpeakerManager speakerManager = rec.getSpeakerManager();
            
            //list all the speaker profiles and set the current profile to the first one in the list
            SpeakerProfile[] profs = speakerManager.listKnownSpeakers();
            for(int i=0;i<profs.length; i++) {
                System.out.println("Found Profile "+i+" = "+profs[i].getName());
            }
            
            SpeakerProfile prof = chooser.getSpeakerProfile();
            speakerManager.setCurrentSpeaker(prof);
            System.out.println("Current Profile set to "+speakerManager.getCurrentSpeaker().getName());
            
            //let the engine decide who is speaking (only works for SAPI4 engines)
            ((com.cloudgarden.speech.CGEngineProperties)props).allowGuessingOfSpeaker(true);
            
            DictationGrammar dictation;
            dictation = rec.getDictationGrammar("dictation");
	    dictation.setEnabled(true);
	    
	    rec.suspend();
	    rec.commitChanges();
	    rec.waitEngineState(rec.LISTENING);
	    
	    rec.requestFocus();
	    rec.resume();

	    rec.waitEngineState(Recognizer.DEALLOCATED); 
	    //three recognitions and the TestResultListener will deallocate the recognizer

	} catch(Exception e) {
	    e.printStackTrace(System.out);
	} catch(Error e1) {
	    e1.printStackTrace(System.out);
	} finally {
	    try {
		rec.deallocate();
	    } catch(Exception e2) {
		e2.printStackTrace(System.out);
	    }
	    System.exit(0);
	}
    }
}
