/**
 * 
 */
package uit.tkorg.ac.htmlpaser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * @author tiendv
 *
 */
public class GetContentDIVTag {
	// ID get Div suggest authors : #ctl00_MainContent_SearchSuggestion_divSearchSuggestion
	// ID to get number of Citation : ctl00_MainContent_AuthorItem_citedBy
	// ID to get number of publi: ctl00_MainContent_AuthorItem_publication
	String getContentOfDivTag(String htmlContent, String divID) {
		Document doc = Jsoup.parse(htmlContent);
		String id = "#"+divID;
		Element getdata = doc.select(id).first();
		return getdata.toString();
	}

}

