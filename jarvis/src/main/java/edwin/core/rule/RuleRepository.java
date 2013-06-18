package edwin.core.rule;

import edwin.common.RepositoryInMemory;

public class RuleRepository extends RepositoryInMemory<String, Rule> {

	@Override
	protected String getKeyFromObject(Rule object) {
		return object.getName();
	}

}
