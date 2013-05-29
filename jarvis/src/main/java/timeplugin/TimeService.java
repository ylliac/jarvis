package timeplugin;

import java.util.Calendar;

//TODO Annotation Entity
public class TimeService {

	//TODO Annotation action
	public String humanTime(){		
		Calendar time = Calendar.getInstance();
		int hour = time.get(Calendar.HOUR);
		int minute = time.get(Calendar.MINUTE);
		
		String result = "Il est " + hour + " heures et " + minute + " minutes";		
		return result;
	}
	
}
