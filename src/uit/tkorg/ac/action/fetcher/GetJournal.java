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
public class GetJournal {
	
	public static ArrayList<String> getJournalFromAuthorID(int authorId,int numJour) {
		ArrayList<String> journals = new ArrayList<String>();
		int start = 1;
		int end = numJour;
		int step = AcademicCrawlConst.MAX_NUMBER_SHOW_IN_PAGE;
		int count = (numJour + 9) /step;
		while(count >= 0){
			try {
				String temp = null;
				String journal = null;
				temp = GetPageContent.getResults(new URL(
						AcademicCrawlConst.ACCADEMIC_JOURNAL_QUERY + authorId + 
						AcademicCrawlConst.AND + 
						AcademicCrawlConst.START + "=" + start + 
						AcademicCrawlConst.AND + 
						AcademicCrawlConst.END + "=" + end));
				for (int i = 0; i < numJour; i++) {
					try{// try getting journal name from pattern tag id
					String conNum = String.valueOf(i);
					if (i < 10) {
						conNum = "0" + conNum;
					}
					journal = GetContentDIVTag.getTextOfDivTag(temp,
							AcademicCrawlConst.CONFERENCE_AND_JOURNAL_PATTERN_DIV.replaceAll(
									"\\(NUM\\)", conNum));
					journals.add(journal);
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
		return journals;
	}

}
