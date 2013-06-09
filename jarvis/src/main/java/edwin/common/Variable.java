package edwin.common;

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
	
	private TYPE value = null;
	
	private String name = null;	
}
