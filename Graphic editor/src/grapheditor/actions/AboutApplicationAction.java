package grapheditor.actions;

import grapheditor.gui.help.AboutApplicationDialog;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

/**Klasa AboutApplicationAction nasledjuje klasu AbstractGEDAction i modeluje akciju
 * za prikaz About GED  box dijaloga sa informacijama o aplikaciji
 * @author Kantar
 */

@SuppressWarnings("serial")
public class AboutApplicationAction extends AbstractGEDAction{

	public AboutApplicationAction() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_F2, ActionEvent.ALT_MASK));
		putValue(SMALL_ICON, loadIcon("images/aboutapplication.png"));
		putValue(NAME, "About GED");
		putValue(SHORT_DESCRIPTION, "About GED");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		new AboutApplicationDialog();
		
	}
	

}
