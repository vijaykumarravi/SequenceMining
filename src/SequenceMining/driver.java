package SequenceMining;

import java.io.IOException;
import java.util.*;
public class driver {
	
	private static HashMap  <Integer,ArrayList<String>> transactions = new HashMap<Integer,ArrayList<String>> (); 
	private static HashMap<Integer, ArrayList<String>> training;
	private static HashMap  <Integer,ArrayList<String>> testing ;
	private static int kFolds = 10;
	public static void main(String [] args) throws IOException
	{
		int accuracy = 0;
		int total = 0;
		importData readData = new importData();
		transactions = readData.read();
		System.out.println("Total Size : "+ transactions.size());
		long startTime = System.currentTimeMillis();
		
		for(int i =1;i<=kFolds;i++)
		{
			foldData(i, kFolds);
			System.out.println("**************************** FOLD: "+i+" ************************");
			System.out.println("********** Training: **********");
			//printHashMap(training);
			System.out.println(training.size());
			System.out.println("********** Testing: **********");
			System.out.println(testing.size());
		//	printHashMap(testing);*/
			model md = new model();
			md.setData(training, testing);
			md.initSet();
			md.trainModelProbability();
			md.testModelProbability();
			System.out.println("Null COunt"+md.nullCount);
			System.out.println("Average Predict Count: "+md.averageprodictCount );
			System.out.println("Predict Count: "+md.predictCount);
			//md.testModel();
			System.out.println("Min Support:"+ md.minSupport);
			//System.out.println(md.getAccuracy());
			accuracy += md.accuracy;
			total += md.totalSequences;
			long stopTime = System.currentTimeMillis();
			System.out.println(stopTime-startTime);
		}
		System.out.println("Accuracy: "+ accuracy+"\nTotal Sequence: "+total+"\nPercent:"+ (accuracy/total)*100+"%");   
		
		
	}
	public static void printHashMap(HashMap  <Integer,ArrayList<String>> argument)
	{
		Iterator it = argument.entrySet().iterator();
		while(it.hasNext())
		{
			Map.Entry <Integer,ArrayList<String>> pair = (Map.Entry<Integer,ArrayList<String>>)it.next();
			System.out.println("\nKey " + pair.getKey()+": ");
			ArrayList <String> value= pair.getValue();
			for (int i = 0;i<value.size();i++)
				System.out.print(value.get(i)+" ");
			
		}
		
	}
	public static void foldData(int fold, int kFold)
	{
		training =  new HashMap  <Integer,ArrayList<String>> ();
		testing =  new HashMap  <Integer,ArrayList<String>> ();
		int i = 0;
		int totalSize = transactions.size();
		int testLimit = totalSize/kFold;
		int trainLimit = totalSize - testLimit ;
		Iterator it = transactions.entrySet().iterator();
		int j =0;
		while(it.hasNext())
		{
			Map.Entry<Integer,ArrayList<String>> pair = (Map.Entry<Integer, ArrayList<String>>)it.next();
			// Split the data set into folds 
			if(i < (totalSize - (testLimit * fold)) || j > testLimit)
			{
				// Add to training Data
				training.put(pair.getKey(),pair.getValue());
				
			}
			else
			{
				// Add to testing data
				testing.put(pair.getKey(), pair.getValue());
				j++;
			}
		i++;
		}
		
	}
}
