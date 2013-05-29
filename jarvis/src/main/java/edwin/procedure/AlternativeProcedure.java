package edwin.procedure;

import edwin.common.Predicate;

public class AlternativeProcedure implements Procedure {

	public AlternativeProcedure() {
	}
	
	public Predicate getPredicate() {
		return predicate;
	}
	
	public void setPredicate(Predicate predicate) {
		this.predicate = predicate;
	}
	
	public Procedure getProcedureIfTrue() {
		return procedureIfTrue;
	}

	public void setProcedureIfTrue(Procedure procedureIfTrue) {
		this.procedureIfTrue = procedureIfTrue;
	}

	public Procedure getProcedureIfFalse() {
		return procedureIfFalse;
	}

	public void setProcedureIfFalse(Procedure procedureIfFalse) {
		this.procedureIfFalse = procedureIfFalse;
	}

	public void execute() {
		if(predicate.isTrue()){
			procedureIfTrue.execute();
		}
		else{
			procedureIfFalse.execute();
		}
	}

	private Predicate predicate;
	
	private Procedure procedureIfTrue;
	
	private Procedure procedureIfFalse;
}
