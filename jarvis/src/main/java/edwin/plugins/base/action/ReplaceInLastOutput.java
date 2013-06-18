package edwin.plugins.base.action;

import edwin.app.Edwin;
import edwin.core.action.Action;
import edwin.core.variable.InternVariables;
import edwin.core.variable.VariableRepository;

public class ReplaceInLastOutput implements Action {

	public ReplaceInLastOutput(String regex, String replacement) {
		this.regex = regex;
		this.replacement = replacement;
	}
	
	public void execute(VariableRepository localVars, Edwin edwin) {
		String result = InternVariables.getOutput(localVars);
		
		result = result.replaceAll(regex, replacement);
		
		InternVariables.setOutput(localVars, result);		
	}

	private String regex;
	
	private String replacement;
}
