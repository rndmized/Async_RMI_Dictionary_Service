package ie.gmit.RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 
 * @author rndmized
 * Definition Interface extending remote interface
 */
public interface Definition extends Remote{
	
	public String getWord() throws RemoteException;
	public String getWordDefinition() throws RemoteException;
	public void setWordDefinition(String definition) throws RemoteException;
	public boolean isProcessed() throws RemoteException;
	public void setProcessed() throws RemoteException;

}
