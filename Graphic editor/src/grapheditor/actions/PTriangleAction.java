package grapheditor.actions;

import grapheditor.app.AppCore;
import grapheditor.view.DiagramView;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public class PTriangleAction extends AbstractGEDAction {
	
	public PTriangleAction() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON, loadIcon("images/triangle.png"));
		//putValue(NAME, "Triangle");
		putValue(SHORT_DESCRIPTION, "Triangle");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		DiagramView diagramView = (DiagramView) AppCore.getInstance().getDesktop().getSelectedFrame();
		if (diagramView != null){	
			diagramView.startTriangleState();	

			AppCore.getInstance().getStatusBar().update_status_bar();
		}
	}

}
