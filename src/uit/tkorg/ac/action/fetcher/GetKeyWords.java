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
public class GetKeyWords {
	/**
	 * 
	 * @param authorId : Author ID
	 * @param numKey : number of Keywords
	 * @return list of Keyword
	 */
	
	public static ArrayList<String> getKeyWordFromAuthorID(int authorId,int numKey) {
		ArrayList<String> keywords = new ArrayList<String>();
		int start = 1;
		int end = AcademicCrawlConst.MAX_NUMBER_SHOW_IN_PAGE;
		int numKeyPerPage = AcademicCrawlConst.MAX_NUMBER_SHOW_IN_PAGE;
		int numPage = (numKey) /numKeyPerPage;
		for (int i = 0; i <= numPage; i++) {
			int MaxRunPerPage = numKeyPerPage;
			if (i == numPage) {
				MaxRunPerPage = numKey - numKeyPerPage * numPage;
			}
			try {
				String temp = null;
				String keyword = null;
				temp = GetPageContent.getResults(new URL(
						AcademicCrawlConst.ACCADEMIC_KEYWORD_QUERY + authorId + 
						AcademicCrawlConst.AND + 
						AcademicCrawlConst.START + "=" + start + 
						AcademicCrawlConst.AND + 
						AcademicCrawlConst.END + "=" + end));
				for (int j = 0; j < MaxRunPerPage; j++) {
					// try getting journal name from pattern tag id
					String conNum = String.valueOf(j);
					if (j < 10) {
						conNum = "0" + conNum;
					}
					keyword = GetContentDIVTag.getTextOfDivTag(temp,
							AcademicCrawlConst.CONFERENCE_AND_JOURNAL_AND_KEYWORD_PATTERN_DIV.replaceAll(
									"\\(NUM\\)", conNum));
					keywords.add(keyword);
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			start = start + numKeyPerPage;
			end = end + numKeyPerPage;
		}
		return keywords;
	}
	
/*	public static void main(String arg[]) {
	ArrayList<String> lst = new ArrayList<String>();
	lst = GetKeyWords.getKeyWordFromAuthorID(70591, 163);
	for (int i = 0; i < lst.size(); i++) {
		System.out.println(lst.get(i));
	}
	System.out.println(lst.size());
}*/
}
