package ie.gmit.RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;
/**
 * 
 * @author rndmized
 * Dictionary Service Interface extending Remote
 */
public interface DictionaryService extends Remote {
	
	public Definition lookUp(String word) throws RemoteException;

}
