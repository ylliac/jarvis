package edwin.core.variable;

public class Variable<TYPE> {
	
	public Variable(String name, TYPE value) {
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	
	public TYPE getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		String result = "";
		
		if(value instanceof Integer){
			result = Integer.toString((Integer) value);
		}
		else if(value instanceof Long){
			result = Long.toString((Long) value);
		} 
		else if(value instanceof Double){
			result = Double.toString((Double) value);
		} 
		else if(value instanceof Float){
			result = Float.toString((Float) value);
		} 
		else if(value instanceof Short){
			result = Short.toString((Short) value);
		} 
		else if(value instanceof String){
			result = ((String) value).toString();
		} 
		
		return result;
	}
	
	private TYPE value = null;
	
	private String name = null;	
}
