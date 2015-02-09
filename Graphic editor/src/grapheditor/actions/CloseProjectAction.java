package grapheditor.actions;

import grapheditor.app.AppCore;
import grapheditor.model.workspace.nodes.Diagram;
import grapheditor.model.workspace.nodes.Project;
import grapheditor.utils.Utils;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JInternalFrame;
import javax.swing.KeyStroke;
import javax.swing.tree.TreePath;

/**
 * Klasa CloseProjectAction nasledjuje klasu AbstractGEDAction i modeluje akciju
 * zatvara sve prozore trenutno selektovanog projekta
 * 
 * @author Kantar
 */

@SuppressWarnings("serial")
public class CloseProjectAction extends AbstractGEDAction {

	public CloseProjectAction() {
		putValue(ACCELERATOR_KEY,
				KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON, loadIcon("images/closeproject.png"));
		putValue(NAME, "Close project");
		putValue(SHORT_DESCRIPTION, "Close project");
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object object = AppCore.getInstance().getWorkspaceTree().getLastSelectedPathComponent();
		if (object instanceof Project) {

			Project project = (Project) object;
			TreePath selPath = new TreePath(project.getPath());
		
			for (int i = 0; i < project.getChildCount(); i++) {
				Diagram diagram = (Diagram) project.getChildAt(i);
				JInternalFrame frame = Utils.getFrameByDiagram(diagram);
				if (frame != null)
					frame.setVisible(false);
			}
			AppCore.getInstance().getWorkspaceTree().collapsePath(selPath);

		} else if (object instanceof Diagram) {
			
			Project project = (Project)((Diagram)object).getParent();
			TreePath selPath = new TreePath(project.getPath());
			
			for (int i = 0; i < project.getChildCount(); i++) {
				Diagram diagram = (Diagram) project.getChildAt(i);
				JInternalFrame frame = Utils.getFrameByDiagram(diagram);
				if (frame != null)
					frame.setVisible(false);
			}
			AppCore.getInstance().getWorkspaceTree().collapsePath(selPath);
		}
		AppCore.getInstance().getDesktop().selectFrame(true);

	}

}
