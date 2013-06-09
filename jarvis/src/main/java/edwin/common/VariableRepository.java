package edwin.common;

public class VariableRepository extends RepositoryInMemory<String, Variable<?>> {

	@Override
	protected String getKeyFromObject(Variable<?> var) {
		return var.getName();
	}
	
}
