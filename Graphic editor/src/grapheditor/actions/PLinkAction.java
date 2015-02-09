package grapheditor.actions;

import grapheditor.app.AppCore;
import grapheditor.view.DiagramView;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public class PLinkAction extends AbstractGEDAction {

	public PLinkAction() {

		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON, loadIcon("images/link.png"));
		putValue(SHORT_DESCRIPTION, "Link");
	}

	public void actionPerformed(ActionEvent arg0) {
		DiagramView diagramView = (DiagramView) AppCore.getInstance()
				.getDesktop().getSelectedFrame();
		if (diagramView != null){
			diagramView.startLinkState();
			AppCore.getInstance().getStatusBar().update_status_bar();
		}
	}
}
