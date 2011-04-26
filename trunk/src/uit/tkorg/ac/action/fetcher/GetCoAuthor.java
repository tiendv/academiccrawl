/**
 * 
 */
package uit.tkorg.ac.action.fetcher;

import java.util.ArrayList;

/**
 * @author tiendv
 *
 */
public class GetCoAuthor {
	public static ArrayList<String> getCoAuthorFromAuthorID (int id) {
		/**
		 * To get list name of coAuthor with ID of Author
		 * 
		 * Input is ID 
		 * Output list of author name.
		 * 
		 * B1: Use Partten : http://academic.research.microsoft.com/Detail?entitytype=2&searchtype=1&id=
		 * B2: Get Number of CoAuthor: A
		 * B3: Get author name
		 *
		 * if numberofAuthor >10 Get htmlcontent to get list name.
		 * else : one time get 10 name to end use this parten:
		 * 
		 * http://academic.research.microsoft.com/Detail?entitytype=2&searchtype=1&id=ID&start=X&end=Y
		 * 
		 * X to Y with Y<a
		 * 
		 */
		ArrayList<String> lisCoAuthorName = new ArrayList<String>();
		return null;
		
	}

}
