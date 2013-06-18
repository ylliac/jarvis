package edwin.core.variable;

public final class InternVariables {

	/**
	 * Last output.
	 */
	public static final String OUTPUT = "_output";
	
	/**
	 * Get the last output.
	 */
	public static final String getOutput(VariableRepository repository){
		Variable<?> variable = repository.findFromKey(OUTPUT);
		String result;
		
		if(variable == null){
			result = "null";
		}
		else{
			result = variable.toString();
		}
		
		return result;
	}
	
	/**
	 * Set the last output.
	 */
	public static final void setOutput(VariableRepository repository, String value){
		Variable<String> variable = new Variable<String>(OUTPUT, value);
		repository.add(variable);
	}
	
}
