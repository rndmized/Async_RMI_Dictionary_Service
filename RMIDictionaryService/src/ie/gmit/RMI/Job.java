package ie.gmit.RMI;
/**
 * 
 * @author rndmized
 * Job task class
 */
public class Job {
	
	private String taskNumber;
	private Definition def;
	
	public Job(){
		super();
	}
	
	public Job(String taskNumber, Definition def) {
		super();
		this.taskNumber = taskNumber;
		this.def = def;
	}

	public String getTaskNumber() {
		return taskNumber;
	}

	public void setTaskNumber(String taskNumber) {
		this.taskNumber = taskNumber;
	}

	public Definition getDef() {
		return def;
	}

	public void setDef(Definition def) {
		this.def = def;
	}

	
	


}

