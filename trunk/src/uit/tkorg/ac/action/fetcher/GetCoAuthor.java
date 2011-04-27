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
	 * =2&searchtype=1&id= B2: Get Number of CoAuthor: A B3: Get author name
	 * 
	 * if numberofAuthor >10 Get htmlcontent to get list name. else : one time
	 * get 10 name to end use this parten:
	 * 
	 * http://academic.research.microsoft.com/Detail?entitytype=2&searchtype=1&
	 * id=ID&start=X&end=Y
	 * 
	 * X to Y with Y<a
	 * 
	 */
	public static ArrayList<String> getCoAuthorFromAuthorID(int authorId,int numCo) {
		ArrayList<String> lisCoAuthorName = new ArrayList<String>();
		int start = 1;
		int end = numCo;
		
		try {
			String temp = null;
			String author = null;
			temp = GetPageContent.getResults(new URL(
					AcademicCrawlConst.ACCADEMIC_COAUTHOR_URL + authorId +
					AcademicCrawlConst.AND+
					AcademicCrawlConst.START + "=" +start +
					AcademicCrawlConst.AND+
					AcademicCrawlConst.END + "=" + end));
			for (int i = 0; i < numCo; i++) {
				// get co-author name from pattern tag id
				String auNum = 	String.valueOf(i);				
				if(i<10){
					auNum = "0"+auNum;
				}				
				author = GetContentDIVTag.getTextOfDivTag(temp,
						AcademicCrawlConst.COAUTHOR_PATTERN.replaceAll("\\(NUM\\)",auNum));
				lisCoAuthorName.add(author);				
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lisCoAuthorName;
	}

//	public static void main(String arg[]) {
//		ArrayList<String> lst = new ArrayList<String>();
//		lst = GetCoAuthor.getCoAuthorFromAuthorID(866448, 95);
//		for (int i = 0; i < lst.size(); i++) {
//			System.out.println(lst.get(i));
//		}
//	}

}
