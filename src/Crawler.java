import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Crawler {
	
	//HashMap that will hold the dictionary. Maps Tonkens to the TokenInfo Object.
	private HashMap<String, TokenInfo> dictionary;
	
	
	//Constructor. The dictionary is intalized.
	public Crawler()
	{
		dictionary = new HashMap<String, TokenInfo>();	
	}
	
	
	//private method that will collect all the documents from a file. Designed to be called
	//mulitple times with different filenames, as the corpus is split into multiple files.
	private void collectFromDoc(String fileName)
	{
		try
		{
			//this is the document vector that will hold the raw occurences of each token in 
			//each document.
			HashMap<String, Integer> HashMapVector = new HashMap<String, Integer>();
			FileInputStream fin=new FileInputStream(fileName);  
		 
			Scanner sc = new Scanner(fin);
			sc.next();
			int docNum = sc.nextInt();
			
			while(sc.hasNext())
			{
        	 
				String t = sc.next();
        	
				//if this token is retreived, it is the end of the current document.
				//we add the document vector to the dictionary, and re-initalize the 
				//HMV.
				if(t.equals("********************************************"))
				{
					addDoctoDict(HashMapVector, docNum);
					if(sc.hasNext())
					{
						sc.next();
						docNum = sc.nextInt();
						HashMapVector = new HashMap<String, Integer>();
						continue;
					}
				}
        	
				//if the next token is not a stopword, we stem it, and add it to the HMV
				if(!Stopwords.isStopword(t))
				{
					String stem = Stemmer.stem(t);
					if(stem.equals("") || stem.equals(" "))
						continue;
					if(Stopwords.isStopword(stem))
						continue;
					if(HashMapVector.containsKey(stem))
					{
						HashMapVector.put(stem, HashMapVector.get(stem)+1);
					}
					else
					{
						HashMapVector.put(stem, 1);
					}
        		
				}
			}
            
			sc.close();
			fin.close();    
		}
		catch(Exception e)
		{
			System.out.println(e);
		}  
	}
	

	//A HMV gathered from a document is passed into this method, and it is added to the dictionary/inverted index.
	private void addDoctoDict(HashMap<String,Integer> hashMapVector, int docNum)
	{
		//System.out.println("Recieved document "+docNum);
		DocumentReference dr = new DocumentReference(docNum,0);
		
		for (HashMap.Entry<String, Integer> entry : hashMapVector.entrySet()) 
		{
			if(!dictionary.containsKey(entry.getKey()))
				{
					TokenInfo toAdd = new TokenInfo();
					dictionary.put(entry.getKey(), toAdd);
				}
			TokenOccurence toAdd = new TokenOccurence(dr,entry.getValue());
			dictionary.get(entry.getKey()).addOccurence(toAdd);
		}
	}
	
	
	//collects data from all the documents making up the corpus.
	public HashMap<String,TokenInfo> buildIndex()
	{
		collectFromDoc("data/LISA0.001");
		collectFromDoc("data/LISA0.501");
		collectFromDoc("data/LISA1.001");
		collectFromDoc("data/LISA1.501");
		collectFromDoc("data/LISA2.001");
		collectFromDoc("data/LISA2.501");
		collectFromDoc("data/LISA3.001");
		collectFromDoc("data/LISA3.501");
		collectFromDoc("data/LISA4.001");
		collectFromDoc("data/LISA4.501");
		collectFromDoc("data/LISA5.001");
		collectFromDoc("data/LISA5.501");
		collectFromDoc("data/LISA5.627");
		collectFromDoc("data/LISA5.850");
		
		
		//after the data is collected, we go through the dictionaty again to compute idf values.
		for (HashMap.Entry<String, TokenInfo> entry : dictionary.entrySet()) 
		{
			//hardcoded to input. fix later.
			double idf = Math.log(5872/(entry.getValue().getOccListLength()))/Math.log(2);
			entry.getValue().setIDF(idf);
			
			
			for(TokenOccurence occ : entry.getValue().getOccList())
			{
				int docNum = occ.getDocumentReference().getDocNum();
				int c =  occ.getCount();
				DocumentLengthTable.addToLength(docNum, Math.pow((c*idf), 2));
			}
			
		}
		
		DocumentLengthTable.doneAdding();
		
		return dictionary;
		
		
	}
	
	

}
