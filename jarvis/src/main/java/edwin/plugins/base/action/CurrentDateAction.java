package edwin.plugins.base.action;

import java.util.Calendar;

import edwin.app.Edwin;
import edwin.core.action.Action;
import edwin.core.variable.InternVariables;
import edwin.core.variable.VariableRepository;

public class CurrentDateAction implements Action {

	public void execute(VariableRepository localVars, Edwin edwin) {
		
		Calendar calendar = Calendar.getInstance();
		
		//Get the day
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		String day = "";
		switch (dayOfWeek) {
		case Calendar.SUNDAY:
			day = "Dimanche";
			break;
		case Calendar.MONDAY:
			day = "Lundi";
			break;
		case Calendar.TUESDAY:
			day = "Mardi";
			break;
		case Calendar.WEDNESDAY:
			day = "Mercredi";
			break;
		case Calendar.THURSDAY:
			day = "Jeudi";
			break;
		case Calendar.FRIDAY:
			day = "Vendredi";
			break;
		case Calendar.SATURDAY:
			day = "Samedi";
			break;
		}
		
		//Get the month
		int monthIndex = calendar.get(Calendar.MONTH);
		String month = "";
		switch (monthIndex) {
		case Calendar.JANUARY:
			month = "Janvier";
			break;
		case Calendar.FEBRUARY:
			month = "Fevrier";
			break;
		case Calendar.MARCH:
			month = "Mars";
			break;
		case Calendar.APRIL:
			month = "Avril";
			break;
		case Calendar.MAY:
			month = "Mai";
			break;
		case Calendar.JUNE:
			month = "Juin";
			break;
		case Calendar.JULY:
			month = "Juillet";
			break;
		case Calendar.AUGUST:
			month = "Aout";
			break;
		case Calendar.SEPTEMBER:
			month = "Septembre";
			break;
		case Calendar.OCTOBER:
			month = "Octobre";
			break;
		case Calendar.NOVEMBER:
			month = "Novembre";
			break;
		case Calendar.DECEMBER:
			month = "Decembre";
			break;
		}
    	
    	String reply = "Nous sommes " + day + " " + calendar.get(Calendar.DAY_OF_MONTH) + " " + month;
		
		//Put the answer in the last output
		InternVariables.setOutput(localVars, reply);
	}

}
