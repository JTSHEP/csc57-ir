//This stemming tool is part of the OPENNLP Java library by Apache.
//OPENNLP contains various natural language proccessing tools, including 
//porter stemming.


import opennlp.tools.stemmer.PorterStemmer;

public class Stemmer {

	
	
	
	public static String stem(String arg)
	{
		arg = arg.toLowerCase();
		//removes possession
		arg = arg.replaceAll("'s", "");
		
		//removes all non-letter characters, specifically punctuation.
		arg = arg.replaceAll("[^a-zA-Z ]", "");
		
		PorterStemmer stemmer = new PorterStemmer();
		return stemmer.stem(arg);
	}
}
