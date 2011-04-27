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
	      Element root = document.addElement( "Author" );
	      root.addAttribute("name",authorName);
	      Element author1 = root.addElement( "AUTHOR" ).addAttribute( "name",
	            "Muthu" ).addAttribute( "location", "India" ).addText(
	            "Javawave.blogspot.com" );

	      Element author2 = root.addElement( "AUTHOR" ).addAttribute( "name",
	            "Krish" ).addAttribute( "location", "India" ).addText(
	            "Javabeat.net" );

	      return document;
	   }
	
	/**
	 * 
	 * @param document : XML file
	 * @param path : place to save XML file
	 * @throws IOException
	 */
	
	 public static void write( Document document,String path ) throws IOException
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
