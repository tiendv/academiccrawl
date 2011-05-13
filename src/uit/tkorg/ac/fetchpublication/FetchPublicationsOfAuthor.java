package uit.tkorg.ac.fetchpublication;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uit.tkorg.ac.action.fetcher.GetPageContent;
import uit.tkorg.ac.htmlpaser.GetContentDIVTag;
import uit.tkorg.ac.properties.file.AcademicCrawlConst;

/**
 * @author cuongnp,tiendv
 *
 */

public class FetchPublicationsOfAuthor {

	/**
	 * @param args
	 */
	int index = 0;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		
		
		// Example run by get Publiscation page of Susan Gauch
		URL _url;
		try {
			_url = new URL("http://academic.research.microsoft.com/Detail?entitytype=2&searchtype=2&id=866448");
		
			String _page = GetPageContent.getResults(_url);
			
			
			/// Run with number of Susan's Publicaiton 
			
			for(int j = 0; j < 81; j++ ){
				
			String paperID = "";
			if(j < 10){
				paperID = "0" + j;
			}
			else{
				paperID = j + "";
			}
			
			
			String paperItemContent = getPublicaitonItemTextContent(_page, paperID);
			System.out.printf(paperItemContent);
			
			System.out.println( "Paper 0" + j);
			
			System.out.println( "Title:" + getTitleOfPublication(paperItemContent, paperID));
			
			System.out.println("Authors : ");
			
			ArrayList<String> authorList = getListAuthorNameOfPublication(paperItemContent);
			if(authorList != null){
				for (int i = 0; i < authorList.size(); i++) {
					System.out.println(authorList.get(i));
				}
			}
			
			System.out.println("");			
			
			String pubAbstract = getAbstractOfPublication(paperItemContent); 
			
			System.out.println( "Abstract: " + pubAbstract);
			System.out.println("");
			
			String conference = getNameConferenceOfPublication(paperItemContent, paperID);
			if(conference != "")
			System.out.println("Confernce: " + conference);
			else
				System.out.println("Journal: " + getNameJournalOfPublication(paperItemContent, paperID));
			
			System.out.println("");
			
			String pageNumber = getPageNumberOfPublication(paperItemContent, paperID);
			System.out.println( "Page number: " + pageNumber);
			
			System.out.println("");	
			int year = getYearPublishOfPublication(paperItemContent, paperID);			
			System.out.println("Year: " + year );			
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 *  Ham lay noi dung (Text) publication trong list pub cua page
	 * @param pageContent : HTML Content of Publicaiton page
	 * @param pubOrder : possiton of Pub
	 * @return : String Text Info Text.
	 */
	public static String getPublicaitonItemTextContent(String pageContent, String pubOrder)
	{
		String ptGetPaperItem = AcademicCrawlConst.PATTERN_PUBLICAITON_ITEML_IN_LIST 
								+ pubOrder + AcademicCrawlConst.PATTERN_DIV_TITLE;
		String result = "";
		int _startIndex = 0;
		int _endIndex = 0;
		
		if(pageContent != null && pageContent != ""){
			_startIndex = pageContent.indexOf(ptGetPaperItem);
			_endIndex = pageContent.indexOf( AcademicCrawlConst.LI_CLOSE_TAB, _startIndex);
		}
		
		if(_startIndex != -1 && _endIndex != -1)
			result = pageContent.substring(_startIndex, _endIndex);
		
		return result;
	}
	
	/**
	 * Ham lay danh sach ten cac tac gia cua bai bao.
	 * @param publicationTextContent
	 * @return : ArrayList Author Name
	 */
	public static ArrayList<String> getListAuthorNameOfPublication(String publicationTextContent){
		
		ArrayList<String> lstAuthor = new ArrayList<String>();
		int _startIndex = 0;
		int _endIndex = 0;
		
		if(publicationTextContent != null && publicationTextContent != ""){
			_startIndex = publicationTextContent.indexOf(AcademicCrawlConst.PATTERN_DIV_CLASS_LIST_AUTHOR_NAME);
			_endIndex = publicationTextContent.indexOf(AcademicCrawlConst.DIV_CLOSE_TAB, _startIndex);
		}
		if(_startIndex != -1 && _endIndex != -1){			
			String listAuthorName = publicationTextContent.substring(_startIndex, _endIndex);
			
			listAuthorName = listAuthorName.replaceAll(AcademicCrawlConst.HTML_TAB,"").replaceAll( AcademicCrawlConst.HTML_TAB_OTHER, "");
			
			lstAuthor = getAuthorNameFromTextListAuthorName(listAuthorName, ",");				
		}
		
		return lstAuthor;
	}

	/**
	 * Lay ten tac gia tu chuoi danh sach ten tac gia
	 * @param listAuthorName
	 * @param characterBetwenAuthorName
	 * @return
	 */
	public static ArrayList<String> getAuthorNameFromTextListAuthorName(String listAuthorName, String characterBetwenAuthorName) {
		
		int beginIndex = 0;		
		int temp = -1;
		
		ArrayList<String> strList = new ArrayList<String>();
		
		while(beginIndex != -1){			
			temp =  beginIndex;
			beginIndex = listAuthorName.indexOf(characterBetwenAuthorName , beginIndex + 1);
			
			if(beginIndex == -1)
				strList.add(listAuthorName.substring(temp).replaceAll("  ", "").replaceAll(",", ""));
			else
				strList.add(listAuthorName.substring(temp, beginIndex).replaceAll("  ", "").replaceAll(",", ""));
			
		}
		
		return strList;
    }
	
	/**
	 * Ham lay tom tat cua tac gia tu noi dung cua bai bao.
	 * @param publicaitonTextContent
	 * @return
	 */
	public static String getAbstractOfPublication(String publicaitonTextContent){
		
		String result = "";		
		int _beginIndex = 0;
		int _endIndex = 0;		
		
		if(publicaitonTextContent != null && publicaitonTextContent != ""){
			_beginIndex = publicaitonTextContent.indexOf(AcademicCrawlConst.DIV_CLASS_ABSTRACT_CONTENT);
			_endIndex = publicaitonTextContent.indexOf(AcademicCrawlConst.DIV_CLOSE_TAB, _beginIndex);
			
		}		
		if(_beginIndex != -1 && _endIndex != -1){			
			result = publicaitonTextContent.substring(_beginIndex, _endIndex);
		}
		
		result = result.replaceAll(AcademicCrawlConst.HTML_TAB, "").replaceAll(AcademicCrawlConst.HTML_TAB_OTHER, "").replaceAll( "  ", "");
		
		return result;
	}
	
	public static String getTitleOfPublication(String publicaitonTextContent, String pubOrder){	
		String result = "";		
		result = GetContentDIVTag.getTextOfDivTag(publicaitonTextContent,
				AcademicCrawlConst.PUBLICATION_PATTERN_DIV.replaceAll(
						"\\(NUM\\)", pubOrder));
		return result;
	}
	
	/**
	 * Ham lay phan conference
	 * @param publicaitonTextContent
	 * @param pubOrder : So thu tu cua pub trong pub page
	 * @return
	 */
	public static String getNameConferenceOfPublication(String publicaitonTextContent, String pubOrder){
		String idOfConferenceTab = AcademicCrawlConst.PATTERN_PUBLICAITON_ITEML_IN_LIST 
								+ pubOrder + AcademicCrawlConst.HL_CONFERENCE;
		
		String result = "";
		if(GetContentDIVTag.getContentOfDivTag(publicaitonTextContent, idOfConferenceTab) != null)		
			result = GetContentDIVTag.getContentOfDivTag(publicaitonTextContent, idOfConferenceTab).replaceAll(AcademicCrawlConst.HTML_TAB,"");
		return result;
	}
	/**
	 * Ham lay ten Joural cua publicaiton neu conference Rong
	 * @param publicaitonTextContent
	 * @param pubOrder
	 * @return
	 */
	public static String getNameJournalOfPublication(String publicaitonTextContent, String pubOrder){	
		String result = "";
		
		String idOfJouralTab = AcademicCrawlConst.PATTERN_PUBLICAITON_ITEML_IN_LIST 		
									+ pubOrder + AcademicCrawlConst.HL_JOURNAL;
			if(GetContentDIVTag.getContentOfDivTag(publicaitonTextContent, idOfJouralTab) != null)		
				result = GetContentDIVTag.getContentOfDivTag(publicaitonTextContent, idOfJouralTab).replaceAll(AcademicCrawlConst.HTML_TAB,"");
		
		return result;
	}
	
	
	/**
	 * Lay page number cua pub 
	 * @param publicaitonTextContent
	 * @param pubOrder
	 * @return
	 */
	public static String getPageNumberOfPublication(String publicaitonTextContent, String pubOrder){
		String idOfYearTab = AcademicCrawlConst.PATTERN_PUBLICAITON_ITEML_IN_LIST 
								+ pubOrder + AcademicCrawlConst.YEAR_CONFERENCE;

		String result = "";
		if(GetContentDIVTag.getContentOfDivTag(publicaitonTextContent, idOfYearTab) != null)
			result = GetContentDIVTag.getContentOfDivTag(publicaitonTextContent, idOfYearTab).replaceAll("\\<.*?>","");
	
		result = result.replaceAll(AcademicCrawlConst.HTML_TAB,"");
		
		Pattern hitsPattern = Pattern.compile(AcademicCrawlConst.PATTERN_PAGENUM_TO_PAGENUM);
		Matcher m = hitsPattern.matcher(result);
        if (!m.find()) {
        	return result;
        } else {
           
        	String firstNumber = m.group(1);
        	String lastNumber = m.group(2);
        	
        	firstNumber = firstNumber.replaceAll(",", "");
        	lastNumber = lastNumber.replaceAll(",", "");
        	
        	result = firstNumber + " - " + lastNumber;
        	
        }
        
		return result;
	}
	
	/**
	 * Ham lay nam cong bo
	 * @param publicaitonTextContent
	 * @param pubOrder
	 * @return
	 */
	
	public static int getYearPublishOfPublication(String publicaitonTextContent, String pubOrder){
		
		String idOfYearTab = AcademicCrawlConst.PATTERN_PUBLICAITON_ITEML_IN_LIST 
								+ pubOrder + AcademicCrawlConst.YEAR_CONFERENCE;
		
		String result = "";
		
		int year = 0;
		
		if(GetContentDIVTag.getContentOfDivTag(publicaitonTextContent, idOfYearTab) != null)
			result = GetContentDIVTag.getContentOfDivTag(publicaitonTextContent, idOfYearTab).replaceAll("\\<.*?>","");		
		
        Pattern datePattern = Pattern.compile(AcademicCrawlConst.PATTERN_YEAR);
	    Matcher m = datePattern.matcher(result);
	    if (!m.find()) {
        	System.out.println("No Info");        	
        } else {
        	year = Integer.parseInt(m.group(1));
        }
		
		return year;
	}
	
}
