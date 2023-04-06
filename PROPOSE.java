import java.io.*;  
import java.net.*;

public class PROPOSE {
	private Member member; // proposer instance
	private double proposalID; // proposer ID
	private String VALUE; // proposed value
	
	// constructor
	public PROPOSE(Member member, double proposalID, String VALUE) {
		this.member = member;
		this.proposalID = proposalID;
		this.VALUE = VALUE;
	}
	
	public void start() {
		// if ID is the largest I have seen
		if (proposalID == member.max_ID) {
			// note we accepted a proposal
			member.proposal_accepted = true;
			// save accepted proposal number
			member.accepted_ID = proposalID;
			// save accepted proposal data
			member.accepted_VALUE = VALUE;
			System.out.println("ACCEPTED VALUE: " + member.accepted_VALUE);
			// respond ACCEPTED message
			try {
				// send ACCEPT(ID,VALUE)
				member.out.println("ACCEPT("+proposalID+","+VALUE+")");
				System.out.println("Sent " + "ACCEPT("+proposalID+","+VALUE+")");
			} catch (Exception e) {
				System.out.println("Error sending ACCEPT(ID,VALUE)");
			}
		} else {
			// not largest ID received
		}
	}
}
