package edwin.core.trigger;

import edwin.common.VariableRepository;

public interface TriggerListener {

	void triggered(boolean triggered, VariableRepository localVars, Trigger source);
	
}
