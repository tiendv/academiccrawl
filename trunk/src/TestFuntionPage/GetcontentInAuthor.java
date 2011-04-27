/**
 * 
 */
package TestFuntionPage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import uit.tkorg.ac.action.fetcher.GetPageContent;
import uit.tkorg.ac.core.fetcher.AcademicFetcherCore;
import uit.tkorg.ac.htmlpaser.GetContentDIVTag;

/**
 * @author tiendv
 *
 */
public class GetcontentInAuthor {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		URL test = null;
		String urlAuthor = "http://academic.research.microsoft.com/Author/257943";
		String urlPublision = "http://academic.research.microsoft.com/Detail?entitytype=2&searchtype=2&id=866448";
		String urlCorAuthor = "http://academic.research.microsoft.com/Detail?searchtype=1&entitytype=2&id=866448";
		String urlConfer = "http://academic.research.microsoft.com/Detail?searchtype=3&entitytype=2&id=866448";
		String urlJourNal = "http://academic.research.microsoft.com/Detail?searchtype=4&entitytype=2&id=866448";
		String urlKeyword = "http://academic.research.microsoft.com/Detail?searchtype=9&entitytype=2&id=866448"; 
		
		// regular to get a number from String : ([\+-]?\d+)([eE][\+-]?\d+)?
		try {
			test = new URL(urlAuthor);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String htmlContent = GetPageContent.getResults(test);
		String contentText = GetPageContent.getUrlContentsAsText(urlAuthor);
		String authorArea = GetContentDIVTag.getContentOfDivTag(htmlContent, "ctl00_LeftPanel_CoAuthors_PanelHeader");
		/*authorArea = authorArea.toString().replaceAll("\\<.*?>","");
		
		String result = AcademicFetcherCore.getPattern(authorArea,"\\d+",0);
		*/
		
		//String htmlContentText = GetPageContent.getUrlContentsAsText(urlAuthor);
		//System.out.print(htmlContent);
		//System.out.print(htmlContentText);
		
		//System.out.print(authorArea);
		//htmlContent=htmlContent.replaceAll("\\<.*?>","");
		/*int start = contentText.indexOf("Interest:", -1);
		int end = contentText.indexOf("Collaborated",start);
		String interest = contentText.substring(start+10,end);
		htmlContent
		
		System.out.print(interest);*/
		System.out.print(authorArea);
		int start = authorArea.indexOf(";id=", -1);
		System.out.print(start);
		int end = authorArea.indexOf("\"",start);
		System.out.print(end);
		String interest = authorArea.substring(start +4,end);
		int id = Integer.parseInt(interest);
		System.out.print(id);
		

	}

}
