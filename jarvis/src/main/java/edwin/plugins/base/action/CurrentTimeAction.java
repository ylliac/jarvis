package edwin.plugins.base.action;

import java.util.Calendar;

import edwin.app.Edwin;
import edwin.core.action.Action;
import edwin.core.variable.InternVariables;
import edwin.core.variable.VariableRepository;

public class CurrentTimeAction implements Action {

	public void execute(VariableRepository localVars, Edwin edwin) {
		
		Calendar calendar = Calendar.getInstance();
    	String reply = "Il est " + calendar.get(Calendar.HOUR_OF_DAY) + " heures, " + calendar.get(Calendar.MINUTE) + " minutes et " + calendar.get(Calendar.SECOND) + " secondes";
		
		//Put the answer in the last output
		InternVariables.setOutput(localVars, reply);
	}

}
