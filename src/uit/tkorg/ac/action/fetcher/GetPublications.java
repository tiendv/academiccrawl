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
public class GetPublications {
	
	public static ArrayList<String> getPublicationFromAuthorID(int authorId,int numPub) {
		ArrayList<String> publications = new ArrayList<String>();
		int start = 1;
		int end = AcademicCrawlConst.MAX_NUMBER_SHOW_IN_PAGE;
		int step = AcademicCrawlConst.MAX_NUMBER_SHOW_IN_PAGE;
		int count = (numPub + 9) /step;
		while(count >= 0){
			try {
				String temp = null;
				String publication = null;
				temp = GetPageContent.getResults(new URL(
						AcademicCrawlConst.ACCADEMIC_PUBLICATION_QUERY + authorId + 
						AcademicCrawlConst.AND + 
						AcademicCrawlConst.START + "=" + start + 
						AcademicCrawlConst.AND + 
						AcademicCrawlConst.END + "=" + end));
				
				for (int i = 0; i < numPub; i++) {
					try{// try getting co-author name from pattern tag id
					String conNum = String.valueOf(i);
					if (i < 10) {
						conNum = "0" + conNum;
					}
					publication = GetContentDIVTag.getTextOfDivTag(temp,
							AcademicCrawlConst.PUBLICATION_PATTERN_DIV.replaceAll(
									"\\(NUM\\)", conNum));
					publications.add(publication);
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
		return publications;
	}
	
//	public static void main(String arg[]) {
//		ArrayList<String> lst = new ArrayList<String>();
//		lst = GetPublications.getPublicationFromAuthorID(196415, 441);
//		for (int i = 0; i < lst.size(); i++) {
//			System.out.println(lst.get(i));
//		}
//		System.out.println(lst.size());
//	}
}
