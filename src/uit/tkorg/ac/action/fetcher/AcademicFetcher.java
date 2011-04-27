
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
	 * 
	 * @param htmlContent
	 */
	public static void fetch(String htmlContent){
		int authorId;
		int numConference;
		int numCoAuthor;
		int numJournal;
		int numKeyword;
		int numPublicaiton;
		String htmlContentWithOutTag;
		ArrayList<String> nameCoAuthor = null;
		ArrayList<String> nameJournal= null;
		ArrayList<String> nameConference = null;
		ArrayList<String> nameKeyword = null;
		ArrayList<String> namePublication = null;
		String interest;
		
		String authorName = null;
		
		// Get Author Name
		
		String authorNameTextArea = GetContentDIVTag.getContentOfDivTag(htmlContent, AcademicCrawlConst.ID_AUTHOR_NAME);
		authorName = AcademicFetcherCore.getAuthorName(authorNameTextArea);
		
		String urlWithAuthorID;
		// Get ID
		String coAuthorArea = GetContentDIVTag.getContentOfDivTag(htmlContent,AcademicCrawlConst.ID_CO_AUTHOR);
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
			String journalTextArea = GetContentDIVTag.getContentOfDivTag(htmlContent,AcademicCrawlConst.ID_JOURNAL);
			if(journalTextArea!= null) {
				numJournal = AcademicFetcherCore.getNumberJournal(journalTextArea);
				if(numJournal!=0)
					nameJournal = GetJournal.getJournalFromAuthorID(authorId, numJournal);
			}
			
			// Get Publication
			String authorTextArea = GetContentDIVTag.getContentOfDivTag(htmlContent,AcademicCrawlConst.ID_PUBLICAITON);
			if (authorTextArea!=null){
				numPublicaiton = AcademicFetcherCore.getNumberPublications(authorTextArea);
				if(numPublicaiton!=0)
					namePublication = GetPublications.getPublicationFromAuthorID(authorId, numPublicaiton);
			}
			
			// Get Keyword
			String keywordTextArea = GetContentDIVTag.getContentOfDivTag(htmlContent,AcademicCrawlConst.ID_KEYWORD);
			if (keywordTextArea!=null){
				numKeyword = AcademicFetcherCore.getNumberKeyword(keywordTextArea);
				if(numKeyword!=0)
					nameKeyword = GetKeyWords.getKeyWordFromAuthorID(authorId, numKeyword);
			}
			// Get Conference
			String conferemceTextArea = GetContentDIVTag.getContentOfDivTag(htmlContent,AcademicCrawlConst.ID_CONFERENCE);
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
		
	}
	public static String getAuthorNameFromURL (String url){
		String authorName = null;
		url = url.replace(AcademicCrawlConst.URL_WITH_AUTHOR_ID, "");
		int lastCharacterIndex = url.indexOf("/");
		authorName = url.substring(lastCharacterIndex, url.length());
		return authorName;
		
	}
}
