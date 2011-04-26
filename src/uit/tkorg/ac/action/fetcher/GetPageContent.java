/**
 * 
 */
package uit.tkorg.ac.action.fetcher;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.htmlparser.beans.StringBean;

/**
 * @author tiendv, cuongnp
 *
 */
public class GetPageContent {
	
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
	  
	    public static  String getUrlContentsAsText(String url) { 
	        String content = ""; 
	        StringBean stringBean = new StringBean(); 
	        stringBean.setURL(url); 
	        content = stringBean.getStrings(); 
	        return content; 
		}

}
