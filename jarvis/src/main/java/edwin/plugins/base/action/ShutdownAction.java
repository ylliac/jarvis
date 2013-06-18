package edwin.plugins.base.action;

import edwin.app.Edwin;
import edwin.core.action.Action;
import edwin.core.variable.VariableRepository;

public class ShutdownAction implements Action {

	public void execute(VariableRepository localVars, Edwin edwin) {
		edwin.stop();
	}

}
