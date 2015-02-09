package grapheditor.gui;

import grapheditor.controller.WorkspaceTreeController;
import grapheditor.controller.WorkspaceTreeListener;
import grapheditor.model.workspace.WorkspaceModel;
import grapheditor.model.workspace.nodes.Project;
import grapheditor.view.WorkspaceTreeCellRendered;
import grapheditor.view.WorkspaceTreeEditor;

import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

/**Klasa WorkspaceTree nasledjuje klasu JTree i predtavlja
 * stablo aplikacije
 * @author Kantar
 */

@SuppressWarnings("serial")
public class WorkspaceTree extends JTree{
	private PopUpMenu popUpMenu;

public WorkspaceTree() {
		popUpMenu = new PopUpMenu();
		addTreeSelectionListener(new WorkspaceTreeController());
		getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
	    setCellEditor(new WorkspaceTreeEditor(this,new DefaultTreeCellRenderer()));
	    setCellRenderer(new WorkspaceTreeCellRendered());
	    setEditable(true);
	    addMouseListener(new WorkspaceTreeListener());
	}

	
	/**
	 * Metoda za dodavanje novog projekta u workspace 
	 * @param project
	 */
	public void addProject(Project project){
		((WorkspaceModel)getModel()).addProject(project);
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	public void addOpenedProject(Project project, String name){
		((WorkspaceModel)getModel()).addOpenedProject(project, name);
		SwingUtilities.updateComponentTreeUI(this);
	}


	public PopUpMenu getPopUpMenu() {
		return popUpMenu;
	}

	public Project getCurrentProject() {
		TreePath path = getSelectionPath();
		for(int i=0; i<path.getPathCount(); i++){
			if(path.getPathComponent(i) instanceof Project){
				return (Project)path.getPathComponent(i);
			}
		}
		return null;
	}
	
	

}
