		import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class SearchEngine {
	
	private String queryFile = "data/LISA.QUE";
	private HashMap<String,Integer> query;
	
	public ArrayList<Integer> search(String q, HashMap<String,TokenInfo> dictionary)
	{
		
		ArrayList<Integer> toReturn = new ArrayList<Integer>();
		
		
		String[] tokens = q.split(" ");
		HashMap<String, Integer> queryVector = new HashMap<String,Integer>();
		
		for(int ii = 0; ii<tokens.length;ii++)
		{
			String t = tokens[ii];
			if(Stopwords.isStopword(t))
				continue;
			
			String stem = Stemmer.stem(t);
			if(Stopwords.isStopword(stem))
				continue;
		
			
			if(queryVector.containsKey(stem))
			{
				queryVector.put(stem, queryVector.get(stem)+1);
			}
			else
			{
				queryVector.put(stem, 1);
			}
		}
			
			query = queryVector;
			
			HashMap<Integer,Double> R = new HashMap<Integer,Double>();
			double queryLength = 0;
			
			for (HashMap.Entry<String, Integer> entry : query.entrySet()) 
			{
				
				if(!dictionary.containsKey(entry.getKey()))
				{
					//query term not found in dict.
					continue;
				}
				
				//idf of current term in the query
				double i = dictionary.get(entry.getKey()).getIDF();
				int k = entry.getValue();
				
				//weight of term is calculated as the idf * the term frequency (in the query)
				double w = k*i;
				
				//The square of the weight is added to the length of the query. Will be sqrt'd below
				queryLength+=(w*w);
				
				//gathers the list of occurences in the dictionary for the current token
				ArrayList<TokenOccurence> l = dictionary.get(entry.getKey()).getOccList();
				
				for(TokenOccurence o : l)
				{
					DocumentReference d = o.getDocumentReference();
					int c = o.getCount();
					
					if(!R.containsKey(d.getDocNum()))
					{
						R.put(d.getDocNum(), 0.0);
					}
					
					//increments the score of the document that the term was found in by the weight of the
					//term in the query times the weight of the term in the document.
					R.put(d.getDocNum(), R.get(d.getDocNum())+(w*c*i));
				}
			}
			
			queryLength = Math.sqrt(queryLength);
			
			//completes the cosine similarity calculation by normalizing the dot product.
			for(HashMap.Entry<Integer,Double> entry : R.entrySet())
			{
				double s = entry.getValue();
				double y = DocumentLengthTable.getDocLength(entry.getKey());
				R.put(entry.getKey(), s/(y*queryLength));
			}
			
			
			//returns all documents with a score above a determined similarity measure.
			for(HashMap.Entry<Integer,Double> entry : R.entrySet())
			{
				if(entry.getValue()>.2)
				{
					toReturn.add(entry.getKey());
				}
			}
			
			
			return toReturn;
			
		
	}
	
	public ArrayList<Integer> searchFromFile(int queryNum, HashMap<String,TokenInfo> dictionary)
	{
		ArrayList<Integer> toReturn = new ArrayList<Integer>();
		
		
		try
		{
			FileInputStream fin=new FileInputStream(queryFile);  	 
			Scanner sc = new Scanner(fin);
			
			int currentQuery = sc.nextInt();
			while(currentQuery!=queryNum)
			{
				while(!sc.next().equals("#"))
				{
					;
				}
				currentQuery = sc.nextInt();
				
			
			}
			
		
			HashMap<String, Integer> queryVector = new HashMap<String,Integer>();
			
			String t = (sc.next());
			
		
			while(!t.equals("#"))
			{
			
				if(Stopwords.isStopword(t))
				{
					t = sc.next();
					continue;
					
				}
				String stem = Stemmer.stem(t);
				if(Stopwords.isStopword(stem))
				{
					t = sc.next();
					continue;
				}
				
				if(queryVector.containsKey(stem))
				{
					queryVector.put(stem, queryVector.get(stem)+1);
				}
				else
				{
					queryVector.put(stem, 1);
				}
				
				t = sc.next();
			}
			
		
			
			query = queryVector;
			
			sc.close();
			fin.close(); 
		}
		catch(Exception e)
		{
			System.out.println(e);
		}  

		

		//this is the hashmap that will store the scores of all documents.
		//Document Numbers are matched to a similarity score
		HashMap<Integer,Double> R = new HashMap<Integer,Double>();
		
		double queryLength = 0;
	
		for (HashMap.Entry<String, Integer> entry : query.entrySet()) 
		{
			
			if(!dictionary.containsKey(entry.getKey()))
			{
				//query term not found in dict.
				continue;
			}
			
			//idf of current term in the query
			double i = dictionary.get(entry.getKey()).getIDF();
			int k = entry.getValue();
			
			//weight of term is calculated as the idf * the term frequency (in the query)
			double w = k*i;
			
			//The square of the weight is added to the length of the query. Will be sqrt'd below
			queryLength+=(w*w);
			
			//gathers the list of occurences in the dictionary for the current token
			ArrayList<TokenOccurence> l = dictionary.get(entry.getKey()).getOccList();
			
			for(TokenOccurence o : l)
			{
				DocumentReference d = o.getDocumentReference();
				int c = o.getCount();
				
				if(!R.containsKey(d.getDocNum()))
				{
					R.put(d.getDocNum(), 0.0);
				}
				
				//increments the score of the document that the term was found in by the weight of the
				//term in the query times the weight of the term in the document.
				R.put(d.getDocNum(), R.get(d.getDocNum())+(w*c*i));
			}
		}
		
		queryLength = Math.sqrt(queryLength);
		
		//completes the cosine similarity calculation by normalizing the dot product.
		for(HashMap.Entry<Integer,Double> entry : R.entrySet())
		{
			double s = entry.getValue();
			double y = DocumentLengthTable.getDocLength(entry.getKey());
			R.put(entry.getKey(), s/(y*queryLength));
		}
		
		
		//returns all documents with a score above a determined similarity measure.
		for(HashMap.Entry<Integer,Double> entry : R.entrySet())
		{
			if(entry.getValue()>.2)
			{
				toReturn.add(entry.getKey());
			}
		}
		
		
		
	
		
		
		
		return toReturn;
	}
}
