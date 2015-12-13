package SequenceMining;

import java.io.*;
import java.util.*;

public class importData {
	private static HashMap  <Integer,ArrayList<String>> input = new HashMap  <Integer,ArrayList<String>> ();
	public static HashMap  <Integer,ArrayList<String>> read() throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Vijay\\workspace\\SequenceMining\\data\\dataset1.txt"));
		String line;
		int i =1;
		while((line = br.readLine()) != null)
			{
				String [] items = line.split(" ");
				for (int j =0; j<items.length;j++){
				if(input.containsKey(i))
				{
					ArrayList <String> temp = input.get(i);
					temp.add(items[j]);
					input.put(i, temp);
				}
				else
				{
					ArrayList <String> temp = new ArrayList<String> ();
					temp.add(items[j]);
					input.put(i, temp);
				}
				}
			i++;
			}
		return input;
	}

}
