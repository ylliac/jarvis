package edwin.core.trigger;

public interface Trigger {

	/**
	 * Name of the local variable containing the trigger command text.
	 */
	String COMMAND_VARIABLE = "_command";
	
	/**
	 * Enable the trigger.
	 */
	void enable();

	/**
	 * Disable the trigger.
	 */
	void disable();

	/**
	 * Listen the trigger.
	 */
	void addListener(TriggerListener listener);

	/**
	 * Unlisten the trigger.
	 */
	void removeListener(TriggerListener listener);
	
	/**
	 * Return wether the trigger is triggered or not.
	 */
	boolean isTriggered();
}
