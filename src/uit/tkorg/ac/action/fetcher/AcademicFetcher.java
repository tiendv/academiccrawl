
package uit.tkorg.ac.action.fetcher;

/**
 * @author tiendv
 *
 */
public class AcademicFetcher {
	
	
	/**
	 * String to get ID from academic when get Author do't know ID 
	 */
	private static String startGetUrlNOID = "http://academic.research.microsoft.com/Search?query=";
	
	//Keyword space = %20
	//Example http://academic.research.microsoft.com/Search?query=tin%20huynh
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String keyworld = "tin huynh";
		keyworld = keyworld.replaceAll(" ", "%20");
		// TODO Auto-generated method stub
		
	}

}
