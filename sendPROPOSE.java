import java.net.*;
import java.io.*;

public class sendPROPOSE extends Thread {
	private Member member; // proposer instance
	private double proposalID; // proposer ID
	private String VALUE; // proposed value
	
	// constructor
	public sendPROPOSE(Member member, double proposalID, String VALUE) {
		// assign parameters to class variables
		this.member = member;
		this.proposalID = proposalID;
		this.VALUE = VALUE;
	}
	
	@Override
	public void run() {
		// obtain input & output stream
		try {
			// send PROPOSE(ID) message
			member.out.println("PROPOSE("+proposalID+","+VALUE+")");
			System.out.println("Sent " + "PROPOSE("+proposalID+","+VALUE+")");
		} catch (Exception e) {
			System.out.println("Error sending PROPOSE(ID,VALUE)");
		}
	}
}
