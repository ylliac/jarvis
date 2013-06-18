package edwin.core.trigger;

import edwin.app.Edwin;

public interface Trigger {
	
	/**
	 * Enable the trigger.
	 */
	void enable(Edwin edwin);

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
	
	/**
	 * Get Edwin.
	 */
	Edwin getEdwin();
}
