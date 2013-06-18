package edwin.core.service;

public interface Service {
		
	/**
	 * Enable the service.
	 */
	void enable();

	/**
	 * Disable the service.
	 */
	void disable();
	
	/**
	 * Refresh the service until it is ready.
	 */
	void ready();
	
}
