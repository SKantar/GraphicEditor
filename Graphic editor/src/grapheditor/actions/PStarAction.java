package grapheditor.actions;

import grapheditor.app.AppCore;
import grapheditor.view.DiagramView;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public class PStarAction extends AbstractGEDAction{
	public PStarAction() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON, loadIcon("images/star.gif"));
		//putValue(NAME, "Star");
		putValue(SHORT_DESCRIPTION, "Star");
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		DiagramView diagramView = (DiagramView) AppCore.getInstance().getDesktop().getSelectedFrame();
		if (diagramView != null){
			diagramView.startStarState();

			AppCore.getInstance().getStatusBar().update_status_bar();
		}
	}
	
}
