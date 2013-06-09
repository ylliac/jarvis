package edwin.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class RepositoryInMemory<KEY, VALUE> implements
		Repository<VALUE> {

	public void add(VALUE object) {
		KEY key = getKeyFromObject(object);
		m_Objects.put(key, object);
	}

	public void addAll(Collection<VALUE> objects) {
		for (VALUE object : objects) {
			add(object);
		}
	}

	public void clear() {
		m_Objects.clear();
	}

	public void remove(VALUE object) {
		KEY key = getKeyFromObject(object);
		m_Objects.remove(key);
	}

	public void removeAll(Collection<VALUE> objects) {
		for (VALUE object : objects) {
			remove(object);
		}
	}

	public Collection<VALUE> findAll() {
		return m_Objects.values();
	}
	
	public VALUE findFromKey(KEY key) {
		return m_Objects.get(key);
	}

	public Collection<VALUE> findIf(Specification<VALUE> specification) {

		List<VALUE> result = new ArrayList<VALUE>();

		Collection<VALUE> allObjects = findAll();
		for (VALUE object : allObjects) {
			if (specification.accept(object)) {
				result.add(object);
			}
		}

		return result;
	}

	protected abstract KEY getKeyFromObject(VALUE object);

	/**
	 * Objects.
	 */
	private Map<KEY, VALUE> m_Objects = new HashMap<KEY, VALUE>();

}
