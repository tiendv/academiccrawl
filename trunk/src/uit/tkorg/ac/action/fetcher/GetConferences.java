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
 * @author tiendv, hoangnt
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
	public static ArrayList<String> getConferenceFromAuthorID(int authorId,
			int numCon) {
		ArrayList<String> conferences = new ArrayList<String>();
		int start = 1;
		int end = AcademicCrawlConst.MAX_NUMBER_SHOW_IN_PAGE;
		int numConPerPage = AcademicCrawlConst.MAX_NUMBER_SHOW_IN_PAGE;
		int numPage = (numCon) / numConPerPage;

		for (int i = 0; i <= numPage; i++) {
			int MaxRunPerPage = numConPerPage;
			if (i == numPage) {
				MaxRunPerPage = numCon - numConPerPage * numPage;
			}
			try {
				String temp = null;
				String conference = null;
				temp = GetPageContent.getResults(new URL(
						AcademicCrawlConst.ACCADEMIC_CONFERENCE_QUERY
								+ authorId + AcademicCrawlConst.AND
								+ AcademicCrawlConst.START + "=" + start
								+ AcademicCrawlConst.AND
								+ AcademicCrawlConst.END + "=" + end));
				for (int j = 0; j < MaxRunPerPage; j++) {
					// try getting conference name from pattern tag id
					String conNum = String.valueOf(j);
					if (j < 10) {
						conNum = "0" + conNum;
					}
					conference = GetContentDIVTag
							.getTextOfDivTag(
									temp,
									AcademicCrawlConst.CONFERENCE_AND_JOURNAL_AND_KEYWORD_PATTERN_DIV
											.replaceAll("\\(NUM\\)", conNum));
					conferences.add(conference);
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			start = start + numConPerPage;
			end = end + numConPerPage;
		}
		return conferences;
	}

/*	public static void main(String arg[]) {
		ArrayList<String> lst = new ArrayList<String>();
		lst = GetConferences.getConferenceFromAuthorID(196415, 45);
		for (int i = 0; i < lst.size(); i++) {
			System.out.println(lst.get(i));
		}
		System.out.println(lst.size());
	}*/
}
