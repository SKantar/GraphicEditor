package grapheditor.actions;

import grapheditor.app.AppCore;
import grapheditor.view.DiagramView;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public class RedoAction extends AbstractGEDAction {
	
	public RedoAction(){
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
		putValue(MNEMONIC_KEY, KeyEvent.VK_R);
		putValue(SMALL_ICON, loadIcon("images/redo.png"));
		putValue(NAME, "Redo");
		putValue(SHORT_DESCRIPTION, "Redo");
		setEnabled(false);
	}
	
	public void actionPerformed(ActionEvent e) {
		DiagramView view = (DiagramView) AppCore.getInstance().getDesktop().getSelectedFrame();
		view.getCommandManager().doCommand();

	}

}
