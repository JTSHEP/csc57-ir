
public class TokenOccurence {
	
	private int count;
	private DocumentReference docRef;
	
	public TokenOccurence(DocumentReference docRef, int count)
	{
		this.count = count;
		this.docRef = docRef;
	}
	
	public int getCount()
	{
		return count;
	}
	
	public DocumentReference getDocumentReference()
	{
		return docRef;
	}

}
