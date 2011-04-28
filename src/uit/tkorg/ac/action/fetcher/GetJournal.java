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
public class GetJournal {

	/**
	 * 
	 * @param authorId
	 *            : Author ID
	 * @param numJour
	 *            : Number of Jounral
	 * @return list Journal
	 */
	public static ArrayList<String> getJournalFromAuthorID(int authorId,
			int numJour) {
		ArrayList<String> journals = new ArrayList<String>();
		int start = 1;
		int end = AcademicCrawlConst.MAX_NUMBER_SHOW_IN_PAGE;
		int numJourPerPage = AcademicCrawlConst.MAX_NUMBER_SHOW_IN_PAGE;
		int numPage = (numJour) / numJourPerPage;
		for (int i = 0; i <= numPage; i++) {
			int MaxRunPerPage = numJourPerPage;
			if (i == numPage) {
				MaxRunPerPage = numJour - numJourPerPage * numPage;
			}
			try {
				String temp = null;
				String journal = null;
				temp = GetPageContent.getResults(new URL(
						AcademicCrawlConst.ACCADEMIC_JOURNAL_QUERY + authorId
								+ AcademicCrawlConst.AND
								+ AcademicCrawlConst.START + "=" + start
								+ AcademicCrawlConst.AND
								+ AcademicCrawlConst.END + "=" + end));
				for (int j = 0; j < MaxRunPerPage; j++) {
					// try getting journal name from pattern tag id
					String conNum = String.valueOf(j);
					if (j < 10) {
						conNum = "0" + conNum;
					}
					journal = GetContentDIVTag
							.getTextOfDivTag(
									temp,
									AcademicCrawlConst.CONFERENCE_AND_JOURNAL_AND_KEYWORD_PATTERN_DIV
											.replaceAll("\\(NUM\\)", conNum));
					journals.add(journal);
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			start = start + numJourPerPage;
			end = end + numJourPerPage;
		}
		return journals;
	}

}
