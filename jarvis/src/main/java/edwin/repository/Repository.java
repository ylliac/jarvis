package edwin.repository;

import java.util.Collection;
import java.util.List;

import edwin.common.Specification;

public interface Repository<TYPE> {

	void add(TYPE object);
	
	void addAll(Collection<TYPE> objects);
	
	void clear();
	
	void remove(TYPE object);
	
	void removeAll(Collection<TYPE> objects);
	
	List<TYPE> findAll();
	
	List<TYPE> findIf(Specification<TYPE> specification);
	
}
