
package uit.tkorg.ac.action.fetcher;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.omg.CORBA.PUBLIC_MEMBER;

import uit.tkorg.ac.core.fetcher.AcademicFetcherCore;
import uit.tkorg.ac.properties.file.AcademicCrawlConst;

/**
 * @author tiendv, cuongnp
 *
 */
public class AcademicFetcher {
	
	
	/**
	 * String to get ID from academic when get Author do't know ID 
	 */
	private static String startGetUrlNOID = "http://academic.research.microsoft.com/Search?query=";
	
	
	private static String _pageContent = "";
	
	//Keyword space = +
	//Example http://academic.research.microsoft.com/Search?query=tin+huynh
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String _keyword = "";
		_keyword = JOptionPane.showInputDialog("Tu khoa : ","Nhap tu khoa");
		
		_keyword = _keyword.replaceAll(" ", "+");
		
		System.out.println(_keyword);
		String _urlString = startGetUrlNOID + _keyword; 
		
		URL _url;
		try {
			_url = new URL(_urlString);
			
			System.out.println(_urlString);
			
			_pageContent = GetPageContent.getResults(_url);
		} catch (MalformedURLException e) {
			System.out.println("Cau truy van ko hop le");
		} catch (IOException e) {
			System.out.println("Cau truy van ko hop le");
		}	
		
		if(_pageContent != ""){
			AcademicFetcherCore.getSuggestionAuthorLink(_pageContent);
		}
		
	}
	//public static void Get
}
