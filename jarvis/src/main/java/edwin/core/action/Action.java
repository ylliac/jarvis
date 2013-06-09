package edwin.core.action;

import edwin.common.VariableRepository;

public interface Action {
	
	void execute(VariableRepository localVars);

}
