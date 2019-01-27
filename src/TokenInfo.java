import java.util.ArrayList;

public class TokenInfo {

	
	private double idf;
	private ArrayList<TokenOccurence> occList;
	
	
	public TokenInfo()
	{
		occList = new ArrayList<TokenOccurence>();
		idf = 0.0;
	}
	
	public double getIDF()
	{
		return idf;
	}
	
	public void setIDF(double idf)
	{
		this.idf = idf;
	}
	
	public void addOccurence(TokenOccurence toAdd)
	{
		occList.add(toAdd);
	}
	
	public int getOccListLength()
	{
		return occList.size();
	}
	
	public ArrayList<TokenOccurence> getOccList()
	{
		return occList;
	}
}
