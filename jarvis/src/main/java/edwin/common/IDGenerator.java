package edwin.common;

public class IDGenerator {

	public static synchronized long newID(){
		return s_CurrentID++;
	}
	
	private static long s_CurrentID = 0;
}
