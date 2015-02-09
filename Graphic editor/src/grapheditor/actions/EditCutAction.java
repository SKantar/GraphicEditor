package grapheditor.actions;

import grapheditor.app.AppCore;
import grapheditor.commands.DeleteElementsCommand;
import grapheditor.model.DiagramElementsSelection;
import grapheditor.view.DiagramView;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;


@SuppressWarnings("serial")
public class EditCutAction extends AbstractGEDAction {
	
	EditCutAction() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		putValue(MNEMONIC_KEY, KeyEvent.VK_T);
		putValue(SMALL_ICON, loadIcon("images/cut.png"));
		putValue(NAME, "Cut");
		putValue(SHORT_DESCRIPTION, "Cut");
		setEnabled(false);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(!(((DiagramView)(AppCore.getInstance().getDesktop().getSelectedFrame())).getDiagram().getSelectionModel().getSelectionListSize()==0)){
			AppCore.getInstance().setPasteCounter(-1);
			DiagramElementsSelection contents = new DiagramElementsSelection (((DiagramView)(AppCore.getInstance().getDesktop().getSelectedFrame())).getDiagram().getSelectionModel().getSelected());
			AppCore.getInstance().getClipboard().setContents(contents, AppCore.getInstance());
			DiagramView view= (DiagramView) AppCore.getInstance().getDesktop().getSelectedFrame();
    		view.getCommandManager().addCommand(new DeleteElementsCommand(view));
			AppCore.getInstance().getActionManager().getEditPasteAction().setEnabled(true);
			
		}
	}
}