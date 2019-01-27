import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Stopwords {

	private static ArrayList<String> stopwords = new ArrayList<String>();
	static
	{
	     try
	     	{    
	            FileInputStream fin=new FileInputStream("data/stopwords.txt");   
	            Scanner sc = new Scanner(fin);
	            while(sc.hasNext())
	            {
	            	stopwords.add(sc.nextLine().trim());
	            }
	               
	  
	            sc.close();
	            fin.close();    
	     	}
	     catch(Exception e)
	     	{
	    	 System.out.println(e);
	     	}   
	     
	}
	
	
	public static boolean isStopword(String x)
	{
		x = x.toLowerCase();
		return stopwords.contains(x);
	}
	
	public static void printStopwords()
	{
		for (String word:stopwords)
		{
			System.out.println(word);
		}
	}
	
}
