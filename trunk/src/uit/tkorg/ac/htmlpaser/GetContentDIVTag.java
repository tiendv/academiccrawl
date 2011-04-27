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
	// ID to get co - author : ctl00_LeftPanel_CoAuthors_PanelHeader
	// ID to get left panel (co - author, Conference,joural, keyword ): ctl00_divLeftWrapper
	public static String getContentOfDivTag(String htmlContent, String divID) {
		Document doc = Jsoup.parse(htmlContent);
		String id = "#"+divID;
		Element getdata = doc.select(id).first();
		return getdata.toString();
	}

}

