package grapheditor.actions;

import grapheditor.app.AppCore;
import grapheditor.model.workspace.nodes.Diagram;
import grapheditor.utils.Utils;
import grapheditor.view.DiagramView;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

/**Klasa CloseDiagramAction nasledjuje klasu AbstractGEDAction i modeluje akciju
 * zatvara prozor trenutno selektovanog dijagrama
 * @author Kantar
 */

@SuppressWarnings("serial")
public class CloseDiagramAction extends AbstractGEDAction{

	public CloseDiagramAction() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_W, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON, loadIcon("images/closediagram.png"));
		putValue(NAME, "Close diagram");
		putValue(SHORT_DESCRIPTION, "Close diagram");
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		Object diagram =AppCore.getInstance().getWorkspaceTree().getLastSelectedPathComponent();
		if(diagram instanceof Diagram){
			DiagramView diagramView = (DiagramView)Utils.getFrameByDiagram((Diagram)diagram);
			if(diagramView != null){
				diagramView.setVisible(false);
				AppCore.getInstance().getDesktop().selectFrame(true);
			}
		}
		
	}
}
