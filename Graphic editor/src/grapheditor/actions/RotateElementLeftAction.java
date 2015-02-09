package grapheditor.actions;

import grapheditor.app.AppCore;
import grapheditor.commands.RotateElementCommand;
import grapheditor.view.DiagramView;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public class RotateElementLeftAction extends AbstractGEDAction{
	
	public RotateElementLeftAction() {

		putValue(ACCELERATOR_KEY,
				KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON, loadIcon("images/rotate_left.png"));
		putValue(NAME, "Rotate Left");
		putValue(SHORT_DESCRIPTION, "Rotate Left");
		setEnabled(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		DiagramView diagramView = (DiagramView)AppCore.getInstance().getDesktop().getSelectedFrame();
		if(diagramView != null){
			if(diagramView.getDiagram().getSelectionModel().getSelectionListSize() == 1)
				diagramView.getCommandManager().addCommand(new RotateElementCommand(diagramView, false));
		
		}
	}

}
