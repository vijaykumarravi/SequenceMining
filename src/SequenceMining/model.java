package SequenceMining;

import java.util.*;

public class model {
	private HashMap  <Integer,ArrayList<String>>  testingData;
	private HashMap  <Integer,ArrayList<String>>  trainingData ;
	private HashMap <String, Integer> count;
	private HashMap <String, HashMap<String,Double>> probability;
	private Set<String> products = new HashSet<String>();
	int nullCount;
	private int accuracy1;
	private int total1;
	int totalSequences;
	int accuracy;
	double confThreshold = 0.01;
	int predictCount;
	double averageprodictCount;
	double minSupport = 0;
	
	public void setData(HashMap  <Integer,ArrayList<String>> trainingData, HashMap  <Integer,ArrayList<String>> testingData)
	{
		this.trainingData = new HashMap  <Integer,ArrayList<String>> ();
		this. testingData = new HashMap  <Integer,ArrayList<String>> ();
		this.trainingData = trainingData;
		this.testingData = testingData;
		accuracy1 = 0;
		total1 = 0;
		predictCount = 0;
	}
	public void initSet()
	{
		Iterator it = trainingData.entrySet().iterator();
		while(it.hasNext())
		{
			
			Map.Entry<Integer,ArrayList<String>> pair = (Map.Entry<Integer, ArrayList<String>>)it.next();
			ArrayList<String> temp = pair.getValue();
			products.addAll(temp);
		}
		it = testingData.entrySet().iterator();
		while(it.hasNext())
		{
			
			Map.Entry<Integer,ArrayList<String>> pair = (Map.Entry<Integer, ArrayList<String>>)it.next();
			ArrayList<String> temp = pair.getValue();
			products.addAll(temp);
		}
		
	}
	public double getAccuracy ()
	{
		System.out.println("Accuracy:" +accuracy1);
		System.out.println("Total:" +total1);
		return accuracy1/total1;
	}
	public void trainModelProbability()
	{
		
		probability = new HashMap<String,HashMap<String,Double>>();
		for (String s: products)
		{	
			int countProducts = 0;
			Iterator it = trainingData.entrySet().iterator();
			while(it.hasNext())
			{
				
				Map.Entry<Integer,ArrayList<String>> pair = (Map.Entry<Integer, ArrayList<String>>)it.next();
				ArrayList<String> transaction = pair.getValue();
				if(s.equals("48769"))
				{
					if(transaction.contains(s))
					{
						System.out.println("There is 48769");
					}
				}
				if(transaction.contains(s))
				{
					// The transaction has the Product.
					countProducts++;
					int index = transaction.indexOf(s);
					if(index != transaction.size()-1)
					{
						// There is another product following product s
						String nextProduct = transaction.get(index+1);
						if(probability.containsKey(s))
							{
								HashMap<String,Double> temp = probability.get(s);
								if(temp.containsKey(nextProduct))
								{
									Double val = temp.get(nextProduct);
									val =val+1;
									temp.put(nextProduct, val);
									
								}
								else
								{
									Double val = 1.0;
									temp.put(nextProduct, val);
								}
								probability.put(s, temp);
							}
						else
						{
							HashMap<String,Double> temp = new HashMap<String,Double>();
							temp.put(nextProduct, 1.0);
							probability.put(s, temp);
						}
					}
				}
			}
			
		
		}
	Iterator iter = probability.entrySet().iterator();
	while(iter.hasNext())
	{
		Map.Entry<String,Map<String,Double>> pair = (Map.Entry<String, Map<String,Double>>) iter.next();
		String key =pair.getKey();
		HashMap<String, Double> newMap = new HashMap<String,Double>();
		Iterator inIter = pair.getValue().entrySet().iterator();
		double current= 0;
		Iterator trainIter = trainingData.entrySet().iterator();
		while(trainIter.hasNext())
		{
			Map.Entry<Integer,ArrayList<String>> trainPair = (Map.Entry<Integer, ArrayList<String>>)trainIter.next();
			ArrayList<String> trans = trainPair.getValue();
			if(trans.contains(key))
				current+=1;
		}
		//System.out.println("Current Product: " + pair.getKey() +"\tCount: "+current);
		
		//Double probabilityCurrent = current/trainingData.size();
		while(inIter.hasNext())
		{
			Map.Entry<String, Double> inPair = (Map.Entry<String, Double>)inIter.next();
			String inKey = inPair.getKey();
			double next = 0;
			Iterator trainIterr = trainingData.entrySet().iterator();
			while(trainIterr.hasNext())
			{
				Map.Entry<Integer,ArrayList<String>> trainPairr = (Map.Entry<Integer, ArrayList<String>>)trainIterr.next();
				ArrayList<String> transs = trainPairr.getValue();
				if(transs.contains(inKey))
					next+=1;
			}
			Double val = inPair.getValue();
			val = val/current;
			val = (val*next)/current;
			newMap.put(inKey, val);
			//System.out.println("Next Product: " + inPair.getKey() +"\tCount: "+next +"\tProbability: "+val );
			
		}
		probability.put(key, newMap);

		
	}
	/*	System.out.println("------ End of Training ----- ");
		Iterator q = probability.entrySet().iterator();
		while(q .hasNext())
		{
			Map.Entry<String, HashMap<String, Double>> p1 = (Map.Entry<String, HashMap<String,Double>>)q.next();
			Iterator q2 = p1.getValue().entrySet().iterator();
			System.out.println("Current Product:" + p1.getKey());
			while (q2.hasNext())
			{
				Map.Entry<String, Double> t = (Map.Entry<String, Double>)q2.next();
				System.out.println("Next Product: "+ t.getKey()+ "Count: "+ t.getValue() );
				
			}
		}
	------
	Iterator iwt = probability.entrySet().iterator();
	while(iwt.hasNext())
	{
		Map.Entry <String,HashMap<String,Double>> pair = (Map.Entry<String,HashMap<String,Double>>)iwt.next();
		System.out.println("\nKey " + pair.getKey()+": ");
		HashMap<String,Double> value= pair.getValue();
		Iterator iwts = value.entrySet().iterator();
		while(iwts.hasNext())
		{
			Map.Entry<String,Double> p1 =(Map.Entry<String, Double>)iwts.next();
			System.out.print("Next Product: "+ p1.getKey()+"\t Probability: "+p1.getValue()+"\n");
		}
		System.out.println("_________________");
		
	}v
*/
		
	}
	public void testModelProbability()
	{
		nullCount = 0;
		totalSequences=0;
		accuracy = 0;
		
		Iterator it =testingData.entrySet().iterator();
		while(it.hasNext())
		{
			Map.Entry<Integer,ArrayList<String>> pair = (Map.Entry<Integer, ArrayList<String>>)it.next();
			ArrayList<String> testProducts = pair.getValue();
			for(int i =0;i<testProducts.size()-1;i++)
			{
				if(testProducts.size()>1)
				{
					ArrayList<String>nextPossible = new ArrayList<String>();
					String currentProduct = testProducts.get(i);
						nextPossible = findMaxProbabiltiy(currentProduct);
					
//					catch(Exception ae)
//					{	
//					
//						for(int k =0;k<nextPossible.size();k++)
//							System.out.println(nextPossible.get(k));
//						
//					}
					
					if(testProducts.indexOf(currentProduct)!=testProducts.size()-1)
					{
				
						//System.out.println("Current Product: "+testProducts.get(i)+ "\t next Product: "+nextPossible.get(0)+"\t Actual next "+ testProducts.get(testProducts.indexOf(currentProduct)+1));
						int indexCurrentProduct = testProducts.indexOf(currentProduct);
						String actulNextProduct = testProducts.get(indexCurrentProduct+1);
					
						if(nextPossible.contains(actulNextProduct))
						{
							predictCount += nextPossible.size();	
							accuracy++;
							totalSequences++;
						}
						else
							{
								totalSequences++;
								//learn(currentProduct,actulNextProduct);	
								/*System.out.println("############");
								for(int k =0;k<nextPossible.size();k++)
									System.out.println(nextPossible.get(k));*/
								
							}
					}
				}
			}
			
		}
	
		
	System.out.println("Total:" + totalSequences);
	System.out.println("Accutacy: "+ accuracy);
	Double percent = (double) (accuracy/totalSequences);
	System.out.println("Percent:"+ percent*100 + "%");
	averageprodictCount = predictCount/totalSequences;
	}
	public void learn(String current, String next)
	{
		System.out.println("~");
		if(probability.containsKey(current))
		{
			HashMap <String,Double> value = probability.get(current);
			if(value.containsKey(next))
			{
				Double val = value.get(next);
				value.put(next, val+1);
			}
			else
			{
				value.put(next, 1.0);
			}
		}
		else
		{
			HashMap<String,Double> learnMap = new HashMap<String, Double>();
			learnMap.put(next, 1.0);
			probability.put(current, learnMap);
		}
	}
	public ArrayList<String> findMaxProbabiltiy(String currentProduct)
	{
		HashMap<String,Double> nextPossibleProducts = new HashMap<String, Double>();
		nextPossibleProducts = probability.get(currentProduct);
		ArrayList<String> returnVal =  new ArrayList<String>();
		Double max = Double.MIN_VALUE;
		//System.out.println(currentProduct);
		try
		{
		Iterator mapIt = nextPossibleProducts.entrySet().iterator();
		while(mapIt.hasNext())
		{
			Map.Entry<String, Double> pair = (Map.Entry<String, Double>)mapIt.next();
			if(pair.getValue() > max)
			{
				returnVal =  new ArrayList<String>();
				max = pair.getValue();
				returnVal.add(pair.getKey());
				minSupport = max;
			}
			else if(pair.getValue() == max)
				returnVal.add(pair.getKey());
		/*	
			if(pair.getValue() > confThreshold)
				returnVal.add(pair.getKey());*/
		}}
		catch(Exception g)
		{
			//	System.out.println(g.getMessage());
			//System.out.println("Current Product:" +currentProduct);
			nullCount++;
		}
	return returnVal;
	}
	
	
	
