package SequenceMining;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.regex.Pattern;

public class prepData {
public static void main(String[] args) throws IOException
{
	//BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Vijay\\workspace\\SequenceMining\\data\\output.txt"));
	BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Vijay\\workspace\\SequenceMining\\preparedDataOutput.txt"));
	HashMap <String, String> check = new HashMap<String,String>();
	PrintWriter wr = new PrintWriter("final1.txt","UTF-8");
	String line;
	Pattern pt= Pattern.compile("#SUP.+");
	while((line = br.readLine()) != null)
		{
			line = line.replace(" ", "");
			String [] items = line.split("-1");
		for(int i =0;i<items.length;i++)
		{
			if(i < items.length - 1)
			{
				check.put(items[i],items[i+1]);
				wr.write(items[i]);
				wr.write("->");
				wr.write(items[i+1]);
				wr.write("\n");
			}
			
		}
		//		String writeLine = line.replaceAll("#SUP.+", " ");		
		//	wr.write(writeLine);
			//wr.write("\n");
		}
	wr.write(check.size());	
	wr.close();
	System.out.println(check.size());
System.out.println("Done");
}

}
