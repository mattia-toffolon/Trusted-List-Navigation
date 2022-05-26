package proseccoCoding.TLN.model;

public class TrustedListFacade{
	private Query query;
	private TrustedListData trustedData;
	
	public TrustedListFacade(){
		query = null;
		trustedData = new TrustedListData();
	}
	
	public boolean startQuery(ArrayList<String> countryCodes){
		query = new Query();
	}
	
	public Query getQuery() {
		return query;
	}
	
	public boolean endQuery() {
		return true;
	}
	
	public TrustedListData getData() {
		return trustedData;
	}
}
