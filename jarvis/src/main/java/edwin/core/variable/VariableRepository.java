package edwin.core.variable;

import edwin.common.RepositoryInMemory;

public class VariableRepository extends RepositoryInMemory<String, Variable<?>> {

	@Override
	protected String getKeyFromObject(Variable<?> var) {
		return var.getName();
	}
	
}
