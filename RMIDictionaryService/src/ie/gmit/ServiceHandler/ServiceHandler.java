package ie.gmit.ServiceHandler;

import java.io.*;
import java.rmi.Naming;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.rmi.NotBoundException;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import javax.servlet.*;
import javax.servlet.http.*;

import ie.gmit.RMI.Definition;
import ie.gmit.RMI.DefinitionImpl;
import ie.gmit.RMI.DictionaryService;
import ie.gmit.RMI.Job;

public class ServiceHandler extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private volatile ExecutorService ex;
	private final int THREAD_POOL_SIZE = 5;
	
	private volatile BlockingQueue<Job> inQueue;
	private volatile Map<String, Definition> outQueue;
	
	
	private static long jobNum = 0;
	private volatile boolean jobCompleted = false;
	
	public void init() throws ServletException {

		inQueue = new LinkedBlockingQueue<Job>();
		outQueue = new HashMap<String, Definition>();
		ex = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		
		// Initialize variables with the submitted form info. 
		// Local to method thus thread safe.
		String taskNumber = req.getParameter("taskNumber");
		String word = req.getParameter("word").toUpperCase();
		Definition def = new DefinitionImpl(word);
		
		DictionaryService dict = null;

		try {
			//Get the Remote Object
			dict = (DictionaryService) Naming.lookup("rmi://localhost:1099/dictionary");
		} catch (NotBoundException e) {
			e.printStackTrace();
		}

		out.print("<html><head><title>Distributed Systems Assignment</title>");		
		out.print("</head>");		
		out.print("<body>");
		
		if (taskNumber == null){
			jobNum++;
			jobCompleted = false;
			
			taskNumber = new String("T" + jobNum);
 			
			//Create a Job object from the request variables and the jobNumber
			Job job = new Job(taskNumber, def);
			
			//Add the job to the queue
			inQueue.add(job);
			//pass job to the ServiceHandlerClient
			Runnable serviceHandlerClient = new ServiceHandlerClient(inQueue, outQueue, dict);
			ex.execute(serviceHandlerClient);
			
		}else{
			
			//Check outQueue if the job has been completed
			if (outQueue.containsKey(taskNumber)) {
				// Get the Definition from the map by task number key
				Definition result = outQueue.get(taskNumber);

				jobCompleted = result.isProcessed();

				//check to see if the Definition item is Processed
				if (jobCompleted == true) {
					System.out.println("Task Completed");
					//remove the processed item from Map by taskNumber
					outQueue.remove(taskNumber);
					//get the distance of the Current Task
					out.print("<font color=\"#000000\"><b>");		
					out.print("<br>Word: " + result.getWord());
					out.print("<br>");
					out.print("<br>Definition: " + result.getWordDefinition());
				
					//Form sends every 10 seconds to simulate a client polling. Keeps going until task is completed.
					out.print("<form name=\"frmRequestDetails\">");
					out.print("<input name=\"word\" type=\"hidden\" value=\"" + result.getWord() + "\">");
					out.print("<input name=\"taskNumber\" type=\"hidden\" value=\"" + taskNumber + "\">");
					out.print("</form>");								
					out.print("</body>");	
					out.print("</html>");	
				}
				else
				{
					out.print("<font color=\"#000000\"><b>");
					out.print("Please wait while the definition is being extracted....");
				}
			}
			
		}
		
		//if the task is complete there is no need to send the form again, just output a thank you message
		if(jobCompleted){
			out.print("<font color=\"#000000\"><b>");
			out.print("<br><br><center>THANK YOU FOR USING THE DICTIONARY SERVICE<center>");
		}
		else//if task is not complete poll again
		{
			out.print("<H1>Processing request for Job#: " + taskNumber + "</H1>");
			out.print("<div id=\"r\"></div>");
		
			out.print("<font color=\"#000000\"><b>");
			out.print("<br>Word: " + word);

		
			// Form is sent every 5 seconds to simulate a client polling. 
			// Keeps polling until task is completed.
			out.print("<form name=\"frmRequestDetails\">");
			out.print("<form name=\"frmRequestDetails\">");
			out.print("<input name=\"word\" type=\"hidden\" value=\"" + word + "\">");
			out.print("<input name=\"taskNumber\" type=\"hidden\" value=\"" + taskNumber + "\">");
			out.print("</form>");								
			out.print("</body>");	
			out.print("</html>");	
		
			out.print("<script>");
			//Here is where we submit the form
			out.print("var wait=setTimeout(\"document.frmRequestDetails.submit();\", 5000);");
			out.print("</script>");
		}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
 	}
}