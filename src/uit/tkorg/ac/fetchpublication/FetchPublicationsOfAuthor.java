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
 * @author cuongnp
 *
 */

public class FetchPublicationsOfAuthor {

	/**
	 * @param args
	 */
	int index = 0;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		URL _url;
		try {
			_url = new URL("http://academic.research.microsoft.com/Detail?entitytype=2&searchtype=2&id=866448");
		
			String _page = GetPageContent.getResults(_url);
			for(int j = 0; j < 81; j++ ){
			String paperID = "0" + j;
			String paperItemContent = getPaperItemContent(_page, paperID);
			
			System.out.println( "Paper 0" + j);
			
			System.out.println("Authors : ");
			getAuthorNameList(paperItemContent);
			ArrayList<String> authorList = getAuthorNameList(paperItemContent);
			if(authorList != null){
				for (int i = 0; i < authorList.size(); i++) {
					System.out.println(authorList.get(i));
				}
			}
			
			System.out.println("");			
			String pubAbstract = getAbstract(paperItemContent); 
			System.out.println( "Abstract: " + pubAbstract);
			
			System.out.println("");
			String conference = getConference(paperItemContent, paperID);
			System.out.println("Confernce: " + conference);
			
			System.out.println("");
			String pageNumber = getPageNumber(paperItemContent, paperID);
			System.out.println( "Page number: " + pageNumber);
			
			System.out.println("");	
			int year = getYear(paperItemContent, paperID);			
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
	
	// Ham lay noi dung chi tiet 1 publication
	public static String getPaperItemContent(String pageContent, String paperID)
	{
		String ptGetPaperItem = AcademicCrawlConst.CLASS_MAIN_CONTENT_PUB_LIST 
								+ paperID + AcademicCrawlConst.DIV_TITLE;
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
	
	//Ham lay phan danh sach cac tac gia
	public static ArrayList<String> getAuthorNameList(String pageItemContent){
		
		ArrayList<String> lstAuthor = new ArrayList<String>();
		int _startIndex = 0;
		int _endIndex = 0;
		
		if(pageItemContent != null && pageItemContent != ""){
			_startIndex = pageItemContent.indexOf(AcademicCrawlConst.DIV_CLASS_AUTHOR_CONTENT);
			_endIndex = pageItemContent.indexOf(AcademicCrawlConst.DIV_CLOSE_TAB, _startIndex);
		}
		
		if(_startIndex != -1 && _endIndex != -1){			
			String temp = pageItemContent.substring(_startIndex, _endIndex);
			
			temp = temp.replaceAll(AcademicCrawlConst.HTML_TAB,"").replaceAll( AcademicCrawlConst.HTML_TAB_OTHER, "");
			
			lstAuthor = getListItem(temp, ",");				
		}
		
		return lstAuthor;
	}

	// Ham cat 1 chuoi thanh nhieu chuoi con
	public static ArrayList<String> getListItem(String strContent, String strFind) {
		
		int beginIndex = 0;		
		int temp = -1;
		
		ArrayList<String> strList = new ArrayList<String>();
		
		while(beginIndex != -1){			
			temp =  beginIndex;
			beginIndex = strContent.indexOf(strFind , beginIndex + 1);
			
			if(beginIndex == -1)
				strList.add(strContent.substring(temp + 1).replaceAll("  ", ""));
			else
				strList.add(strContent.substring(temp + 1, beginIndex).replaceAll("  ", ""));
			
		}
		
		return strList;
    }
	
	//Ham lay phan abstract
	public static String getAbstract(String pageItemContent){
		
		String result = "";		
		int _beginIndex = 0;
		int _endIndex = 0;		
		
		if(pageItemContent != null && pageItemContent != ""){
			_beginIndex = pageItemContent.indexOf(AcademicCrawlConst.DIV_CLASS_ABSTRACT_CONTENT);
			_endIndex = pageItemContent.indexOf(AcademicCrawlConst.DIV_CLOSE_TAB, _beginIndex);
			
		}		
		if(_beginIndex != -1 && _endIndex != -1){			
			result = pageItemContent.substring(_beginIndex, _endIndex);
		}
		
		result = result.replaceAll(AcademicCrawlConst.HTML_TAB, "").replaceAll(AcademicCrawlConst.HTML_TAB_OTHER, "").replaceAll( "  ", "");
		
		return result;
	}
	
	//Ham lay phan conference
	public static String getConference(String paperItemContent, String paperID){
		String idOfConferenceTab = AcademicCrawlConst.CLASS_MAIN_CONTENT_PUB_LIST 
								+ paperID + AcademicCrawlConst.HL_CONFERENCE;
		String result = "";
		if(GetContentDIVTag.getContentOfDivTag(paperItemContent, idOfConferenceTab) != null)		
			result = GetContentDIVTag.getContentOfDivTag(paperItemContent, idOfConferenceTab).replaceAll(AcademicCrawlConst.HTML_TAB,"");
		
		return result;
	}
	
	//Ham lay so trang
	public static String getPageNumber(String paperItemContent, String paperID){
		String idOfYearTab = AcademicCrawlConst.CLASS_MAIN_CONTENT_PUB_LIST 
								+ paperID + AcademicCrawlConst.YEAR_CONFERENCE;
		
		String result = "";
		if(GetContentDIVTag.getContentOfDivTag(paperItemContent, idOfYearTab) != null)
			result = GetContentDIVTag.getContentOfDivTag(paperItemContent, idOfYearTab).replaceAll("\\<.*?>","");
	
		result = result.replaceAll(AcademicCrawlConst.HTML_TAB,"");
		
		Pattern hitsPattern = Pattern.compile(AcademicCrawlConst.PATTERN_PAGENUM_TO_PAGENUM);
		Matcher m = hitsPattern.matcher(result);
        if (!m.find()) {
        	System.out.println("No Info");
        } else {
           
        	String firstNumber = m.group(1);
        	String lastNumber = m.group(2);
        	
        	firstNumber = firstNumber.replaceAll(",", "");
        	lastNumber = lastNumber.replaceAll(",", "");
        	
        	result = firstNumber + " - " + lastNumber;
        	
        }
        
		return result;
	}
	
	//Ham lay nam cong bo
	public static int getYear(String paperItemContent, String paperID){
		
		String idOfYearTab = AcademicCrawlConst.CLASS_MAIN_CONTENT_PUB_LIST 
								+ paperID + AcademicCrawlConst.YEAR_CONFERENCE;
		
		String result = "";
		
		int year = 0;
		
		if(GetContentDIVTag.getContentOfDivTag(paperItemContent, idOfYearTab) != null)
			result = GetContentDIVTag.getContentOfDivTag(paperItemContent, idOfYearTab).replaceAll("\\<.*?>","");		
		
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
