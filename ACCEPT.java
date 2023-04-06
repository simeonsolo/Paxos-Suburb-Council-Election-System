import java.io.*;  
import java.net.*;

public class ACCEPT extends Thread {
	private Member member; // proposer instance
	private double proposalID; // proposer ID
	private int memberCount; // member count
	private String VALUE = null;
	
	// constructor
	public ACCEPT(Member member, double proposalID, String VALUE, int memberCount) {
		this.member = member;
		this.proposalID = proposalID;
		this.memberCount = memberCount;
		this.VALUE = VALUE;
		member.ACCEPTS++;
	}
	
	@Override
	public void run() {
		// if majority accepts
		if (member.ACCEPTS > memberCount/2)
		{
			// send ACCEPTED(VALUE) message
			member.out.println("ACCEPTED("+VALUE+"))");
		}
	}
}
