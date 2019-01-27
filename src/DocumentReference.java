
public class DocumentReference {

	private int docNum = -1;
	private double length = 0.0;
	
	public DocumentReference(int docNum, double length)
	{
		this.docNum=docNum;
		this.length = length;
	}
	
	public int getDocNum()
	{
		return docNum;
	}
	
	public double getLength()
	{
		return length;
	}
}
