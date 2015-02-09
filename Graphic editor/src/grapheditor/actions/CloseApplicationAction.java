package grapheditor.actions;

import grapheditor.app.AppCore;
import grapheditor.model.workspace.nodes.Project;
import grapheditor.model.workspace.nodes.Workspace;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

/**
 * Klasa CloseApplicationAction nasledjuje klasu AbstractGEDAction i modeluje
 * akciju zatvara aplikaciju uz prethodnu potvrdu
 * 
 * @author Kantar
 */

@SuppressWarnings("serial")
public class CloseApplicationAction extends AbstractGEDAction {

	public CloseApplicationAction() {
		putValue(SMALL_ICON, loadIcon("images/close.png"));
		putValue(NAME, "Exit");
		putValue(SHORT_DESCRIPTION, "Close program");
	}

	public void actionPerformed(ActionEvent arg0) {
		Workspace w = (Workspace) AppCore.getInstance().getWorkspaceTree()
				.getModel().getRoot();
		for (int i = 0; i < w.getChildCount(); i++) {
			if (((Project) w.getChildAt(i)).isChanged()) {
				int result = JOptionPane.showConfirmDialog(AppCore.getInstance(),"Do you want to save workspace?");
				if (result == JOptionPane.YES_OPTION) {
					AppCore.getInstance().getActionManager()
							.getSaveWorkspaceAction().actionPerformed(null);
				}
				break;
			}
		}
		int result = JOptionPane.showConfirmDialog(AppCore.getInstance(),"Are you sure you wish to quit the Programe?");
		if (result == JOptionPane.YES_OPTION) {
			System.exit(0);
		}

	}

}
