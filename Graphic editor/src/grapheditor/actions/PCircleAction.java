package grapheditor.actions;

import grapheditor.app.AppCore;
import grapheditor.view.DiagramView;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public class PCircleAction extends AbstractGEDAction{
	
	public PCircleAction() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON, loadIcon("images/circle.png"));
		//putValue(NAME, "Circle");
		putValue(SHORT_DESCRIPTION, "Circle");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		DiagramView diagramView = (DiagramView) AppCore.getInstance().getDesktop().getSelectedFrame();
		if (diagramView != null){
				diagramView.startCircleState();

				AppCore.getInstance().getStatusBar().update_status_bar();
		}
	}

}
