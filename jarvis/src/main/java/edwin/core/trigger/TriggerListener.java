package edwin.core.trigger;

import edwin.core.variable.VariableRepository;

public interface TriggerListener {

	void triggered(boolean triggered, VariableRepository localVars,
			Trigger source);

}
