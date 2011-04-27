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
public class GetConferences {
	/**
	 * To get list name of conferences with ID of Author
	 * 
	 * Input is AUTHOR_ID and NUMBER OF CONFERENCES Output list of conferences
	 * name.
	 * 
	 * B1: Use Partten :
	 * http://academic.research.microsoft.com/Detail?entitytype
	 * =2&searchtype=3&id= B2: Get Number of Conferences: A B3: Get conferences
	 * name
	 * 
	 */
	public static ArrayList<String> getConferenceFromAuthorID(int authorId,int numCon) {
		ArrayList<String> conferences = new ArrayList<String>();
		int start = 1;
		int end = AcademicCrawlConst.MAX_NUMBER_SHOW_IN_PAGE;
		int step = AcademicCrawlConst.MAX_NUMBER_SHOW_IN_PAGE;
		int count = (numCon + 9) /step;
		while(count >= 0){
			try {
				String temp = null;
				String conference = null;
				temp = GetPageContent.getResults(new URL(
						AcademicCrawlConst.ACCADEMIC_CONFERENCE_QUERY + authorId + 
						AcademicCrawlConst.AND + 
						AcademicCrawlConst.START + "=" + start + 
						AcademicCrawlConst.AND + 
						AcademicCrawlConst.END + "=" + end));
				for (int i = 0; i < numCon; i++) {
					try{// try getting conference name from pattern tag id
						String conNum = String.valueOf(i);
						if (i < 10) {
							conNum = "0" + conNum;
						}
						conference = GetContentDIVTag.getTextOfDivTag(temp,
								AcademicCrawlConst.CONFERENCE_AND_JOURNAL_PATTERN_DIV.replaceAll(
										"\\(NUM\\)", conNum));
						conferences.add(conference);
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
		return conferences;
	}
	
//	public static void main(String arg[]) {
//		ArrayList<String> lst = new ArrayList<String>();
//		lst = GetConferences.getConferenceFromAuthorID(196415, 45);
//		for (int i = 0; i < lst.size(); i++) {
//			System.out.println(lst.get(i));
//		}
//		System.out.println(lst.size());
//	}
}
