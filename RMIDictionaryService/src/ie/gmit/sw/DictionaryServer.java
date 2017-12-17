package ie.gmit.sw;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

import ie.gmit.RMI.DictionaryService;
import ie.gmit.RMI.DictionaryServiceImpl;
/**
 * 
 * @author rndmized
 *
 */
public class DictionaryServer {
	public static void main(String[] args) throws Exception{

		
		//Create an instance of the class DictionaryService 
		DictionaryService dictServ = new DictionaryServiceImpl();
		
		//Start the RMI registry on port 1099
		LocateRegistry.createRegistry(1099);
		
		//Bind our remote object to the registry with the human-readable name "dictionary"
		Naming.rebind("dictionary", dictServ);
		
		//Print a nice message to standard output
		System.out.println("Server ready.");
	}

}
