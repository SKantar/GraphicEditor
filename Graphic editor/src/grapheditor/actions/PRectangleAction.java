package grapheditor.actions;

import grapheditor.app.AppCore;
import grapheditor.view.DiagramView;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public class PRectangleAction extends AbstractGEDAction {
	
	public PRectangleAction() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON, loadIcon("images/rectangle.png"));
		//putValue(NAME, "Rectangle");
		putValue(SHORT_DESCRIPTION, "Rectangle");
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		DiagramView diagramView = (DiagramView) AppCore.getInstance().getDesktop().getSelectedFrame();
		if (diagramView != null){
			diagramView.startRectangleState();

			AppCore.getInstance().getStatusBar().update_status_bar();
		}
	}

}
