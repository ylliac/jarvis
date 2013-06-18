package edwin.plugins.base.action;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.apache.log4j.Logger;

import edwin.app.Edwin;
import edwin.core.action.Action;
import edwin.core.variable.VariableRepository;

public class PauseAction implements Action {

	private static final Logger LOGGER = Logger.getLogger(PauseAction.class);

	public void execute(VariableRepository localVars, Edwin edwin) {
		int event = KeyEvent.VK_SPACE;

		try {
			Robot robot = new Robot();
			robot.keyPress(event);
			robot.keyRelease(event);
		} catch (AWTException e) {
			LOGGER.error(e, e);
		}
	}

}
