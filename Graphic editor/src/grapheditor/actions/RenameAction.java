package grapheditor.actions;

import grapheditor.app.AppCore;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

/**Klasa RenameAction nasledjuje klasu AbstractGEDAction i modeluje akciju
 * inicira izmenu naziva projekta
 * @author Kantar
 */

@SuppressWarnings("serial")
public class RenameAction extends AbstractGEDAction{
	
	private static boolean editing = false;
	
	public RenameAction() {
		//putValue(SMALL_ICON,new ImageIcon(getClass().getResource("../rename.png")));
		putValue(MNEMONIC_KEY,KeyEvent.VK_R);
		putValue(ACCELERATOR_KEY,KeyStroke.getKeyStroke(
                KeyEvent.VK_R, ActionEvent.CTRL_MASK));
		putValue(NAME,"Rename");
		putValue(SHORT_DESCRIPTION,"Rename");
	}

	
	public void actionPerformed(ActionEvent arg0) {
		editing=true;
		AppCore.getInstance().getWorkspaceTree().startEditingAtPath(AppCore.getInstance().getWorkspaceTree().getSelectionPath());		
	}


	public static boolean isEditing() {
		return editing;
	}


	public static void setEditing(boolean editing) {
		RenameAction.editing = editing;
	}
	
	

}
