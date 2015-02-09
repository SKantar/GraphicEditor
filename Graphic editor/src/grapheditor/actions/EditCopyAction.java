package grapheditor.actions;

import grapheditor.app.AppCore;
import grapheditor.model.DiagramElementsSelection;
import grapheditor.view.DiagramView;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

/**Klasa <b>EditCopyAction</b> nasledjuje klasu <b>AbstractEditorAction</b> i modeluje akciju
 * za kopiranje elemenata sa kanvasa na clipboard
 * @author Tim5
 */
@SuppressWarnings("serial")
public class EditCopyAction extends AbstractGEDAction {
	
	EditCopyAction() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_C, ActionEvent.CTRL_MASK));
		putValue(MNEMONIC_KEY, KeyEvent.VK_C);
		putValue(SMALL_ICON, loadIcon("images/copy.png"));
		putValue(NAME, "Copy");
		putValue(SHORT_DESCRIPTION, "Copy");
		setEnabled(false);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(!(((DiagramView)(AppCore.getInstance().getDesktop().getSelectedFrame())).getDiagram().getSelectionModel().getSelectionListSize()==0)){
			AppCore.getInstance().setPasteCounter(0);
			DiagramElementsSelection contents = new DiagramElementsSelection (((DiagramView)(AppCore.getInstance().getDesktop().getSelectedFrame())).getDiagram().getSelectionModel().getSelected());
			AppCore.getInstance().getClipboard().setContents (contents, AppCore.getInstance());

			AppCore.getInstance().getActionManager().getEditPasteAction().setEnabled(true);
		}
		
	}
}