package ie.gmit.RMI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * 
 * @author rndmized
 * Definition interface implementation extending Unicast Remote object
 *
 */
public class DefinitionImpl extends UnicastRemoteObject implements Definition {
	
	//Instance variables
		private static final long serialVersionUID = 1L;
		private String word;
		private String definition;
		private boolean isProcessed = false;

	protected DefinitionImpl() throws RemoteException {
		super();
	}

	public DefinitionImpl(String word) throws RemoteException {
		super();
		this.word = word;
	}


	@Override
	public void setProcessed() throws RemoteException {
		this.isProcessed = true;
		
	}

	@Override
	public String getWordDefinition() throws RemoteException {
		return this.definition;
	}

	@Override
	public void setWordDefinition(String definition) throws RemoteException {
		this.definition = definition;
		
	}

	@Override
	public boolean isProcessed() throws RemoteException {
		return isProcessed;
	}

	@Override
	public String getWord() throws RemoteException {
		return this.word;
	}
	
	
	


}
