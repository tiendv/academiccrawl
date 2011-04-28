/**
 * 
 */
package uit.tkorg.ac.action.fetcher;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import uit.tkorg.ac.htmlpaser.GetContentDIVTag;
import uit.tkorg.ac.properties.file.AcademicCrawlConst;

/**
 * @author tiendv,hoangnt
 * 
 */
public class GetCoAuthor {
	/**
	 * To get list name of coAuthor with ID of Author
	 * 
	 * Input is AUTHOR_ID and NUMBER OF CO-AUTHOR Output list of author name.
	 * 
	 * B1: Use Partten :
	 * http://academic.research.microsoft.com/Detail?entitytype
	 * =2&searchtype=1&id= 
	 * B2: Get Number of CoAuthor: A 
	 * B3: Get author name
	 * 
	 * if numberofAuthor >10 Get htmlcontent to get list name. else : one time
	 * get 10 name to end use this parten:
	 * 
	 * http://academic.research.microsoft.com/Detail?entitytype=2&searchtype=1&
	 * id=ID&start=1&end=A
	 * 
	 */
	
	
	/**
	 *  TO get CoAuthor from authorID and number of CoAuthor.
	 */
	
	public static ArrayList<String> getCoAuthorFromAuthorID(int authorId,int numCo) {
		ArrayList<String> lisCoAuthorName = new ArrayList<String>();
		int start = 1;
		int end = AcademicCrawlConst.MAX_NUMBER_SHOW_IN_PAGE;
		int numAuthorPerPage = AcademicCrawlConst.MAX_NUMBER_SHOW_IN_PAGE;
		int numPage = (numCo) /numAuthorPerPage ;
		
		for (int i = 0; i <= numPage; i++) {
			int MaxRunPerPage = numAuthorPerPage;
			if(i==numPage){
				MaxRunPerPage = numCo-numAuthorPerPage*numPage;
			}
			try {
				String temp = null;
				String author = null;
				
				temp = GetPageContent.getResults(new URL(
						AcademicCrawlConst.ACCADEMIC_COAUTHOR_QUERY + authorId +
						AcademicCrawlConst.AND+
						AcademicCrawlConst.START + "=" +start +
						AcademicCrawlConst.AND+
						AcademicCrawlConst.END + "=" + end));
				for (int j = 0; j < MaxRunPerPage; j++) {					
					// try getting co-author name from pattern tag id					
					String auNum = 	String.valueOf(j);				
					if(j<10){
						auNum = "0"+auNum;
					}				
					author = GetContentDIVTag.getTextOfDivTag(temp,
							AcademicCrawlConst.COAUTHOR_PATTERN_DIV.replaceAll("\\(NUM\\)",auNum));
					lisCoAuthorName.add(author);		
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			start = start + numAuthorPerPage;
			end = end + numAuthorPerPage;
		}
		return lisCoAuthorName;
	}

/*	public static void main(String arg[]) {
		ArrayList<String> lst = new ArrayList<String>();
//		lst = GetCoAuthor.getCoAuthorFromAuthorID(866448, 95);
		lst = GetCoAuthor.getCoAuthorFromAuthorID(1844728, 112);
//		lst = GetCoAuthor.getCoAuthorFromAuthorID(2442423, 151);
//		lst = GetCoAuthor.getCoAuthorFromAuthorID(409109, 108);
		for (int i = 0; i < lst.size(); i++) {
			System.out.println(lst.get(i));
		}
		System.out.println(lst.size());
	}*/

}
