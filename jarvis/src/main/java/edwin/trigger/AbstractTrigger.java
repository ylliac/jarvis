package edwin.trigger;

import java.util.Set;


public abstract class AbstractTrigger implements Trigger {

	
	public void addListener(TriggerListener listener){
		listeners.add(listener);
	}
	
	public void removeListener(TriggerListener listener){
		listeners.remove(listener);
	}
	
	protected void fireTrigged(){
		for (TriggerListener listener : listeners) {
			listener.triggered(this);
		}
	}
	
	private Set<TriggerListener> listeners;
}
