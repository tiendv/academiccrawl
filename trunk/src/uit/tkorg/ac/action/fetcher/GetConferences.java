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
		int end = numCon;

		try {
			String temp = null;
			String conference = null;
			temp = GetPageContent.getResults(new URL(
					AcademicCrawlConst.ACCADEMIC_CONFERENCE_URL + authorId + 
					AcademicCrawlConst.AND + 
					AcademicCrawlConst.START + "=" + start + 
					AcademicCrawlConst.AND + 
					AcademicCrawlConst.END + "=" + end));
			for (int i = 0; i < numCon; i++) {
				// get conferences name from pattern tag id
				String conNum = String.valueOf(i);
				if (i < 10) {
					conNum = "0" + conNum;
				}
				conference = GetContentDIVTag.getTextOfDivTag(temp,
						AcademicCrawlConst.CONFERENCE_PATTERN.replaceAll(
								"\\(NUM\\)", conNum));
				conferences.add(conference);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return conferences;
	}

}
