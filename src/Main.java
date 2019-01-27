import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
	
	
	
	public static void main(String[] agrs)
	{

		//System.out.println(Stemmer.stem("librarian"));
		//Stopwords.printStopwords();
		//System.out.println(Stopwords.isStopword("with"));
		Crawler crawler = new Crawler();
		HashMap<String, TokenInfo> dictionary = crawler.buildIndex();
		//System.out.println(DocumentLengthTable.getDocLength(743));
		SearchEngine se = new SearchEngine();
	
		/*ArrayList<Integer> results = se.searchFromFile(23,dictionary);
		
		
		
		
		for(Integer doc:results)
		{
			System.out.println("Doc "+doc);
		}
		
		*/
		
		
		System.out.println("Enter a Search Query:");

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			
			
			String input = br.readLine();
			ArrayList<Integer> results = se.search(input,dictionary);
			
			
			for(Integer doc:results)
			{
				System.out.println("Doc "+doc);
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	
		
	
		
		
	}

}
