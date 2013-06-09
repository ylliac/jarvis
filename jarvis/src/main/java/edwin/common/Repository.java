package edwin.common;

import java.util.Collection;
import java.util.List;


public interface Repository<VALUE> {

	void add(VALUE object);
	
	void addAll(Collection<VALUE> objects);
	
	void clear();
	
	void remove(VALUE object);
	
	void removeAll(Collection<VALUE> objects);
	
	Collection<VALUE> findAll();
	
	Collection<VALUE> findIf(Specification<VALUE> specification);
	
}
