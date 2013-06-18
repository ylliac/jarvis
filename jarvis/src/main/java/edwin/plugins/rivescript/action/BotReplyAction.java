package edwin.plugins.rivescript.action;

import edwin.app.Edwin;
import edwin.core.action.Action;
import edwin.core.variable.InternVariables;
import edwin.core.variable.VariableRepository;
import edwin.plugins.rivescript.service.RiveScriptService;

public class BotReplyAction implements Action {

	public void execute(VariableRepository localVars, Edwin edwin) {
		//Get the last output
		String output = InternVariables.getOutput(localVars);
		
		//Give it to the bot and get the answer
		RiveScriptService service = edwin.getService(RiveScriptService.class);
		String reply = service.reply(output);
		
		//Put the answer in the last output
		InternVariables.setOutput(localVars, reply);
	}

}
