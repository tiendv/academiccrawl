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
		int step = AcademicCrawlConst.MAX_NUMBER_SHOW_IN_PAGE;
		int count = (numKey) /step;
		while(count >= 0){
			try {
				String temp = null;
				String keyword = null;
				temp = GetPageContent.getResults(new URL(
						AcademicCrawlConst.ACCADEMIC_KEYWORD_QUERY + authorId + 
						AcademicCrawlConst.AND + 
						AcademicCrawlConst.START + "=" + start + 
						AcademicCrawlConst.AND + 
						AcademicCrawlConst.END + "=" + end));
				for (int i = 0; i < numKey; i++) {
					try{// try getting journal name from pattern tag id
					String conNum = String.valueOf(i);
					if (i < 10) {
						conNum = "0" + conNum;
					}
					keyword = GetContentDIVTag.getTextOfDivTag(temp,
							AcademicCrawlConst.CONFERENCE_AND_JOURNAL_AND_KEYWORD_PATTERN_DIV.replaceAll(
									"\\(NUM\\)", conNum));
					keywords.add(keyword);
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
