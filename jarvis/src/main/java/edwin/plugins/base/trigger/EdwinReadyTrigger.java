package edwin.plugins.base.trigger;

import edwin.app.Edwin;
import edwin.app.EdwinListener;
import edwin.core.trigger.AbstractTrigger;
import edwin.core.variable.VariableRepository;

public class EdwinReadyTrigger extends AbstractTrigger implements EdwinListener {

	@Override
	protected void enableImpl() {
		getEdwin().addListener(this);
	}

	@Override
	protected void disableImpl() {
		getEdwin().removeListener(this);
	}

	public void edwinReady(Edwin edwin) {
		VariableRepository localVars = new VariableRepository();
		setTriggered(true, localVars);
		setTriggered(false, localVars);
	}

}
