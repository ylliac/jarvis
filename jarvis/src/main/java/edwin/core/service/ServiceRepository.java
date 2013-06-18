package edwin.core.service;

import edwin.common.RepositoryInMemory;

public class ServiceRepository extends RepositoryInMemory<Class<? extends Service>, Service> {

	@Override
	protected Class<? extends Service> getKeyFromObject(Service object) {
		return object.getClass();
	}

}
