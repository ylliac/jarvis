package edwin.speech.service;

public interface SpeechRecognitionListener {

	void newRecognition(String grammarName, String sentence, String[] tags);
	
}
