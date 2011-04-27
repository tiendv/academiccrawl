package uit.tkorg.ac.properties.file;

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
	public static String FIND_AUTHOR_ID = "id=^\\d*$";
		
	public static String COAUTHOR_PATTERN_DIV = "ctl00_MainContent_ObjectList_ctl(NUM)_authorName";
	public static String CONFERENCE_AND_JOURNAL_PATTERN_DIV = "ctl00_MainContent_ObjectList_ctl(NUM)_name";
	public static String PUBLICATION_PATTERN_DIV = "ctl00_MainContent_ObjectList_ctl(NUM)_Title";
}
