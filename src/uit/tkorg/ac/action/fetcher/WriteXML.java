/**
 * 
 */
package uit.tkorg.ac.action.fetcher;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 * @author tiendv
 * 
 */
public class WriteXML {

	public static Document buildDocument(String authorName, String interest,
			ArrayList<String> coAuthor, ArrayList<String> conference,
			ArrayList<String> journal, ArrayList<String> keyword,
			ArrayList<String> publication) {
		if (interest == null)
			interest = "";
		Document document = DocumentHelper.createDocument();
		// root
		Element root = document.addElement("Author");
		root.addAttribute("name", authorName);
		root.addAttribute("interest", interest);

		// CoAuthor
		Element listCoAuthor;
		if (coAuthor != null) {
			listCoAuthor = root.addElement("COAUTHOR").addAttribute("count",
					String.valueOf(coAuthor.size()));
			for (int i = 0; i < coAuthor.size(); i++) {
				Element nameCorAuthor = listCoAuthor.addElement("AUTHOR")
						.addText(coAuthor.get(i));
			}
		} else
			listCoAuthor = root.addElement("COAUTHOR")
					.addAttribute("count", "");
		// Conference
		Element listConference;
		if (conference != null) {
			listConference = root.addElement("CONFERENCES").addAttribute(
					"count", String.valueOf(conference.size()));
			for (int i = 0; i < conference.size(); i++) {
				Element nameConference = listConference
						.addElement("CONFERENCE").addText(conference.get(i));
			}
		} else
			listConference = root.addElement("CONFERENCES").addAttribute(
					"count", "0");

		// Journal
		Element listJournal;
		if (journal != null) {
			listJournal = root.addElement("JOURNALS").addAttribute("count",
					String.valueOf(journal.size()));
			for (int i = 0; i < journal.size(); i++) {
				Element nameJournal = listJournal.addElement("JOURNAL")
						.addText(journal.get(i));
			}
		} else
			listJournal = root.addElement("JOURNALS")
					.addAttribute("count", "0");

		// Keyword
		Element listKeyword;
		if (keyword != null) {
			listKeyword = root.addElement("KEYWORDS").addAttribute("count",
					String.valueOf(keyword.size()));
			for (int i = 0; i < keyword.size(); i++) {
				Element nameKeyword = listKeyword.addElement("KEYWORD")
						.addText(keyword.get(i));
			}
		} else
			listKeyword = root.addElement("KEYWORDS")
					.addAttribute("count", "0");

		// Publicaiton
		Element listPublication;
		if (publication != null) {
			listPublication = root.addElement("PUBLICAITONS").addAttribute(
					"count", String.valueOf(publication.size()));
			for (int i = 0; i < publication.size(); i++) {
				Element title = listPublication.addElement("PUBLICAITON")
						.addText(publication.get(i));
			}
		} else
			listPublication = root.addElement("PUBLICAITONS").addAttribute(
					"count", "0");

		/*
		 * Element author2 = root.addElement( "AUTHOR" ).addAttribute( "name",
		 * "Krish" ).addAttribute( "location", "India" ).addText( "Javabeat.net"
		 * );
		 */

		return document;
	}

	/**
	 * 
	 * @param document
	 *            : XML file
	 * @param path
	 *            : place to save XML file
	 * @throws IOException
	 */

	public static void write(Document document, String path) throws IOException {
		// write to a file output.xml
		XMLWriter writer = new XMLWriter(new FileWriter(path));
		writer.write(document);
		writer.close();

		// Pretty print the document to System.out
		OutputFormat format = OutputFormat.createPrettyPrint();
		writer = new XMLWriter(System.out, format);
		writer.write(document);

		// Compact format to System.out
		// format = OutputFormat.createCompactFormat();
		// writer = new XMLWriter( System.out, format );
		// writer.write( document );

	}

}
