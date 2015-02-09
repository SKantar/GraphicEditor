package grapheditor.actions;

import grapheditor.app.AppCore;
import grapheditor.model.workspace.nodes.Diagram;
import grapheditor.model.workspace.nodes.Project;
import grapheditor.model.workspace.nodes.Workspace;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

/**Klasa DeleteAction nasledjuje klasu AbstractGEDAction i modeluje akciju
 * za brisanje elementa iz WorkspaceTree-a
 * @author Kantar
 */

@SuppressWarnings("serial")
public class DeleteAction extends AbstractGEDAction{

	public DeleteAction() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_DELETE,ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON, loadIcon("images/delete.png"));
		putValue(NAME, "Delete");
		putValue(SHORT_DESCRIPTION, "Delete");
	}
	
	public void actionPerformed(ActionEvent e) {
		int answer;
		Object object =AppCore.getInstance().getWorkspaceTree().getLastSelectedPathComponent();
		if (object instanceof Project){
			answer = JOptionPane.showConfirmDialog(AppCore.getInstance(), 
		            "Are you sure you want to delete "+((Project)object).toString()+" project?", "Delete project", 
		            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		    if (answer == JOptionPane.YES_OPTION){
			((Workspace)(AppCore.getInstance().getWorkspaceModel().getRoot())).remove((Project)object);
			AppCore.getInstance().getDesktop().selectFrame(true);
		    }
		}
		else if (object instanceof Diagram){
			Diagram diagram = (Diagram)object;
			Project project = (Project)diagram.getParent();
			answer = JOptionPane.showConfirmDialog(AppCore.getInstance(), 
		            "Are you sure you want to delete "+((Diagram)object).toString()+" diagram?", "Delete diagram", 
		            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		    if (answer == JOptionPane.YES_OPTION){
				project.remove((Diagram)object);
				AppCore.getInstance().getDesktop().selectFrame(true);
			}
		}
		SwingUtilities.updateComponentTreeUI(AppCore.getInstance().getWorkspaceTree());
		
		}

}
