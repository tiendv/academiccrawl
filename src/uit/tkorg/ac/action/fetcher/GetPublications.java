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
public class GetPublications {
	
	/**
	 * TO get list of Title Publicaiton  
	 * @param authorId : Author ID
	 * @param numPub : Number of publication
	 * @return : list of title publication
	 */
	public static ArrayList<String> getPublicationFromAuthorID(int authorId,int numPub) {
		ArrayList<String> publications = new ArrayList<String>();
		int start = 1;
		int end = AcademicCrawlConst.MAX_NUMBER_SHOW_IN_PAGE;
		int numPubPerPage = AcademicCrawlConst.MAX_NUMBER_SHOW_IN_PAGE;
		int numPage = (numPub) /numPubPerPage;
		for (int i = 0; i <= numPage; i++) {
			int MaxRunPerPage = numPubPerPage;
			if (i == numPage) {
				MaxRunPerPage = numPub - numPubPerPage * numPage;
			}
			try {
				String temp = null;
				String publication = null;
				temp = GetPageContent.getWithUTF8( new URL(
						AcademicCrawlConst.ACCADEMIC_PUBLICATION_QUERY + authorId + 
						AcademicCrawlConst.AND + 
						AcademicCrawlConst.START + "=" + start + 
						AcademicCrawlConst.AND + 
						AcademicCrawlConst.END + "=" + end));
				
				for (int j = 0; j < MaxRunPerPage; j++) {
					// try getting co-author name from pattern tag id
					String conNum = String.valueOf(j);
					if (j < 10) {
						conNum = "0" + conNum;
					}
					publication = GetContentDIVTag.getTextOfDivTag(temp,
							AcademicCrawlConst.PUBLICATION_PATTERN_DIV.replaceAll(
									"\\(NUM\\)", conNum));
					publications.add(publication);
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			start = start + numPubPerPage;
			end = end + numPubPerPage;
		}
		return publications;
	}
	
/*	public static void main(String arg[]) {
		ArrayList<String> lst = new ArrayList<String>();
		lst = GetPublications.getPublicationFromAuthorID(866448, 81);
		for (int i = 0; i < lst.size(); i++) {
			System.out.println(lst.get(i));
		}
		
		System.out.println(lst.size());
	}*/
}
