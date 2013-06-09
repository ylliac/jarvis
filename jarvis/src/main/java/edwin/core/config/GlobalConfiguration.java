package edwin.core.config;

public class GlobalConfiguration {

	private static GlobalConfiguration s_Instance = null;
	
	public static synchronized GlobalConfiguration getInstance(){
		if(s_Instance == null){
			s_Instance = new GlobalConfiguration();
		}
		
		return s_Instance;
	}
	
	private GlobalConfiguration(){
		name = "Maxime";
	}
	
	public String getName() {
		return name;
	}
	
	private String name;	
}
