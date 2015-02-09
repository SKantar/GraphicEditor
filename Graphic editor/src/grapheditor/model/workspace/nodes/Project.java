package grapheditor.model.workspace.nodes;

import grapheditor.app.AppCore;
import grapheditor.event.UpdateEvent;
import grapheditor.event.UpdateListener;
import grapheditor.utils.Utils;
import grapheditor.view.DiagramView;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import javax.swing.JInternalFrame;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

/**
 * Klasa Project modeluje projekat koji sadrzi listu dijagrama
 * 
 * @author Kantar
 */

@SuppressWarnings("serial")
public class Project extends DefaultMutableTreeNode implements Serializable, UpdateListener {

	private String name;
	private transient boolean changed; 
	private File projectFile;
	
	public Project(String name) {
		super();
		this.name = name;
		this.changed = false;
		this.projectFile=null;
	}

	public String toString(){
		return ((changed?"* ":"")+ name);
	}	

	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public void add(MutableTreeNode newChild) {
		super.add(newChild);
		((Diagram)newChild).getDiagramModel().addUpdateListener(this);
		setChanged(true);
		for (int i = 1; i <= this.getChildCount() + 1; i++)
			if (isAvelableName("Diagram " + String.valueOf(i),
					(Diagram) newChild)) {
				((Diagram) newChild).setName("Diagram " + String.valueOf(i));
				break;
			}

		//AppCore.getInstance().getPalette().getHandCursor().setSelected(true);
		SwingUtilities.updateComponentTreeUI(AppCore.getInstance()
				.getWorkspaceTree());
	}

	@Override
	public void remove(MutableTreeNode aChild) {
		JInternalFrame frame = Utils.getFrameByDiagram((Diagram) aChild);
		if (frame != null)
			frame.dispose();
		super.remove(aChild);
	}

	public boolean isAvelableName(String name, Diagram diagram) {
		for (int i = 0; i < this.getChildCount(); i++) {
			if (this.getChildAt(i) != diagram)
				if (this.getChildAt(i).toString().equals(name))
					return false;
		}
		return true;
	}
	
	public boolean isChanged() {
		return changed;
	}


	public void setChanged(boolean changed) {
		if (this.changed!=changed){
		     this.changed=changed;
		     SwingUtilities.updateComponentTreeUI(AppCore.getInstance().getWorkspaceTree());
		     List<DiagramView> visibleDiagrams = Utils.getVisibleDiagrams();
		     for(DiagramView view : visibleDiagrams){
		    	 if(view.getTitle().contains(getName()))
		    		 view.setTitle(view.getDiagram().toString() + " " + toString());
		     }
		}
	}
	
	public File getProjectFile() {
		return projectFile;
	}


	public void setProjectFile(File projectFile) {
		this.projectFile = projectFile;
	}

	public int getDiagramCount() {
		return getChildCount();
	}

	public Diagram getDiagram(int index) {
		return (Diagram)getChildAt(index);
	}

	@Override
	public void updatePerformed(UpdateEvent e) {
			setChanged(true);
	}
	
}
