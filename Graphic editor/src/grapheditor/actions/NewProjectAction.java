package grapheditor.actions;

import grapheditor.app.AppCore;
import grapheditor.model.workspace.nodes.Diagram;
import grapheditor.model.workspace.nodes.Project;
import grapheditor.view.DiagramView;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;

import javax.swing.KeyStroke;

/**Klasa NewProjectAction nasledjuje klasu AbstractGEDAction i modeluje akciju
 * za kreiranje novog projekta unutar workspacea
 * @author Kantar
 */


@SuppressWarnings("serial")
public class NewProjectAction extends AbstractGEDAction{
	
	public NewProjectAction() {
		putValue(ACCELERATOR_KEY,KeyStroke.getKeyStroke(
		        KeyEvent.VK_J, ActionEvent.CTRL_MASK + ActionEvent.ALT_MASK));
		putValue(SMALL_ICON, loadIcon("images/newproject.png"));
		putValue(NAME, "New project");
		putValue(SHORT_DESCRIPTION, "New project");
	}
	
	public void actionPerformed(ActionEvent e) {
		Project project = new Project(" ");
		AppCore.getInstance().getWorkspaceTree().addProject(project);
		Diagram diagram=new Diagram(" ");
		project.add(diagram);
		
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
