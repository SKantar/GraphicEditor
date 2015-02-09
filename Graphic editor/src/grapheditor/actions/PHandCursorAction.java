package grapheditor.actions;

import grapheditor.app.AppCore;
import grapheditor.view.DiagramView;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public class PHandCursorAction extends AbstractGEDAction {

	public PHandCursorAction() {
		putValue(ACCELERATOR_KEY,
				KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON, loadIcon("images/cursor.png"));
		// putValue(NAME, "Select mode");
		putValue(SHORT_DESCRIPTION, "Select mode");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		DiagramView diagramView = (DiagramView) AppCore.getInstance().getDesktop().getSelectedFrame();
		if (diagramView != null){
				diagramView.startSelectState();

				AppCore.getInstance().getStatusBar().update_status_bar();
		}
	}
}
