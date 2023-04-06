import java.io.*;  
import java.net.*;

public class PREPARE {
	private Member member; // proposer instance
	private double proposalID; // proposer ID
	
	// constructor
	public PREPARE(Member member, double proposalID) {
		this.member = member;
		this.proposalID = proposalID;
	}
	
	public void start() {
		// if ID is not current maximum
		if (proposalID <= member.max_ID) {
		// ignore
		} else { // is maximum
			// assign new maximum
			member.max_ID = proposalID;
			// is there already an accepted proposal
			if (member.proposal_accepted == true) {
				System.out.println("Already accepted proposal");
				// respond
				try {
					// send PROMISE(ID,accepted_ID,acceptedVALUE)
					member.out.println("PROMISE("+proposalID+","+member.accepted_ID+","+member.accepted_VALUE+")");
					System.out.println("Sent PROMISE(ID,accepted_ID,accepted_VALUE)");
				} catch (Exception e) {
					System.out.println("Error sending PROMISE(ID,ACCEPTED_ID,ACCEPTED_VALUE");
				}
			} else {
				// respond
				try {
					// send PROMISE(ID)
					member.out.println("PROMISE("+proposalID+")");
					System.out.println("Sent PROMISE(ID)");
				} catch (Exception e) {
					System.out.println("Error sending PROMISE(ID)");
				}
			}
		}
	}
}
