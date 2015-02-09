package grapheditor.actions;

import grapheditor.app.AppCore;
import grapheditor.view.DiagramView;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public class ZoomBestFitAction extends AbstractGEDAction{
	
	public ZoomBestFitAction() {

		putValue(ACCELERATOR_KEY,KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON, loadIcon("images/best_fit.png"));
		putValue(NAME, "Best fit zoom");
		putValue(SHORT_DESCRIPTION, "Best fit zoom");
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		DiagramView diagramView = (DiagramView)AppCore.getInstance().getDesktop().getSelectedFrame();
		if(diagramView != null)
			diagramView.bestFitZoom();
		
	}

}
