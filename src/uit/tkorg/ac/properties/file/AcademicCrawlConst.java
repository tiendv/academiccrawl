package uit.tkorg.ac.properties.file;
/**
 * 
 * @author tiendv,cuongnp,hoangnt
 *
 */
public class AcademicCrawlConst {
	public AcademicCrawlConst() {
		// TODO Auto-generated constructor stub
	}
	
	public static int MAX_NUMBER_SHOW_IN_PAGE = 100;
	
	public static String A = "a";
	public static String AND = "&";
	public static String START = "start";
	public static String END = "end";
	public static String HREF = "href";
	public static String CLASS_AUTHORS_SUGGESTION = "class=\"search-suggestion-author";
	public static String CLASS_AUTHOR_SUGGESTION = "class=\"search-suggestion";
	public static String CLASS_AUTHORS = "<div class=\'author-compact-card\'>";
	public static String ACCADEMIC_HOMEPAGE_URL = "http://academic.research.microsoft.com";
	public static String ACCADEMIC_COAUTHOR_QUERY = "http://academic.research.microsoft.com/Detail?entitytype=2&searchtype=1&id=";
	public static String ACCADEMIC_CONFERENCE_QUERY = "http://academic.research.microsoft.com/Detail?entitytype=2&searchtype=3&id=";
	public static String ACCADEMIC_JOURNAL_QUERY = "http://academic.research.microsoft.com/Detail?searchtype=4&entitytype=2&id=";
	public static String ACCADEMIC_PUBLICATION_QUERY="http://academic.research.microsoft.com/Detail?searchtype=2&entitytype=2&id=";
	public static String ACCADEMIC_KEYWORD_QUERY= "http://academic.research.microsoft.com/Detail?searchtype=9&entitytype=2&id=";
	public static String FIND_AUTHOR_ID = "id=^\\d*$";
		
	public static String COAUTHOR_PATTERN_DIV = "ctl00_MainContent_ObjectList_ctl(NUM)_authorName";
	public static String CONFERENCE_AND_JOURNAL_AND_KEYWORD_PATTERN_DIV = "ctl00_MainContent_ObjectList_ctl(NUM)_name";
	public static String PUBLICATION_PATTERN_DIV = "ctl00_MainContent_ObjectList_ctl(NUM)_Title";
	
	public static String URL_START = "http://academic.research.microsoft.com/Search?query=";
	public static String URL_WITH_AUTHOR_ID = "http://academic.research.microsoft.com/Author/";
	public static String DOMAIN_COMPUTER_SCIENCE = "&SearchDomain=2";
	
	public static String ID_CO_AUTHOR ="ctl00_LeftPanel_CoAuthors_PanelHeader";
	public static String ID_JOURNAL="ctl00_LeftPanel_RelatedJournals_PanelHeader";
	public static String ID_PUBLICAITON ="ctl00_MainContent_AuthorItem_publication";
	public static String ID_KEYWORD = "ctl00_LeftPanel_RelatedKeywords_PanelHeader";
	public static String ID_CONFERENCE="ctl00_LeftPanel_RelatedConferences_PanelHeader";
	public static String ID_AUTHOR_NAME = "ctl00_MainContent_AuthorItem_authorName";
	
	public static String HTML_TAB = "\\<.*?>";
	public static String HTML_TAB_OTHER = "&nbsp;";
	public static String LI_CLOSE_TAB = "</li>";
	public static String DIV_CLOSE_TAB = "</div>";	
	public static String PATTERN_PUBLICAITON_ITEML_IN_LIST= "ctl00_MainContent_ObjectList_ctl";
	public static String PATTERN_DIV_TITLE = "_divTitle";
	public static String HL_CONFERENCE = "_HLConference";
	public static String HL_JOURNAL = "_HLJournal";
	public static String YEAR_CONFERENCE = "_YearConference";
	public static String PATTERN_DIV_CLASS_LIST_AUTHOR_NAME = "<div class=\"content\">";
	public static String DIV_CLASS_ABSTRACT_CONTENT = "<div class=\"abstract\">";
	public static String PATTERN_PAGENUM_TO_PAGENUM = "(\\d+,*\\d*,*\\d*,*\\d*)-(\\d+,*\\d*,*\\d*,*\\d)";
	public static String PATTERN_YEAR = "(\\d{4})";
}
