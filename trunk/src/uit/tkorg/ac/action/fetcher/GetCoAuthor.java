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
 * @author tiendv
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
	public static ArrayList<String> getCoAuthorFromAuthorID(int authorId,int numCo) {
		ArrayList<String> lisCoAuthorName = new ArrayList<String>();
		int start = 1;
		int end = AcademicCrawlConst.MAX_NUMBER_SHOW_IN_PAGE;
		int step = AcademicCrawlConst.MAX_NUMBER_SHOW_IN_PAGE;
		int count = (numCo) /step ;
		
		while(count >= 0){
			try {
				String temp = null;
				String author = null;
				temp = GetPageContent.getResults(new URL(
						AcademicCrawlConst.ACCADEMIC_COAUTHOR_QUERY + authorId +
						AcademicCrawlConst.AND+
						AcademicCrawlConst.START + "=" +start +
						AcademicCrawlConst.AND+
						AcademicCrawlConst.END + "=" + end));
				for (int i = 0; i < step; i++) {					
					try{// try getting co-author name from pattern tag id
					String auNum = 	String.valueOf(i);				
					if(i<10){
						auNum = "0"+auNum;
					}				
					author = GetContentDIVTag.getTextOfDivTag(temp,
							AcademicCrawlConst.COAUTHOR_PATTERN_DIV.replaceAll("\\(NUM\\)",auNum));
					lisCoAuthorName.add(author);		
					} catch (Exception e) {
						// do nothing
					}
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			count--;
			start = start + step;
			end = end + step;
		};
		return lisCoAuthorName;
	}

	public static void main(String arg[]) {
		ArrayList<String> lst = new ArrayList<String>();
		lst = GetCoAuthor.getCoAuthorFromAuthorID(866448, 95);
		/*lst = GetCoAuthor.getCoAuthorFromAuthorID(1844728, 112);
		lst = GetCoAuthor.getCoAuthorFromAuthorID(2442423, 151);
		lst = GetCoAuthor.getCoAuthorFromAuthorID(409109, 108);*/
		for (int i = 0; i < lst.size(); i++) {
			System.out.println(lst.get(i));
		}
		System.out.println(lst.size());
	}

}
