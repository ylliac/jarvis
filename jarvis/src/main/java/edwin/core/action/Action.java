package edwin.core.action;

import edwin.app.Edwin;
import edwin.core.variable.VariableRepository;

public interface Action {
	
	void execute(VariableRepository localVars, Edwin edwin);

}
