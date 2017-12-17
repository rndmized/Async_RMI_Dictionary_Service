package ie.gmit.ServiceHandler;

import java.util.Map;
import java.util.concurrent.BlockingQueue;

import ie.gmit.RMI.Definition;
import ie.gmit.RMI.DictionaryService;
import ie.gmit.RMI.Job;

public class ServiceHandlerClient implements Runnable {
	private BlockingQueue<Job> in;
	private Definition result;
	private DictionaryService service;
	private Map<String, Definition> out;

	public ServiceHandlerClient(BlockingQueue<Job> inputQueue, Map<String, Definition> outputQueue, DictionaryService dict) {
		this.in = inputQueue;
		this.service = dict;
		this.out = outputQueue;
	}

	//Create a new thread
	public void run() {
		Job job = in.poll();

		try {

			Thread.sleep(2000);	
			result = service.getDefinition(job.getDef().getWord());
			out.put(job.getTaskNumber(), result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
