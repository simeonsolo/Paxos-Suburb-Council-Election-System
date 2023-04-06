------------------- IMPLEMENTED FUNCTIONALITY -------------------

-> Paxos implementation works when two councillors send voting proposals at the same time (DONE)
-> Paxos implementation works in the case where all M1-M9 have immediate responses to voting queries (DONE)
-> Paxos implementation works when M1 – M9 have responses to voting queries suggested by the profiles, including when M2 or M3 propose and then go offline (DONE)
-> Testing harness for the above scenarios + evidence that they work (in the form of printouts) (DONE)
-> BONUS: Paxos implementation works with a number ‘n’ of councilors with four profiles of response times: immediate;  medium; late; never (DONE)

------------------- HOW IT WORKS -------------------

-> Aggregate server maintains connections with all members, receives & distributes queries to active members
-> Member object accepts the following arguments: member type (M1-M9 or immediate,medium,late,ever), proposal value (if this member intends to serve as a proposer)
-> Members (M1-M3 or immediate,medium,late,never) begin by sending PROPOSAL(ID)
-> Members connection and/or response time is then evaluated (in seconds):
	-> M1: response time = 0
	-> M2: if at cafe (50% chance), response time = 0. if not at cafe (50% chance), they either have response time = 1 (90% chance), or lose connection (10% chance)
	-> M3: if not in woods (80% chance), response time = 0.5. if in woods (20% chance), they lose connection
	-> M4-M9: response time varies, = 0, 0.5 or 1
	-> immediate: response time = 0
	-> medium: response time = 0.5
	-> late: response time = 1
	-> never: loses connection
-> Standard paxos flow ensues, with following important points:
	-> number of active members is stored and updated on the aggregate server at every message send
	-> number of active members is sent in ACCEPT & PROMISE messages so member knows if they receive majority votes or not
	-> once a member has received majority accepts, and a consensus is reached, it will send an ACCEPTED message that is then distributed to all active members so it is known
	-> once the AS sends out the ACCEPTED message, it will terminate itself and all members
	
------------------- HOW TO RUN -------------------

	javac *.java
	java AggregateServer
	java Member *member profile* *value to propose*
	
	e.g.,
	
		-> Base implementation (9 members, M1-M3 propose):
			-> Requires:
				>= 3 active members (members that will not lose connection)
					>= 1 proposer (M1 or M2 or M3)
		
		javac *.java
		java AggregateServer
		java Member M1 Robert
		java Member M2 Darren
		java Member M3 Lucy
		java Member M4
		java Member M5
		java Member M6
		java Member M7
		java Member M8
		java Member M9
	
		-> Bonus implementation (n members that all propose):
			-> At minimum:
				>= 3 active members (members that will not lose connection)
		
		javac *.java
		java AggregateServer
		java Member immediate Simeon
		java Member medium Declan
		java Member late Lolly
		java Member never Karen
		...

------------------- HOW TO RUN TESTS ------------------

INFORMATION
	-> Automated testing is completed in bash
	-> Bash script is called "test.sh"
	-> Test cases are described in this script
	-> Tests are written for base implementation and bonus implementation
	-> Sleep times are long to assure paxos round completes for evaluation
	-> Results are compared with expected result/s (listed in script)
	-> Test results are printed in the console
	-> output.txt stores the log of the aggregate server (messages it receives and sends)
	-> result.txt stores the result of that round of paxos
	
HOW TO RUN
	-> javac *.java
	-> chmod +x test.sh
	-> ./test.sh
