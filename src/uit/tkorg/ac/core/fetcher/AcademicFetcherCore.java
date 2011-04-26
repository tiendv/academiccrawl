package uit.tkorg.ac.core.fetcher;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import uit.tkorg.ac.properties.file.AcademicCrawlConst;

/**
 * @author tiendv, cuongnp
 *
 */

public class AcademicFetcherCore {
	public AcademicFetcherCore() {
		
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
		Element link = doc.select(AcademicCrawlConst.A).first();
		
		_authorLink = link.attr(AcademicCrawlConst.HREF); // "http://example.com/"
		
		return _authorLink;
	}
	
	public static ArrayList<String> getSuggestionAuthorLink(String _pageContent){
		int index = -1; 	//vi tri cua chuoi "class=\"search-suggestion-author" trong page content
				
		index = _pageContent.indexOf(AcademicCrawlConst.CLASS_AUTHORS_SUGGESTION);
		if(index == -1){
			index = _pageContent.indexOf(AcademicCrawlConst.CLASS_AUTHOR_SUGGESTION);
		}
		
		String _subString  = AcademicCrawlConst.CLASS_AUTHORS;			
		ArrayList<Integer> _lstAuthorNameIndex = AcademicFetcherCore.getListIndexOfSubStringInString(_pageContent, _subString);
		ArrayList<String> _lstLink = new ArrayList<String>();
				
		String strTemp = "";
		
		for(int i= 0; i < _lstAuthorNameIndex.size() - 1; i++){
			strTemp = AcademicFetcherCore.GetAuthorLink(_pageContent.substring(_lstAuthorNameIndex.get(i), _lstAuthorNameIndex.get(i+1)));
			_lstLink.add(AcademicCrawlConst.ACCADEMIC_HOMEPAGE_URL + strTemp);
		}			
		if(_lstAuthorNameIndex.size() > 0)
			strTemp = AcademicFetcherCore.GetAuthorLink(_pageContent.substring(_lstAuthorNameIndex.get(_lstAuthorNameIndex.size() - 1)));
		
		_lstLink.add(AcademicCrawlConst.ACCADEMIC_HOMEPAGE_URL + strTemp);
		
		for(int i = 0; i < _lstLink.size(); i++){
			System.out.println(_lstLink.get(i));
		}
		
		return _lstLink;
	
	}
	
	public static int CheckSearchStatus(String _pageContent){
		int index = -1; 	//vi tri cua chuoi "class=\"search-suggestion-author" trong page content
		
		index = _pageContent.indexOf(AcademicCrawlConst.CLASS_AUTHORS_SUGGESTION);
		if(index != -1){
			return 3;
		}else{	
			index = _pageContent.indexOf(AcademicCrawlConst.CLASS_AUTHOR_SUGGESTION);
			if(index != -1){
				return 2;
			}
		}
		
		return 1;
	}
}
