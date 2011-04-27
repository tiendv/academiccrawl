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
	
	public static Document buildDocument(String authorName,String interest,ArrayList<String> coAuthor, ArrayList<String> conference, ArrayList<String> journal, ArrayList<String> keyword, ArrayList<String> publication )
	   {
			
	      Document document = DocumentHelper.createDocument();
	      // root
	      Element root = document.addElement( "Author" );
	      root.addAttribute("name",authorName);
	      root.addAttribute("interest", interest);
	     
	      // CoAuthor
	      Element listCoAuthor = root.addElement( "COAUTHOR");
	      if (coAuthor.isEmpty() == false)
	      for(int i =0; i<coAuthor.size();i++ ) {
	    	  Element nameCorAuthor = listCoAuthor.addElement ("AUTHOR").addText(coAuthor.get(i));
	      }
	      // Conference
	     Element listConference =root.addElement( "CONFERENCES");
	     if (conference.isEmpty() == false)
	    	 for(int i =0; i< conference.size();i++) {
	    		 Element nameConference = listConference.addElement ("CONFERENCE").addText(conference.get(i));  
	    	 }
	     // Journal
	     Element listJournal = root.addElement( "JOURNALS");
	     if (journal.isEmpty() == false)
	    	 for(int i =0; i< journal.size();i++) {
	    		 Element nameJournal = listJournal.addElement ("JOURNAL").addText(journal.get(i));  
	    	 }
	     //Keyword
	     Element listKeyword = root.addElement( "KEYWORDS");
	     if (keyword.isEmpty() == false)
	    	 for(int i =0; i< keyword.size();i++) {
	    		 Element nameKeyword = listKeyword.addElement ("KEYWORD").addText(keyword.get(i));  
	    	 }
	 
	     // Publicaiton
	     
	     Element listPublication = root.addElement( "PUBLICAITONS");
	     if (publication.isEmpty() == false)
	    	 for(int i =0; i< publication.size();i++) {
	    		 Element title = listPublication.addElement ("PUBLICAITON").addText(publication.get(i));  
	    	 }
	   

	/*      Element author2 = root.addElement( "AUTHOR" ).addAttribute( "name",
	            "Krish" ).addAttribute( "location", "India" ).addText(
	            "Javabeat.net" );*/

	      return document;
	   }
	
	/**
	 * 
	 * @param document : XML file
	 * @param path : place to save XML file
	 * @throws IOException
	 */
	
	 public static void write( Document document,String path) throws IOException
	   {
	      // write to a file output.xml
	      XMLWriter writer = new XMLWriter( new FileWriter( path ) );
	      writer.write( document );
	      writer.close();

	      // Pretty print the document to System.out
	      OutputFormat format = OutputFormat.createPrettyPrint();
	      writer = new XMLWriter( System.out, format );
	      writer.write( document );

	      // Compact format to System.out
	      format = OutputFormat.createCompactFormat();
	      writer = new XMLWriter( System.out, format );
	      writer.write( document );
	      
	   }

}
