package grapheditor.actions;


import grapheditor.gui.help.AboutAuthorDialog;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

/**Klasa AboutAuthorAction nasledjuje klasu AbstractGEDAction i modeluje akciju
 * za prikaz About author  box dijaloga sa informacijama o autoru
 * @author Kantar
 */

@SuppressWarnings("serial")
public class AboutAuthorAction extends AbstractGEDAction{

	public AboutAuthorAction() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_F1, ActionEvent.ALT_MASK));
		putValue(SMALL_ICON, loadIcon("images/aboutauthor.png"));
		putValue(NAME, "About author");
		putValue(SHORT_DESCRIPTION, "About author");
	}

	
	public void actionPerformed(ActionEvent arg0) {
			new AboutAuthorDialog();
	}


}
