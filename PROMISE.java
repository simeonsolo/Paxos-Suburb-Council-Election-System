import java.io.*;  
import java.net.*;

public class PROMISE {
	private Member member; // proposer instance
	private double proposalID; // proposer ID
	private int memberCount; // member count
	private double accepted_ID; // accepted propopsal ID
	private String accepted_VALUE; // accepted proposal value
	private String VALUE; // own members value
	
	// constructor 1
	public PROMISE(Member member, double proposalID, String VALUE, int memberCount) {
		this.member = member;
		this.proposalID = proposalID;
		this.memberCount = memberCount;
		this.VALUE = VALUE;
		// increment promises
		member.PROMISES++;
	}
	
	// constructor 2
	public PROMISE(Member member, double proposalID, double accepted_ID, String accepted_VALUE, int memberCount) {
		this.member = member;
		this.proposalID = proposalID;
		this.memberCount = memberCount;
		this.accepted_ID = accepted_ID;
		member.promisedIDs.add(accepted_ID);
		this.accepted_VALUE = accepted_VALUE;
		member.promisedVALUES.add(accepted_VALUE);
		// increment promises
		member.PROMISES++;
	}
	
	public void start() {
		// if majority promises
		if (member.PROMISES > memberCount/2)
		{
			// if we received responses from accepted values (from other proposals)
			if (!member.promisedIDs.isEmpty() || !member.promisedVALUES.isEmpty())
			{
				// find max ID
				double max = 0;
				int index = 0;
				for (int i = 0 ; i < member.promisedIDs.size() ; i++)
				{
					if (member.promisedIDs.get(i) > max)
					{
						max = member.promisedIDs.get(i);
						index = i;
					}
				}
				// send PROPOSE
				sendPROPOSE sendingPROPOSE = new sendPROPOSE(member,proposalID,member.promisedVALUES.get(index));
				sendingPROPOSE.start();
			}
			else // no responses contain accepted values (from other proposals)
			{
				// send PROPOSE
				sendPROPOSE sendingPROPOSE = new sendPROPOSE(member,proposalID,VALUE);
				sendingPROPOSE.start();
			}
		}
	}
}
