package edwin.plugins.speech.service;

public interface SpeechRecognitionListener {

	void newRecognition(String grammarName, String sentence, String[] tags);
	
}
