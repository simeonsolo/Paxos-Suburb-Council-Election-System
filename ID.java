import java.io.*;  
import java.net.*;
import java.nio.*;
import java.nio.file.*;

public class ID {
	public static synchronized int increment() {
		// read content from file
		String num = "";
		try
		{
			num = new String(Files.readAllBytes(Paths.get("max.txt")));
		} catch (Exception e) {}
		// separate into entries
		String[] temp = num.split("\n",2);
		// increment
		int max = Integer.parseInt(temp[0]);
		max++;
		// rewrite
		try 
		(FileWriter fw = new FileWriter("max.txt", false);
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter out = new PrintWriter(bw)) 
		{
			out.print(max);	
		} catch (Exception e) {}
		// return
		return max;
	}
}
