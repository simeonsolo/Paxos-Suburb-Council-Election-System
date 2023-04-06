import java.net.*;
import java.io.*;

public class MemberHandler extends Thread {
	private Socket socket; // socket
	public PrintWriter out; // output stream
	public BufferedReader in; // input stream
	
	public MemberHandler(Socket socket, PrintWriter out, BufferedReader in) {
		this.socket = socket; // socket
		this.out = out; // output stream
		this.in = in; // input stream
		// add streams to static vector of streams
		AggregateServer.outputStreams.add(out);
		AggregateServer.inputStreams.add(in);
		// increment number of connections
		AggregateServer.numConnections++;
	}
	
	@Override
	public void run() {
		// loop for requests
		String request;
		// allow little bit of time for nodes to connect
		try {
			Thread.sleep(3000);
		} catch (Exception e) {}
		try {
			while ((request = in.readLine()) != null) {
				// receiving prepare request
				if (request.startsWith("PREPARE"))
				{
					// parse
					String[] arr = request.split("\\(",2);
					String[] arr2 = arr[1].split("\\)",2);
					// send prepare to all active members
					sendAll.Prepare(Double.parseDouble(arr2[0]));
				} 
				// receiving propose request
				else if (request.startsWith("PROPOSE"))
				{
					// parse
					String[] arr = request.split("\\(",2);
					String[] arr2 = arr[1].split("\\)",2);
					String[] arr3 = arr2[0].split(",",2);
					// send propose to all active members
					sendAll.Propose(Double.parseDouble(arr3[0]),arr3[1]);
				}
				// receiving PROMISE
				else if (request.startsWith("PROMISE"))
				{
					// parse
					String[] arr = request.split("\\(",2);
					String[] arr2 = arr[1].split("\\)",2);
					String[] arr3 = arr2[0].split(",",-1);
					// if PROMISE(ID)
					if (arr3.length == 1)
					{
						// send to all active members
						sendAll.PromiseA(Double.parseDouble(arr3[0]));
					}
					else // if PROMISE(ID,ACCEPTED_ID,ACCEPTED_VALUE)
					{
						// send to all active members
						sendAll.PromiseB(Double.parseDouble(arr3[0]),Double.parseDouble(arr3[1]),arr3[2]);
					}
				}
				// receiving ACCEPTED
				else if (request.startsWith("ACCEPTED"))
				{
					// parse
					String[] arr = request.split("\\(",2);
					String[] arr2 = arr[1].split("\\)",2);
					// write result to file
					try 
					(FileWriter fw = new FileWriter("result.txt", false);
					BufferedWriter bw = new BufferedWriter(fw);						
					PrintWriter fileout = new PrintWriter(bw)) 
					{
						fileout.println(arr2[0]);	
					} catch (Exception e) {}
					// send winner to all active members
					sendAll.Accepted(arr2[0]);
				}
				// receiving ACCEPT
				else if (request.startsWith("ACCEPT"))
				{
					// parse
					String[] arr = request.split("\\(",2);
					String[] arr2 = arr[1].split("\\)",2);
					String[] arr3 = arr2[0].split(",",2);
					// send accept to all active members
					sendAll.Accept(Double.parseDouble(arr3[0]),arr3[1]);
				}
				// invalid request
				else
				{
					System.out.println("Invalid Request");
				}
				}
			} catch (Exception e) {}
	}
}
