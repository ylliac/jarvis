package edwin.procedure;

import java.util.List;

public class ProcedureSequence implements Procedure {

	public ProcedureSequence() {
	}
	
	public List<Procedure> getSequence() {
		return sequence;
	}
	
	public void setSequence(List<Procedure> sequence) {
		this.sequence = sequence;
	}
	
	public void execute() {
		for (Procedure procedure : sequence) {
			procedure.execute();
		}
	}

	private List<Procedure> sequence;
}
