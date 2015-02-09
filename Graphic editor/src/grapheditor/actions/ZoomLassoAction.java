package grapheditor.actions;

import grapheditor.app.AppCore;
import grapheditor.view.DiagramView;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public class ZoomLassoAction extends AbstractGEDAction{

	public ZoomLassoAction() {

		putValue(ACCELERATOR_KEY,
				KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON, loadIcon("images/lasso_zoom.png"));
		putValue(NAME, "Lasso zoom");
		putValue(SHORT_DESCRIPTION, "Lasso zoom");
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		DiagramView diagramView = (DiagramView) AppCore.getInstance().getDesktop().getSelectedFrame();
		if (diagramView != null)
			diagramView.getStateManager().setLassoZoomState();
	}
	
}
