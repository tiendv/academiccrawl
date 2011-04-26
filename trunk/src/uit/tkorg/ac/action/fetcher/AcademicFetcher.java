
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
	
	//Keyword space = +
	//Example http://academic.research.microsoft.com/Search?query=tin+huynh
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String _pageContent = fetch();
		
		if(_pageContent != ""){
			if(AcademicFetcherCore.checkSearchStatus(_pageContent) == 3){		
				ArrayList<String> _lstLink = new ArrayList<String>();
				
				 _lstLink = AcademicFetcherCore.getSuggestionAuthorLink(_pageContent);
				
				if(_lstLink.size() > 0){
					for(int i = 0; i < 1; i++){
						System.out.println(AcademicFetcherCore.getAuthorPageDetail(_lstLink.get(i)));
					}
				}
				
			}else if(AcademicFetcherCore.checkSearchStatus(_pageContent) == 2){
				System.out.println("Tim dc 1 ket qua!");
				System.out.println(_pageContent);
			}else{
				System.out.println("Khong tim thay ket qua");
			}
			
		}
		
	}
	public static String fetch(){
		String _keyword = "";
		String _pageContent = "";
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
		
		return _pageContent;
		
	}
}
