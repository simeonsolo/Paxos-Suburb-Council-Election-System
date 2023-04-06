import java.io.*;  
import java.net.*;
import java.util.*;

public class ServerThreadListener extends Thread {
	private int port; // port
	private ServerSocket serverSocket; // socket
	
	public ServerThreadListener(int port) throws IOException 
	{
		// assign class variables
		this.port = port;
		// create socket
		this.serverSocket = new ServerSocket(this.port);
	}
	
	@Override
	public void run() {
		while (serverSocket.isBound() && !serverSocket.isClosed())
		{
			try {
				// accept members
				Socket socket = serverSocket.accept();
				// create streams
				try {
					PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
					BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					System.out.println("Accepted connection!");
					// send connection 
					MemberHandler memberHandler = new MemberHandler(socket, out, in);
					memberHandler.start();
				} catch (Exception e) { 
					System.out.println("Error creating streams");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
