import java.io.*;  
import java.net.*;
import java.util.*;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

public class Member {
	private Socket socket; // socket
	public PrintWriter out; // output stream
	public BufferedReader in; // input stream
	public int PROMISES = 0; // number of promises received for proposal
	public int ACCEPTS = 0; // number of accepts received for proposal
	public double max_ID = 0; // maximum ID received
	public String VALUE; // proposed value, if exists
	public boolean proposal_accepted = false; // true if accepted proposal already
	public double accepted_ID; // accepted proposal ID
	public String accepted_VALUE; // accepted proposal value
	public Vector<Double> promisedIDs = new Vector<Double>(); // stores promised IDs
	public Vector<String> promisedVALUES = new Vector<String>(); // stored promised values
	public String profile; // profile e.g. M1-M9 or immediate,medium,late,never
	
	public static void main(String[] args) throws Exception {
		// create member instance
		try {
			Member member = new Member();
			member.run(args);
		} catch (Exception e) {}
	}
	
	public void run(String[] args) throws Exception {
		// get member profile type from args
		this.profile = args[0];
		// get proposal value from args if exists
		if (args.length == 2) {
			this.VALUE = args[1];
		}
		// obtain process ID
		RuntimeMXBean bean = ManagementFactory.getRuntimeMXBean();
		String jvmName = bean.getName();
		String[] pid = jvmName.split("@",2);
		// make connection to aggregate server
		this.socket = new Socket("localhost",4444);
		// obtain input/output streams
		out = new PrintWriter(socket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		// send PREPARE(ID) message (if proposer)
		double proposalID = 0;
		if (profile.equals("M1") || profile.equals("M2") || profile.equals("M3") || profile.equals("immediate") || profile.equals("medium") || profile.equals("late") || profile.equals("never"))
		{
			// obtain unique proposal ID
			int propID = ID.increment();
			String mod = propID+"."+pid[0];
			proposalID = Double.parseDouble(mod);
			// send
			sendPREPARE sendingPREPARE = new sendPREPARE(this,proposalID);
			sendingPREPARE.start();
		} 
		// obtain response time for member type
		int responseTime = memberType.returnResponseTime(profile);
		// if member type loses connection
		if (responseTime == -1 ) {
			socket.close();
			out.close();
			in.close();
		}
		// loop continously for messages from AS
		String request;
		while ((request = in.readLine()) != null)
		{
			// sleep for given response time
			Thread.sleep(responseTime);
			// if PREPARE(ID)
			if (request.startsWith("PREPARE"))
			{
				// parse
				String[] arr = request.split("\\(",2);
				String[] arr2 = arr[1].split("\\)",2);
				// if not my own PREPARE
				if (Double.parseDouble(arr2[0]) != proposalID)
				{
					PREPARE processingPREPARE = new PREPARE(this,Double.parseDouble(arr2[0]));
					processingPREPARE.start();
				}
			}
			// if PROMISE(...)
			else if (request.startsWith("PROMISE"))
			{
				// parse
				String[] arr = request.split("\\(",2);
				String[] arr2 = arr[1].split("\\)",2);
				String[] arr3 = arr2[0].split(",",-1);
				// if promise is intended for me
				if (Double.parseDouble(arr3[0]) == proposalID)
				{
					// if PROMISE(ID,MEMBERCOUNT)
					if (arr3.length == 2)
					{
						PROMISE processingPROMISE = new PROMISE(this,Double.parseDouble(arr3[0]),this.VALUE,Integer.parseInt(arr3[1]));
						processingPROMISE.start();
					}
					else // if PROMISE(ID,ACCEPTED_ID,ACCEPTED_VALUE,MEMBERCOUNT)
					{
						PROMISE processingPROMISE = new PROMISE(this,Double.parseDouble(arr3[0]),Double.parseDouble(arr3[1]),arr3[2],Integer.parseInt(arr3[3]));
						processingPROMISE.start();
					}
				}
			}
			// if PROPOSE(ID,VALUE)
			else if (request.startsWith("PROPOSE"))
			{
				// parse
				String[] arr = request.split("\\(",2);
				String[] arr2 = arr[1].split("\\)",2);
				String[] arr3 = arr2[0].split(",",2);
				// if not my own proposal
				if (Double.parseDouble(arr3[0]) != proposalID)
				{
					PROPOSE processingPROPOSE = new PROPOSE(this,Double.parseDouble(arr3[0]),arr3[1]);
					processingPROPOSE.start();
				}
			}
			// if ACCEPTED(VALUE)
			else if (request.startsWith("ACCEPTED"))
			{
				// parse
				String[] arr = request.split("\\(",2);
				String[] arr2 = arr[1].split("\\)",2);
				System.out.println("Winning value is: " + arr2[0]);
			}
			// if ACCEPT(ID,VALUE,MEMBERCOUNT)
			else if (request.startsWith("ACCEPT"))
			{
				// parse
				String[] arr = request.split("\\(",2);
				String[] arr2 = arr[1].split("\\)",2);
				String[] arr3 = arr2[0].split(",",3);
				// if accept is intended for me
				if (Double.parseDouble(arr3[0]) == proposalID)
				{
					ACCEPT processingACCEPT = new ACCEPT(this,Double.parseDouble(arr3[0]),arr3[1],Integer.parseInt(arr3[2]));
					processingACCEPT.start();
				}
			}
			else
			{
				// invalid request
				System.out.println("Invalid Request.");
			}
		}
	}
}
