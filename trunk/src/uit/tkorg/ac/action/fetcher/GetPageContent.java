/**
 * 
 */
package uit.tkorg.ac.action.fetcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;

import org.htmlparser.beans.StringBean;

/**
 * @author tiendv, cuongnp
 *
 */
public class GetPageContent {
	
	/**
	 * 
	 * @param source : URL to get HTML content
	 * @return : String with HTML Tag
	 * @throws IOException
	 */
	  public static String getResults(URL source) throws IOException {
	        
	        InputStream in = source.openStream();
	        StringBuffer sb = new StringBuffer();
	        byte[] buffer = new byte[256];
	        while(true) {
	            int bytesRead = in.read(buffer);
	            if(bytesRead == -1) break;
	            for (int i=0; i<bytesRead; i++)
	                sb.append((char)buffer[i]);
	        }
	        return sb.toString();
	    }
	  public static String getWithUTF8(URL url) throws UnsupportedEncodingException, IOException {
		  	URLConnection urlc = url.openConnection();

	        //BufferedInputStream buffer = new BufferedInputStream(urlc.getInputStream());
	        BufferedReader buffer = new BufferedReader(new InputStreamReader(urlc.getInputStream(), "UTF8"));

	        StringBuilder builder = new StringBuilder();

	        int byteRead;

	        while ((byteRead = buffer.read()) != -1)
	            builder.append((char) byteRead);

	        buffer.close();
	        return builder.toString();
	  }
	  
	  /**
	   * 
	   * @param url : String URL 
	   * @return : String HTML content with out HTML Tag
	   */
	  
	    public static  String getUrlContentsAsText(String url) { 
	        String content = ""; 
	        StringBean stringBean = new StringBean(); 
	        stringBean.setURL(url); 
	        content = stringBean.getStrings(); 
	        return content; 
		}

}
