package grapheditor.actions;

import grapheditor.app.AppCore;
import grapheditor.view.DiagramView;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;


@SuppressWarnings("serial")
public class UndoAction extends AbstractGEDAction {

	public UndoAction(){
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
		putValue(MNEMONIC_KEY, KeyEvent.VK_U);
		putValue(SMALL_ICON, loadIcon("images/undo.png"));
		putValue(NAME, "Undo");
		putValue(SHORT_DESCRIPTION, "Undo");
		setEnabled(false);
	}
	
	public void actionPerformed(ActionEvent e) {
		DiagramView view=(DiagramView) AppCore.getInstance().getDesktop().getSelectedFrame();
		view.getCommandManager().undoCommand();
	}
}
