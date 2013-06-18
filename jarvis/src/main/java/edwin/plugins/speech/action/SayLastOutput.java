package edwin.plugins.speech.action;

import edwin.core.variable.InternVariables;

public class SayLastOutput extends SpeechAction {

	public SayLastOutput() {
		super("%" + InternVariables.OUTPUT);		
	}	
	
}
