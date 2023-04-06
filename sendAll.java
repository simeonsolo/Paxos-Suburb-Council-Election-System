import java.io.*;  
import java.net.*;
import java.util.*;

public class sendAll {
	// send PREPARE
	public static void Prepare(double ID)
	{
		// log
		System.out.println("Received "+"PREPARE("+ID+")");
		// loop over vector of connections
		for (int i = 0; i < AggregateServer.outputStreams.size(); i++)
		{
			// if member is still connected
			if (AggregateServer.outputStreams.get(i).checkError() == false) {
				// send PREPARE(ID)
				AggregateServer.outputStreams.get(i).println("PREPARE("+ID+")");
			// if member has since disconnected
			} else {
				// remove this connection from static vectors
				AggregateServer.numConnections--;
				AggregateServer.outputStreams.remove(i);
				AggregateServer.inputStreams.remove(i);
				i--;
			}
		}
	}
	// send PROPOSE
	public static void Propose(double ID, String VALUE)
	{
		// log
		System.out.println("Received "+"PROPOSE("+ID+","+VALUE+")");
		// loop over vector of connections
		for (int i = 0; i < AggregateServer.outputStreams.size(); i++)
		{
			// if member is still connected
			if (AggregateServer.outputStreams.get(i).checkError() == false) {
				// send PROPOSE(ID,VALUE)
				AggregateServer.outputStreams.get(i).println("PROPOSE("+ID+","+VALUE+")");
			// if member has since disconnected
			} else {
				// remove this connection from static vectors
				AggregateServer.numConnections--;
				AggregateServer.outputStreams.remove(i);
				AggregateServer.inputStreams.remove(i);
				i--;
			}
		}
	}
	// send PROMISE(ID)
	public static void PromiseA(double ID)
	{
		// log
		System.out.println("Received "+"PROMISE("+ID+")");
		// loop over vector of connections
		for (int i = 0; i < AggregateServer.outputStreams.size(); i++)
		{
			// if member is still connected
			if (AggregateServer.outputStreams.get(i).checkError() == false) {
				// send PROMISE(ID,NUMCONNECTIONS)
				AggregateServer.outputStreams.get(i).println("PROMISE("+ID+","+AggregateServer.numConnections+")");
			// if member has since disconnected
			} else {
				// remove this connection from static vectors
				AggregateServer.numConnections--;
				AggregateServer.outputStreams.remove(i);
				AggregateServer.inputStreams.remove(i);
				i--;
			}
		}
	}
	// send PROMISE(ID,ACCEPTED_ID,ACCEPTED_VALUE,MEMBERCOUNT)
	public static void PromiseB(double ID, double Accepted_ID, String Accepted_VALUE)
	{	
		// log
		System.out.println("Received "+"PROMISE("+ID+","+Accepted_ID+","+Accepted_VALUE+")");
		// loop over vector of connections
		for (int i = 0; i < AggregateServer.outputStreams.size(); i++)
		{
			// if member is still connected
			if (AggregateServer.outputStreams.get(i).checkError() == false) {
				// send PROMISE(ID,accepted_ID,accepted_VALUE,NUMCONNECTIONS)
				AggregateServer.outputStreams.get(i).println("PROMISE("+ID+","+Accepted_ID+","+Accepted_VALUE+","+AggregateServer.numConnections+")");
			// if member has since disconnected
			} else {
				// remove this connection from static vectors
				AggregateServer.numConnections--;
				AggregateServer.outputStreams.remove(i);
				AggregateServer.inputStreams.remove(i);
				i--;
			}	
		}
	}
	
	// send ACCEPT
	public static void Accept(double ID,String VALUE)
	{
		// log
		System.out.println("Received "+"ACCEPT("+ID+","+VALUE+")");
		// loop over vector of connections
		for (int i = 0; i < AggregateServer.outputStreams.size(); i++)
		{
			// if member is still connected
			if (AggregateServer.outputStreams.get(i).checkError() == false) {
				// send ACCEPT(ID,VALUE,NUMCONNECTIONS)
				AggregateServer.outputStreams.get(i).println("ACCEPT("+ID+","+VALUE+","+AggregateServer.numConnections+")");
			// if member has since disconnected
			} else {
				// remove this connection from static vectors
				AggregateServer.numConnections--;
				AggregateServer.outputStreams.remove(i);
				AggregateServer.inputStreams.remove(i);
				i--;
			}
		}
	}
	
	// send ACCEPTED
	public static void Accepted(String VALUE)
	{
		// log
		System.out.println("Received "+"ACCEPTED("+VALUE+")");
		// loop over vector of connections
		for (int i = 0; i < AggregateServer.outputStreams.size(); i++)
		{
			// if member is still connected
			if (AggregateServer.outputStreams.get(i).checkError() == false) {
				// send ACCEPTED(VALUE)
				AggregateServer.outputStreams.get(i).println("ACCEPTED("+VALUE+")");
			// if member has since disconnected
			} else {
				// remove this connection from static vectors
				AggregateServer.numConnections--;
				AggregateServer.outputStreams.remove(i);
				AggregateServer.inputStreams.remove(i);
				i--;
			}
			// terminate program as end of round reached
			try {
				Thread.sleep(1000);
			} catch (Exception e) {}
			System.exit(0);
		}
	}
}
