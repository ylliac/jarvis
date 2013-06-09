package edwin.core.rule;

import org.apache.log4j.Logger;

import edwin.common.VariableRepository;
import edwin.core.action.Action;
import edwin.core.trigger.Trigger;
import edwin.core.trigger.TriggerListener;

public class Rule implements TriggerListener {

	private static final Logger LOGGER = Logger.getLogger(Rule.class);

	public Rule(String name, Trigger trigger, Action action) {
		this.name = name;
		this.trigger = trigger;
		this.action = action;
	}

	public void triggered(boolean triggered, VariableRepository localVars,
			Trigger source) {
		if (triggered && source == trigger) {

			// Check if the command variable is set
			if (localVars.findFromKey(Trigger.COMMAND_VARIABLE) == null) {
				throw new IllegalStateException("Command variable ("
						+ Trigger.COMMAND_VARIABLE
						+ ") must be set by the trigger");
			}
			
			LOGGER.info("Rule " + getName() + " triggered");

			action.execute(localVars);
		}
	}

	public void enable() {
		trigger.enable();
		trigger.addListener(this);
	}

	public void disable() {
		trigger.removeListener(this);
		trigger.disable();
	}
	
	public String getName() {
		return name;
	}

	/**
	 * Action.
	 */
	private Action action = null;

	/**
	 * Trigger.
	 */
	private Trigger trigger = null;

	/**
	 * Rule's name.
	 */
	private String name = "";

}
