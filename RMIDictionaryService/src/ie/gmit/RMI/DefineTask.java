package ie.gmit.RMI;
/**
 * 
 * @author rndmized
 * Define task search and attach definition to word.
 */
public class DefineTask implements Runnable {

	private Definition definition;
	private Dictionary dict;
		
	public DefineTask(Definition def, Dictionary dict) {
		super();
		this.definition = def;
		this.dict = dict;
	}

	@Override
	public void run() {
		try {
			definition.setWordDefinition(dict.getWordDefinition(definition.getWord()));
			Thread.sleep(1000);
			definition.setProcessed();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
