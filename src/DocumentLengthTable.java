import java.util.HashMap;

//This static class stores the data for document lengths, which is needed
//to normalize results. It has a static initializer to initialize values for
//all documents to 0. The Crawler then increments the length by the appropriate
//weight during the crawling. Finally, when all data is indexed, the crawler
//calls the doneAdding() method, which finishes the calculation, and sets the
//flag that allows data to be retrieved to true.


public class DocumentLengthTable {

	
	private static HashMap<Integer, Double> data;
	private static boolean computed;
	
	static
	{
		data = new HashMap<Integer, Double>();
		computed = false;
	}
	
	//A call to determing the length of a document. Will Throw an Error if
	//this information is not yet computed.
	public static Double getDocLength(Integer docNum)
	{
		if(!computed)
			throw new Error();
		
		if(!data.containsKey(docNum))
		{
			return 0.0;
		}
		
		else
		{
			return data.get(docNum);
		}
	}
	
	public static void addToLength(Integer docNum, Double toAdd)
	{
		if(!data.containsKey(docNum))
		{
			data.put(docNum, toAdd);
		}
		
		else
		{
			data.put(docNum, data.get(docNum)+toAdd);
		}
	}
	
	
	//this method being called signifies that the data has all been crawled and is ready to be finalized.
	public static void doneAdding()
	{
		
		for (Integer doc : data.keySet()) {
		    data.put(doc, Math.sqrt(data.get(doc)));
		}
		
		computed = true;
	
	}
	
	
}

