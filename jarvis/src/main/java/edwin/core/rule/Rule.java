package edwin.core.rule;

import org.apache.log4j.Logger;

import edwin.app.Edwin;
import edwin.core.action.Action;
import edwin.core.action.CompositeAction;
import edwin.core.trigger.Trigger;
import edwin.core.trigger.TriggerListener;
import edwin.core.variable.VariableRepository;

public class Rule implements TriggerListener {

	private static final Logger LOGGER = Logger.getLogger(Rule.class);

	public Rule(String name, Trigger trigger, Action action) {
		this.name = name;
		this.trigger = trigger;
		this.action = action;
	}
	
	public Rule(String name, Trigger trigger, Action... actions) {
		this.name = name;
		this.trigger = trigger;
		CompositeAction compositeAction = new CompositeAction(actions);
		this.action = compositeAction;
	}

	public void triggered(boolean triggered, VariableRepository localVars,
			Trigger source) {
		if (triggered && source == trigger) {
			
			LOGGER.info("Rule " + getName() + " triggered");

			action.execute(localVars, trigger.getEdwin());
		}
	}

	public void enable(Edwin edwin) {
		trigger.enable(edwin);
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
