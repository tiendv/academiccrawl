/**
 * 
 */
package uit.tkorg.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import uit.tkorg.ac.action.fetcher.AcademicFetcher;
import uit.tkorg.ac.action.fetcher.GetPageContent;
import uit.tkorg.ac.core.fetcher.AcademicFetcherCore;
import uit.tkorg.ac.properties.file.AcademicCrawlConst;

/**
 * @author tiendv
 *
 */
public class AcademicApplicaiton {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException{ 
		String _keyword = "";
		String _pageContent = "";
		_keyword = JOptionPane.showInputDialog("Tu khoa : ","Nhap tu khoa");
		_keyword = _keyword.replaceAll(" ", "+");
		String _urlString = AcademicCrawlConst.URL_START + _keyword + AcademicCrawlConst.DOMAIN_COMPUTER_SCIENCE;
		URL temp = new URL(_urlString);
		_pageContent = GetPageContent.getResults(temp);
		
		
		int checkStatus = AcademicFetcherCore.checkSearchStatus(_pageContent);
		if (checkStatus == 1)
			JOptionPane.showMessageDialog(null, " Khong co tac gia nao!", "Error", JOptionPane.ERROR_MESSAGE);
		else {
			if(checkStatus == 2)
				AcademicFetcher.fetch(_pageContent);
			else {	
				
				int status =JOptionPane.showConfirmDialog(null, "Co mot so tac gia trung ten ! Tiep tuc?","Thong bao", JOptionPane.YES_NO_OPTION, JOptionPane.YES_NO_OPTION);
				if (status == 1)
					return;
					else
					{
						ArrayList<String> _lstLink = new ArrayList<String>();
						_lstLink = AcademicFetcherCore.getSuggestionAuthorLink(_pageContent);
						for (int i = 0;i <_lstLink.size();i++) {
							System.out.printf(_lstLink.get(i));
							String nameAuthor = AcademicFetcher.getAuthorNameFromURL(_lstLink.get(i));
							int chose =JOptionPane.showConfirmDialog(null,"Co phai : "+nameAuthor +"?","Thong bao", JOptionPane.YES_NO_OPTION, JOptionPane.YES_NO_OPTION);
								if (chose ==0) {
								URL authorLink = new URL(_lstLink.get(i));
								String pageContent = GetPageContent.getResults(authorLink);
								AcademicFetcher.fetch(pageContent);
								}
								
							
								
						}
					}
			}
		}


	
	}

}
