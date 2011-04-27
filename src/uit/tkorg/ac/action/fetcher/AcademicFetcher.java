
package uit.tkorg.ac.action.fetcher;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import uit.tkorg.ac.core.fetcher.AcademicFetcherCore;
import uit.tkorg.ac.htmlpaser.GetContentDIVTag;

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
		
		int authorId;
		int numConference;
		int numCoAuthor;
		int numJournal;
		int numKeyword;
		int numPublicaiton;
		
		String htmlContent;
		String htmlContentWithOutTag;
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
				ArrayList<String> nameCoAuthor;
				ArrayList<String> nameJournal;
				ArrayList<String> nameConference;
				ArrayList<String> nameKeyword;
				ArrayList<String> namePublication;
				String interest;
				// Get ID
				
				String coAuthorArea = GetContentDIVTag.getContentOfDivTag(_pageContent,"ctl00_LeftPanel_CoAuthors_PanelHeader");
				authorId = AcademicFetcherCore.getAuthorID(coAuthorArea);
				
				// Get Interest
				// Link ?
				//htmlContentWithOutTag = GetPageContent.getUrlContentsAsText(url);
			
				// Get CoAuthor Name
				numCoAuthor = AcademicFetcherCore.getNumberCoAuthor(coAuthorArea);
				System.out.println(numCoAuthor);
				System.out.println(authorId);
				if (numCoAuthor !=0)
				nameCoAuthor = GetCoAuthor.getCoAuthorFromAuthorID(authorId, numCoAuthor);
				
				// Get Journal
				//
				String journalTextArea = GetContentDIVTag.getContentOfDivTag(_pageContent,"ctl00_LeftPanel_RelatedJournals_PanelHeader");
				numJournal = AcademicFetcherCore.getNumberJournal(journalTextArea);
				if(numJournal!=0)
					nameJournal = GetJournal.getJournalFromAuthorID(authorId, numJournal);
				
				// Get Publication
				String authorTextArea = GetContentDIVTag.getContentOfDivTag(_pageContent, "ctl00_MainContent_AuthorItem_publication");
				numPublicaiton = AcademicFetcherCore.getNumberPublications(authorTextArea);
				if(numPublicaiton!=0)
					namePublication = GetPublications.getPublicationFromAuthorID(authorId, numPublicaiton);
				
				// Get Keyword
				String keywordTextArea = GetContentDIVTag.getContentOfDivTag(_pageContent, "ctl00_LeftPanel_RelatedKeywords_PanelHeader");
				numKeyword = AcademicFetcherCore.getNumberKeyword(keywordTextArea);
				if(numKeyword!=0)
					nameKeyword = GetKeyWords.getKeyWordFromAuthorID(authorId, numKeyword);
				// Get Conference
				String conferemceTextArea = GetContentDIVTag.getContentOfDivTag(_pageContent, "ctl00_LeftPanel_RelatedConferences_PanelHeader");
				numConference = AcademicFetcherCore.getNumberConference(conferemceTextArea);
				if(numConference!=0)
					nameConference = GetConferences.getConferenceFromAuthorID(authorId, numConference);
				System.out.println("Tim dc 1 ket qua!");
				//System.out.println(_pageContent);
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
