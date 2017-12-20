package ie.gmit.RMI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
/** 
 * Implementation of Dictionary Service
 * */
public class DictionaryServiceImpl extends UnicastRemoteObject implements DictionaryService {

	private static final long serialVersionUID = 1L;

	public DictionaryServiceImpl() throws RemoteException {
		super();
	}

	@Override
	public Definition lookUp(String word) throws RemoteException {
		// create instance of Definition passing in word to define
		Definition def = new DefinitionImpl(word);
		// Call in task to find definition for such definition
		new Thread(new DefineTask(def, new Dictionary("dictionary.csv"))).start();
		// Return definition
		return def;
	}

}
