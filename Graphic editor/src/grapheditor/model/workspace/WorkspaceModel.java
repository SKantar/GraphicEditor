package grapheditor.model.workspace;

import javax.swing.tree.DefaultTreeModel;

import grapheditor.model.workspace.nodes.Project;
import grapheditor.model.workspace.nodes.Workspace;

/**Klasa WorkspaceModel  nasledjuje klasu DefaultTreeModel i sadrzi
 * metodu za dodavanje projekta u workspace
 * @author Kantar
 */


@SuppressWarnings("serial")
public class WorkspaceModel extends DefaultTreeModel {
	
	public WorkspaceModel() {
		super(new Workspace());
	}
	
	public void addProject(Project project){
		((Workspace)getRoot()).add(project);
	}
	
	public void addOpenedProject(Project project, String name){
		((Workspace)getRoot()).add(project);
		project.setName(name);
	}

}
