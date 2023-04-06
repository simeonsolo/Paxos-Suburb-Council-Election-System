import java.io.*;  
import java.net.*;
import java.util.*;

public class AggregateServer {
	static Vector<PrintWriter> outputStreams = new Vector<PrintWriter>(); // static vector that stores output streams 
	static Vector<BufferedReader> inputStreams = new Vector<BufferedReader>(); // static vector that stores input streams
	static int numConnections = 0; // static vector of number of active connections
	
	public static void main(String[] args) throws Exception {
		/* OPEN SERVER LISTENER THREAD */
		try {
			ServerThreadListener serverThreadListener = new ServerThreadListener(4444);
			serverThreadListener.start();
		} catch  (IOException e) {
			e.printStackTrace();
		}
	}
}
