package grapheditor.actions;

import java.net.URL;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;
/**Klasa AbstractGEDAction nasledjuje klasu AbstractAction, a svaka
 * naslednica ove klase predstavlja po jednu akciju u file, popup meniju ili 
 * toolbaru. Svaka naslednica klase AbstractGEDAction redefinise
 * metodu actionPerformed klase AbstractAction koja definise
 * sta ce se dogoditi kada se okine akcija
 * @author Kantar
 */

@SuppressWarnings("serial")
public  abstract class AbstractGEDAction extends AbstractAction{

	/**Kreira ikonu na osnovu naziva fajla zadatog relativno u odnosu na ovu klasu
	 * @param fileName Naziv fajla slike sa relativnom putanjom
	 * @return - vraca slicicu enkapsuliranu u klasi Icon
	 */
	
	public Icon loadIcon(String fileName){
		URL imageURL = getClass().getResource(fileName);
		Icon icon = null;
		
		if (imageURL != null) {                      
	        icon = new ImageIcon(imageURL);
	    } else {                                     
	        System.err.println("Resource not found: " + fileName);
	    }

		return icon;
	}

	
	


}
