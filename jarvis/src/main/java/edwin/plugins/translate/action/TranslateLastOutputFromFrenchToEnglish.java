package edwin.plugins.translate.action;

import com.memetix.mst.language.Language;

import edwin.app.Edwin;
import edwin.core.action.Action;
import edwin.core.variable.InternVariables;
import edwin.core.variable.VariableRepository;
import edwin.plugins.translate.service.TranslateService;

public class TranslateLastOutputFromFrenchToEnglish implements Action {

	public void execute(VariableRepository localVars, Edwin edwin) {

		TranslateService service = edwin.getService(TranslateService.class);

		String result = service.translate(InternVariables.getOutput(localVars),
				Language.FRENCH, Language.ENGLISH);
		InternVariables.setOutput(localVars, result);
	}

}
