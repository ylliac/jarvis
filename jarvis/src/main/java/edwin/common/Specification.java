package edwin.common;

public interface Specification<TYPE> {

	boolean accept(TYPE object);
	
}
