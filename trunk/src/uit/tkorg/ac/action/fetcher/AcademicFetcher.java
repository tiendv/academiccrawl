
package uit.tkorg.ac.action.fetcher;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

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
			int index = -1; 	//vi tri cua chuoi "class=\"search-suggestion-author" trong page content
			
			index = _pageContent.indexOf("class=\"search-suggestion-author");
			System.out.println(" index = " + index);
			
			if(index == -1){
				index = _pageContent.indexOf("class=\"search-suggestion");
			}
			
			String _subString  = "<div class=\'author-compact-card\'>";
			
			ArrayList<Integer> _lstAuthorNameIndex = getListIndexOfSubStringInString(_pageContent, _subString);
			ArrayList<String> _lstLink = new ArrayList<String>();
			
			String str = "";
			
			for(int i= 0; i < _lstAuthorNameIndex.size() - 1; i++){
				str = GetAuthorLink(_pageContent.substring(_lstAuthorNameIndex.get(i), _lstAuthorNameIndex.get(i+1)));
				_lstLink.add("http://academic.research.microsoft.com" + str);
			}			
			str = GetAuthorLink(_pageContent.substring(_lstAuthorNameIndex.get(_lstAuthorNameIndex.size() - 1)));
			
			_lstLink.add("http://academic.research.microsoft.com" + str);
			
			for(int i = 0; i < _lstLink.size(); i++){
				System.out.println(_lstLink.get(i));
			}
			
			
		}
	}
	
	//Ham lay ve mang cac vi tri cua chuoi con trong chuoi cha
	public static ArrayList<Integer> getListIndexOfSubStringInString(String strContent, String subStr){
		int lastIndex = 0;
		ArrayList<Integer> authorNumber = new ArrayList<Integer>();
		
		while(lastIndex != -1){			
			lastIndex = strContent.indexOf(subStr, lastIndex + 1);
			
			if( lastIndex != -1){
				authorNumber.add(lastIndex);
			}
		}
		
		return authorNumber;
	}
	
	//Ham lay duong dan den mot tac gia 
	public static String GetAuthorLink(String strContent){
		String _authorLink = "";
		
		Document doc = Jsoup.parse(strContent);
		Element link = doc.select("a").first();
		
		_authorLink = link.attr("href"); // "http://example.com/"
		
		return _authorLink;
	}

}
