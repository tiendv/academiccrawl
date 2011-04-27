
package uit.tkorg.ac.action.fetcher;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.dom4j.Document;

import uit.tkorg.ac.core.fetcher.AcademicFetcherCore;
import uit.tkorg.ac.htmlpaser.GetContentDIVTag;
import uit.tkorg.ac.properties.file.AcademicCrawlConst;

/**
 * @author tiendv, cuongnp, hoangnt
 *
 */
public class AcademicFetcher {
	

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
				
				ArrayList<String> nameCoAuthor = null;
				ArrayList<String> nameJournal= null;
				ArrayList<String> nameConference = null;
				ArrayList<String> nameKeyword = null;
				ArrayList<String> namePublication = null;
				String interest;
				
				String authorName = null;
				
				// Get Author Name
				
				String authorNameTextArea = GetContentDIVTag.getContentOfDivTag(_pageContent, AcademicCrawlConst.ID_AUTHOR_NAME);
				authorName = AcademicFetcherCore.getAuthorName(authorNameTextArea);
				
				String urlWithAuthorID;
				// Get ID
				String coAuthorArea = GetContentDIVTag.getContentOfDivTag(_pageContent,AcademicCrawlConst.ID_CO_AUTHOR);
				if(coAuthorArea != null){
					authorId = AcademicFetcherCore.getAuthorID(coAuthorArea);
					
					// Get Interest
					
					urlWithAuthorID = AcademicCrawlConst.URL_WITH_AUTHOR_ID+ Integer.toString(authorId);
					htmlContentWithOutTag = GetPageContent.getUrlContentsAsText(urlWithAuthorID);
					interest = AcademicFetcherCore.getInterest(htmlContentWithOutTag);
						
					// Get CoAuthor Name
					numCoAuthor = AcademicFetcherCore.getNumberCoAuthor(coAuthorArea);
					if (numCoAuthor !=0)
					nameCoAuthor = GetCoAuthor.getCoAuthorFromAuthorID(authorId, numCoAuthor);
					
					// Get Journal
					//
					String journalTextArea = GetContentDIVTag.getContentOfDivTag(_pageContent,AcademicCrawlConst.ID_JOURNAL);
					if(journalTextArea!= null) {
						numJournal = AcademicFetcherCore.getNumberJournal(journalTextArea);
						if(numJournal!=0)
							nameJournal = GetJournal.getJournalFromAuthorID(authorId, numJournal);
					}
					
					// Get Publication
					String authorTextArea = GetContentDIVTag.getContentOfDivTag(_pageContent,AcademicCrawlConst.ID_PUBLICAITON);
					if (authorTextArea!=null){
						numPublicaiton = AcademicFetcherCore.getNumberPublications(authorTextArea);
						if(numPublicaiton!=0)
							namePublication = GetPublications.getPublicationFromAuthorID(authorId, numPublicaiton);
					}
					
					// Get Keyword
					String keywordTextArea = GetContentDIVTag.getContentOfDivTag(_pageContent,AcademicCrawlConst.ID_KEYWORD);
					if (keywordTextArea!=null){
						numKeyword = AcademicFetcherCore.getNumberKeyword(keywordTextArea);
						if(numKeyword!=0)
							nameKeyword = GetKeyWords.getKeyWordFromAuthorID(authorId, numKeyword);
					}
					// Get Conference
					String conferemceTextArea = GetContentDIVTag.getContentOfDivTag(_pageContent,AcademicCrawlConst.ID_CONFERENCE);
					if(conferemceTextArea!=null) {
						numConference = AcademicFetcherCore.getNumberConference(conferemceTextArea);
						if(numConference!=0)
							nameConference = GetConferences.getConferenceFromAuthorID(authorId, numConference);
						
					}
					
					// write to XML File
					
					Document xmlFile = WriteXML.buildDocument(authorName, interest, nameCoAuthor, nameConference, nameJournal, nameKeyword, namePublication);
					String nameFIle;
					nameFIle = JOptionPane.showInputDialog(" Tem file: ","Nhap vao ten File");
					String path = "C:\\"+ nameFIle+".xml";
					
					try {
						WriteXML.write(xmlFile, path);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					
					
				}
				
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
		
		String _urlString = AcademicCrawlConst.URL_START + _keyword + AcademicCrawlConst.DOMAIN_COMPUTER_SCIENCE; 
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