	public void testModel()
	{
		Iterator it =testingData.entrySet().iterator();
		while(it.hasNext())
		{
			Map.Entry<Integer,ArrayList<String>> pair = (Map.Entry<Integer, ArrayList<String>>)it.next();
			ArrayList<String> products = pair.getValue();
			for(int i =0;i<products.size()-1;i++)
			{
				if(products.size()>1)
				{
					//System.out.println("Current Product:"+products.get(i));
					ArrayList<String> nextProd = trainModel(products.get(i));
					total1++;
					//System.out.println("List of Next Products");
//					for(int j =0; j<nextProd.size();j++)
//					{
//						System.out.println(nextProd.get(j));
//					}
					//System.out.println("Actual Next Product: " + products.get(i+1));
					if(nextProd.contains(products.get(i+1)))
						accuracy1++;
				//	System.out.println("********************");
					
				}
			}
		}
	}
	public ArrayList<String> trainModel(String currentProduct)
	{
		ArrayList<String> returnNextproduct = new ArrayList<String>();
		count = new HashMap<String, Integer>();
		Iterator it = trainingData.entrySet().iterator();
		while(it.hasNext())
		{
			Map.Entry<Integer, ArrayList<String>> pair = (Map.Entry<Integer, ArrayList<String>>)it.next();
			ArrayList<String> products = pair.getValue();
			if(products.contains(currentProduct))
			{
				int index = products.indexOf(currentProduct);
				if(index != products.size()-1)
				{
					String nextProduct = products.get(index+1);
					//System.out.println("Next Prod:"+nextProduct);
					if(count.containsKey(nextProduct))
					{
						
						int counter= count.get(nextProduct);
						count.put(nextProduct, counter+1);
						
					}
					else
					{
						count.put(nextProduct, 1);
					}
				}
			}
		}
		Iterator iter = count.entrySet().iterator();
		Integer maxCount = Integer.MIN_VALUE;
		//System.out.println("-------- ADDING---------");
		while(iter.hasNext())
		{
			Map.Entry<String, Integer> pair = (Map.Entry<String, Integer>)iter.next();
			if(pair.getValue() > maxCount)
			{
				//System.out.println(pair.getKey());
				returnNextproduct = new ArrayList<String>();
				returnNextproduct.add(pair.getKey());
				maxCount = pair.getValue();
			}
			else if(pair.getValue() == maxCount)
			{
			//	System.out.println(pair.getKey());
				maxCount = pair.getValue();
				returnNextproduct.add(pair.getKey());

			}
		}
	return returnNextproduct;
	}
	

}
