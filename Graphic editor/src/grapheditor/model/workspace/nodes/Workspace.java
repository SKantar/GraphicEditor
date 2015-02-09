package grapheditor.model.workspace.nodes;

import grapheditor.app.AppCore;
import grapheditor.utils.Utils;
import grapheditor.view.DiagramView;

import javax.swing.JInternalFrame;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

/**
 * Klasa Workspace modeluje workspace koji sadrzi listu projekata
 * 
 * @author Kantar
 */

@SuppressWarnings("serial")
public class Workspace extends DefaultMutableTreeNode{

	private String name;

	public Workspace() {
		super();
		name = "Workspace";
	}

	public String toString() {
		return name;
	}

	@Override
	public void add(MutableTreeNode newChild) {
		super.add(newChild);
		for (int i = 1; i <= this.getChildCount() + 1; i++)
			if (isAvelableName("Project " + String.valueOf(i),(Project) newChild) && isAvelableName("* Project " + String.valueOf(i),(Project) newChild)) {
				((Project) newChild).setName("Project " + String.valueOf(i));
				break;
			}
	}


	@Override
	public void remove(MutableTreeNode aChild) {
		Project project = (Project) aChild;
		for (int i = 0; i < project.getChildCount(); i++) {
			Diagram diagram = (Diagram) project.getChildAt(i);
			JInternalFrame frame = Utils.getFrameByDiagram(diagram);
			if (frame != null)
				frame.dispose();
		}
		super.remove(aChild);
	}
	
	
	public boolean isAvelableName(String name, Project project) {
		for (int i = 0; i < this.getChildCount(); i++) {
			if (this.getChildAt(i) != project)
				if (this.getChildAt(i).toString().equals(name))
					return false;
		}
		return true;
	}
	
	public void reset() {
		removeAllChildren();
		AppCore.getInstance().getDesktop().removeAll();
		DiagramView.setOpenFrameCount(0);
	}
}
