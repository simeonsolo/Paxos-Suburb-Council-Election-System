import java.net.*;
import java.io.*;

public class sendPREPARE extends Thread {
	private Member member; // proposer instance
	private double proposalID; // proposer ID
	
	// constructor
	public sendPREPARE(Member member, double proposalID) throws IOException {
		// assign parameters to class variables
		this.member = member;
		this.proposalID = proposalID;
	}
	
	@Override
	public void run() {
		// make connection to aggregate server
		try {
			// send PREPARE(ID)
			member.out.println("PREPARE("+proposalID+")");
			System.out.println("Sent "+"PREPARE("+proposalID+")");
			return;
		} catch (Exception e) {
			System.out.println("Error sending PREPARE(ID)");
		}
	}
}
