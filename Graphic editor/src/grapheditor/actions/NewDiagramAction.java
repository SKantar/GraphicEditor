package grapheditor.actions;

import grapheditor.app.AppCore;
import grapheditor.model.workspace.nodes.Diagram;
import grapheditor.model.workspace.nodes.Project;
import grapheditor.view.DiagramView;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;

import javax.swing.KeyStroke;

/**Klasa NewDiagramAction nasledjuje klasu AbstractGEDAction i modeluje akciju
 * za kreiranje novog dijagrama unutar projekta
 * @author Kantar
 */

@SuppressWarnings("serial")
public class NewDiagramAction extends AbstractGEDAction{
	
	public NewDiagramAction() {
		putValue(ACCELERATOR_KEY,KeyStroke.getKeyStroke(
		        KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON, loadIcon("images/newdiagram.png"));
		putValue(NAME, "New diagram");
		putValue(SHORT_DESCRIPTION, "New diagram");
	}

	public void actionPerformed(ActionEvent e) {
		Object object =AppCore.getInstance().getWorkspaceTree().getLastSelectedPathComponent();
		if (object  instanceof Project) {
			Diagram diagram =new Diagram(" ");
			((Project)object).add(diagram);
			
	        DiagramView view=new DiagramView();
			view.setDiagram(diagram);
			AppCore.getInstance().getDesktop().add(view);
			
			try {
				view.setSelected(true);
			} catch (PropertyVetoException arg) {
				arg.printStackTrace();
			}
	}
		else if(object instanceof Diagram){
			Diagram diagram =new Diagram(" ");
			Project p = (Project)((Diagram)object).getParent();
			p.add(diagram);
	        DiagramView view=new DiagramView();
			view.setDiagram(diagram);
			AppCore.getInstance().getDesktop().add(view);
			
			try {
				view.setSelected(true);
			} catch (PropertyVetoException arg) {
				arg.printStackTrace();
			}
		}
	}
}