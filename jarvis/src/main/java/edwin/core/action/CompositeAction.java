package edwin.core.action;

import edwin.app.Edwin;
import edwin.core.variable.VariableRepository;

public class CompositeAction implements Action {

	public CompositeAction(Action... actions) {
		this.actions = actions;
	}
	
	public void execute(VariableRepository localVars, Edwin edwin) {
		for (Action action : actions) {
			action.execute(localVars, edwin);
		}
	}
	
	private Action[] actions = null; 
}
