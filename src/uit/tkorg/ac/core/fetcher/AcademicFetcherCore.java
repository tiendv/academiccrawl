package uit.tkorg.ac.core.fetcher;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import uit.tkorg.ac.action.fetcher.GetPageContent;
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
	
	/**
	 * Ham lay duong dan den mot tac gia 
	 * @param strContent : mot doan text trong div da duoc cat!
	 * @return: Link  author suggest
	 */
	public static String GetAuthorLink(String strContent){
		String _authorLink = "";
		
		Document doc = Jsoup.parse(strContent);
		Element link = doc.select(AcademicCrawlConst.A).first();
		
		_authorLink = link.attr(AcademicCrawlConst.HREF); // "http://example.com/"
		
		return _authorLink;
	}
	/**
	 * 
	 * @param _pageContent
	 * @return
	 */
	public static ArrayList<String> getSuggestionAuthorLink(String _pageContent){
		
		ArrayList<String> _lstLink = new ArrayList<String>();
			
		String _subString  = AcademicCrawlConst.CLASS_AUTHORS;			
		ArrayList<Integer> _lstAuthorNameIndex = AcademicFetcherCore.getListIndexOfSubStringInString(_pageContent, _subString);
		
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
	/**
	 * Kiem tra ket qua tra ve sau khi seach
	 * @param _pageContent
	 * @return  truong hop
	 */
	public static int checkSearchStatus(String _pageContent){
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
	
	/**
	 * 
	 * @param link
	 * @return
	 */
	public static String getAuthorPageDetail(String link){
		URL _url;
		String _result = "";
		try {
			_url = new URL(link);
			_result = GetPageContent.getResults(_url);
		} catch (MalformedURLException e) {
			System.out.println("Link khong hop le!");
		} catch (IOException e) {
			System.out.println("Link khong hop le!");
		}
					
		return _result;
	}
	
	/**
	 * 
	 * @param htmlContent : string to find
	 * @param _patternStr : pattern to match
	 * @param position    : posotion to get
	 * @return
	 */
	public static String getPattern(String htmlContent,String _patternStr, int position){
		String result;
		Pattern _pattern = Pattern.compile(_patternStr);
		Matcher _matcher =  _pattern.matcher(htmlContent);
		_matcher.find();
		result = _matcher.group(position).toString();
		return result;
	}
	/**
	 * 
	 * @param coAuthorArea : 
	 * @return
	 * ID = ctl00_LeftPanel_CoAuthors_PanelHeader
	 */
	public static int getAuthorID (String coAuthorArea){
		int id = 0;
		int start = coAuthorArea.indexOf(";id=", -1);
		int end = coAuthorArea.indexOf("\"",start);
		String interest = coAuthorArea.substring(start+4,end);
		id = Integer.parseInt(interest);
		return id;
		
	}
	
	/**
	 * Lay linh vuc nghien cuu cua tac gia
	 * @param htmlContentAsText : noi dung cua trang duoi dang Text
	 * @return
	 */
	public static String getInterest (String htmlContentAsText) {
		int start = htmlContentAsText.indexOf("Interest:", -1);
		int end = htmlContentAsText.indexOf("Collaborated",start);
		String interest = htmlContentAsText.substring(start+10,end);
		return interest;
	}
	/**
	 * 
	 * @param authorTextArea
	 * @return
	 * 
	 * ID : ctl00_MainContent_AuthorItem_authorName
	 */
	public static String getAuthorName(String authorTextArea) {
		String authorName;
		authorName = authorTextArea.toString().replaceAll("\\<.*?>","");
		return authorName;
	}
	
	/**
	 * 
	 * @param authorTextArea :noi dung text sau khi da lay id trong dvi
	 * @return
	 *  id : ctl00_MainContent_AuthorItem_publication
	 */
	public static int getNumberPublications(String authorTextArea) {
		int numberOfPublication;
		authorTextArea = authorTextArea.toString().replaceAll("\\<.*?>","");
		authorTextArea =getPattern(authorTextArea,"\\d+",0);
		numberOfPublication=Integer.parseInt(authorTextArea);
		return numberOfPublication;
	}
	/**
	 * 
	 * @param authorTextArea
	 * @return
	 *  id: ctl00_LeftPanel_CoAuthors_PanelHeader
	 */
	public static int getNumberCoAuthor(String coAuthorTextArea) {
		int numberPublication = 0;
		coAuthorTextArea = coAuthorTextArea.toString().replaceAll("\\<.*?>","");
		coAuthorTextArea =getPattern(coAuthorTextArea,"\\d+",0);
		numberPublication=Integer.parseInt(coAuthorTextArea);
		return numberPublication;
	}
	
	/**
	 * 
	 * @param Journal
	 * @return
	 * ID: ctl00_LeftPanel_RelatedJournals_PanelHeader
	 */
	public static int getNumberJournal(String journalTextArea) {
		int numberJournal = 0;
		journalTextArea = journalTextArea.toString().replaceAll("\\<.*?>","");
		journalTextArea =getPattern(journalTextArea,"\\d+",0);
		numberJournal=Integer.parseInt(journalTextArea);
		return numberJournal;
	}
	/**
	 * 
	 * @param journalTextArea
	 * @return
	 * ID : ctl00_LeftPanel_RelatedConferences_PanelHeader
	 */
	public static int getNumberConference(String conferemceTextArea) {
		int numberConference = 0;
		conferemceTextArea = conferemceTextArea.toString().replaceAll("\\<.*?>","");
		conferemceTextArea =getPattern(conferemceTextArea,"\\d+",0);
		numberConference=Integer.parseInt(conferemceTextArea);
		return numberConference;
	}
	
	/**
	 * 
	 * @param keywordTextArea
	 * @return
	 * ID : ctl00_LeftPanel_RelatedKeywords_PanelHeade
	 */
	public static int getNumberKeyword(String keywordTextArea) {
		int numberJournal = 0;
		keywordTextArea = keywordTextArea.toString().replaceAll("\\<.*?>","");
		keywordTextArea =getPattern(keywordTextArea,"\\d+",0);
		numberJournal=Integer.parseInt(keywordTextArea);
		return numberJournal;
	}
}
