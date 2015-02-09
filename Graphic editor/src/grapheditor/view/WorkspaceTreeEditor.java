package grapheditor.view;

import grapheditor.actions.RenameAction;
import grapheditor.app.AppCore;
import grapheditor.model.elements.DiagramElement;
import grapheditor.model.workspace.nodes.Diagram;
import grapheditor.model.workspace.nodes.ElementFolder;
import grapheditor.model.workspace.nodes.Project;
import grapheditor.model.workspace.nodes.Workspace;
import grapheditor.utils.Utils;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;

/**Klasa WorkspaceTreeEditor nasledjuje DefaultTreeCellEditor  i implementira ActionListener
 * klasi se vrsi promena imena stavka JTree-a
 * @author Kantar
 */


public class WorkspaceTreeEditor  extends DefaultTreeCellEditor{
	
	
    private Object stavka=null;
    private JTextField edit=null;
    
	public WorkspaceTreeEditor(JTree arg0, DefaultTreeCellRenderer arg1) {
		super(arg0, arg1);
	}

	public Component getTreeCellEditorComponent(JTree arg0, Object arg1, boolean arg2, boolean arg3, boolean arg4, int arg5) {

		stavka=arg1;
		
		edit=new JTextField(arg1.toString());
		edit.addActionListener(this);

		RenameAction.setEditing(false);
		return edit;
	}



	public boolean isCellEditable(EventObject arg0) {
			Object project =AppCore.getInstance().getWorkspaceTree().getLastSelectedPathComponent();
			if(project instanceof Workspace) return false;
			if ((arg0!=null && arg0 instanceof MouseEvent) || RenameAction.isEditing())
			if (( arg0!=null && ((MouseEvent)arg0).getClickCount()==3)  || RenameAction.isEditing()){
				return true;
			}
				return false;
	}

	

	public void actionPerformed(ActionEvent e){
		if (stavka instanceof Project){
			Project project = (Project)stavka;
			if(((Workspace)project.getParent()).isAvelableName(e.getActionCommand(), project)){
				for(int i = 0 ; i < project.getChildCount(); i++){
					Diagram diagram = (Diagram)(project.getChildAt(i));
					if(Utils.getFrameByDiagram(diagram) != null){
						Utils.getFrameByDiagram(diagram).setTitle(diagram.toString() + " " + e.getActionCommand());
					}
				}
				project.setName(e.getActionCommand());
			}
			else JOptionPane.showMessageDialog(null,"\tName exists, please choose a different");
		}
		if (stavka instanceof Diagram){
			
			Diagram diagram = (Diagram)stavka;
			Project project = (Project)diagram.getParent();
			
			if(project.isAvelableName(e.getActionCommand(), diagram)){
				if(Utils.getFrameByDiagram(diagram)!=null){
					Utils.getFrameByDiagram(diagram).setTitle(e.getActionCommand() + " " + project.toString());
				}
				((Diagram)stavka).setName(e.getActionCommand());
			}
			else JOptionPane.showMessageDialog(null,"Name exists, please choose a different");
		}
		
		if(stavka instanceof DiagramElement){
			DiagramElement element = (DiagramElement)stavka;

			ElementFolder elementFolder = (ElementFolder) element.getParent();
			Diagram diagram = (Diagram) elementFolder.getParent();
			if(element.getName().equals(e.getActionCommand())) return;
			if(diagram.getDiagramModel().isAvelableName(e.getActionCommand())){
				element.setName(e.getActionCommand());
				diagram.getDiagramModel().fireUpdatePerformed(element.getRepaintBounds());
			}
			else{
				JOptionPane.showMessageDialog(null,"Name exists, please choose a different");
			}
			
		}
      	 
	}

}
