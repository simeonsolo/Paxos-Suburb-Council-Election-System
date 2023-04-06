import java.io.*;  
import java.net.*;
import java.util.*;

public class memberType {
	public static int returnResponseTime(String profile) {
		int chance;
		// if M1
		if (profile.equals("M1")) { // instantaneous
			return 0;
		// if M2
		} else if (profile.equals("M2")) {
			// 50% chance they are at cafe
			chance = (int)(Math.random()*(2-1+1)+1); 
			if (chance == 1) { // if not at cafe
				// 10% chance they have no connection
				chance = (int)(Math.random()*(10-1+1)+1);
				if (chance == 1) { // lost connection 
					return -1;
				} else { // slow response time
					return 1000;
				}
			} else { // immediate response
				return 0;
			}
		// if M3
		} else if (profile.equals("M3")) {
			// 20% chance they are in woods
			chance = (int)(Math.random()*(5-1+1)+1);
			if (chance == 1) { // lost connection
				return -1;
			} else { // moderate response time
				return 500;
			}
		// if M4-M9
		} else if (profile.equals("M4") || profile.equals("M5") || profile.equals("M6") || profile.equals("M7") || profile.equals("M8") || profile.equals("M9")) {
			chance = (int)(Math.random()*(3-1+1)+1);
			if (chance == 1) { // immediate
				return 0;
			} else if (chance == 2) { // moderate response
				return 500;
			} else if (chance == 3) { // slow response
				return 1000;
			}
		// if immediate
		} else if (profile.equals("immediate")) { // immediate
			return 0;
		// if medium
		} else if (profile.equals("medium")) { // moderate response
			return 500;
		// if late
		} else if (profile.equals("late")) { // slow response
			return 1000;
		// if never
		} else if (profile.equals("never")) { // lost connection
			return -1;
		}
		return -1;
	}
}
