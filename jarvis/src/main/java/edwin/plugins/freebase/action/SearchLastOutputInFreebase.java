package edwin.plugins.freebase.action;

import edwin.app.Edwin;
import edwin.core.action.Action;
import edwin.core.variable.InternVariables;
import edwin.core.variable.VariableRepository;
import edwin.plugins.freebase.service.FreebaseService;

public class SearchLastOutputInFreebase implements Action {

	public void execute(VariableRepository localVars, Edwin edwin) {
		String query = InternVariables.getOutput(localVars);

		FreebaseService service = edwin.getService(FreebaseService.class);

		String result = service.search(query);
		InternVariables.setOutput(localVars, result);
	}

}
