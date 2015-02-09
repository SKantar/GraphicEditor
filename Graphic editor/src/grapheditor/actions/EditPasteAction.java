package grapheditor.actions;

import grapheditor.app.AppCore;
import grapheditor.view.DiagramView;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

/**Klasa <b>EditPasteAction</b> nasledjuje klasu <b>AbstractEditorAction</b> i modeluje akciju
 * za kopiranje sa clipboard-a na kanvas 
 * @author Tim5
 */
@SuppressWarnings("serial")
public class EditPasteAction extends AbstractGEDAction {
	EditPasteAction() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_V, ActionEvent.CTRL_MASK));
		putValue(MNEMONIC_KEY, KeyEvent.VK_P);
		putValue(SMALL_ICON, loadIcon("images/paste.png"));
		putValue(NAME, "Paste");
		putValue(SHORT_DESCRIPTION, "Paste");
		setEnabled(false);
	}
	
	public void actionPerformed(ActionEvent e) {
		((DiagramView)(AppCore.getInstance().getDesktop().getSelectedFrame())).paste();
	}


	
}