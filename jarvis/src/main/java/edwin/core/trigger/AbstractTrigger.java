package edwin.core.trigger;

import java.util.HashSet;
import java.util.Set;

import edwin.common.IDGenerator;
import edwin.common.VariableRepository;


public abstract class AbstractTrigger implements Trigger {
	
	public void addListener(TriggerListener listener){
		listeners.add(listener);
	}
	
	public void removeListener(TriggerListener listener){
		listeners.remove(listener);
	}
	
	public long getId() {
		return id;
	}
	
	protected void fireTriggered(VariableRepository localVars){
		for (TriggerListener listener : listeners) {
			listener.triggered(triggered, localVars, this);
		}
	}
	
	public boolean isTriggered() {
		return triggered;
	}
	
	protected void setTriggered(boolean triggered, VariableRepository localVars) {
		this.triggered = triggered;
		
		fireTriggered(localVars);
	}
	
	private boolean triggered = false;
	
	private Set<TriggerListener> listeners = new HashSet<TriggerListener>();
	
	private long id = IDGenerator.newID();	
}
