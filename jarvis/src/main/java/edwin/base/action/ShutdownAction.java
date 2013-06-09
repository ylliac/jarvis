package edwin.base.action;

import edwin.common.VariableRepository;
import edwin.core.action.Action;

public class ShutdownAction implements Action {

	public void execute(VariableRepository localVars) {
		System.exit(0);
	}

}
