package grapheditor.actions;

import grapheditor.app.AppCore;
import grapheditor.view.DiagramView;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public class ZoomOutAction extends AbstractGEDAction {
	
	public ZoomOutAction() {

		putValue(ACCELERATOR_KEY,
				KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));

		putValue(SMALL_ICON, loadIcon("images/zoom_out.png"));
		putValue(NAME, "Zoom out");
		putValue(SHORT_DESCRIPTION, "Zoom out");
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		DiagramView diagramView = (DiagramView)AppCore.getInstance().getDesktop().getSelectedFrame();
		if(diagramView != null)
		 diagramView.zoomOut();
		
	}

}
