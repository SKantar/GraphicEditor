package grapheditor.actions;

import grapheditor.app.AppCore;
import grapheditor.view.DiagramView;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public class SelectAllAction extends AbstractGEDAction {

	SelectAllAction(){
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_A, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON, loadIcon("images/selectall.png"));
		putValue(MNEMONIC_KEY, KeyEvent.VK_A);
		putValue(NAME, "SelectAll");
		putValue(SHORT_DESCRIPTION, "Selects all");
	}
	
	public void actionPerformed(ActionEvent e) {
		
		DiagramView view= (DiagramView) AppCore.getInstance().getDesktop().getSelectedFrame();
		if(view != null)
			view.getDiagram().getSelectionModel().addToSelectionList(view.getDiagram().getDiagramModel().getDiagramElements());

	}

}
