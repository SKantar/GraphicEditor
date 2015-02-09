package grapheditor.actions;

import grapheditor.app.AppCore;
import grapheditor.commands.DeleteElementsCommand;
import grapheditor.view.DiagramView;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

/**Klasa DeleteAction nasledjuje klasu AbstractGEDAction i modeluje akciju
 * za brisanje elementa iz WorkspaceTree-a
 * @author Kantar
 */

@SuppressWarnings("serial")
public class DeleteElementsAction extends AbstractGEDAction{

	public DeleteElementsAction() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_DELETE,0));
		putValue(SMALL_ICON, loadIcon("images/delete.png"));
		putValue(NAME, "Delete");
		putValue(SHORT_DESCRIPTION, "Delete");
	}
	
	public void actionPerformed(ActionEvent e) {
		int answer;
		answer = JOptionPane.showConfirmDialog(AppCore.getInstance(), 
	            "Are you sure you want to delete selected elements", "Delete elements", 
	            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
	    	if (answer == JOptionPane.YES_OPTION){
	    		DiagramView view= (DiagramView) AppCore.getInstance().getDesktop().getSelectedFrame();
	    		view.getCommandManager().addCommand(new DeleteElementsCommand(view));
	    	}
		}
	
	/*public static void delete() {
		DiagramView view= (DiagramView) AppCore.getInstance().getDesktop().getSelectedFrame();
		ArrayList<DiagramElement> forRemove = new ArrayList<DiagramElement>();
		if (!view.getDiagram().getSelectionModel().getSelectionList().isEmpty()){
			Iterator<DiagramElement> it=view.getDiagram().getSelectionModel().getSelectionListIterator();
			while (it.hasNext()){
				DiagramElement element=it.next();
				if (element instanceof LinkElement)
				{
					((LinkElement)element).setFree();
				}
				forRemove.add(element);
				
			}
			for (int i=0;i<view.getDiagram().getDiagramModel().getElementCount();i++) {
				if (view.getDiagram().getDiagramModel().getElementAt(i) instanceof LinkElement) {
					DiagramDevice dd1=((LinkElement)view.getDiagram().getDiagramModel().getElementAt(i)).getStartDevice();
					DiagramDevice dd2=((LinkElement)view.getDiagram().getDiagramModel().getElementAt(i)).getEndDevice();
					if (view.getDiagram().getSelectionModel().getSelectionList().contains(dd1) || view.getDiagram().getSelectionModel().getSelectionList().contains(dd2))
						forRemove.add((DiagramElement)view.getDiagram().getDiagramModel().getElementAt(i));
				}
			}
			for(DiagramElement element : forRemove){
				AppCore.getInstance().getWorkspaceTree().clearSelection();
				if(element instanceof LinkElement){
					((LinkElement)element).setFree();
				}
				view.getDiagram().getDiagramModel().removeFromJTree(element);
				view.getDiagram().getDiagramModel().removeDiagramElement(element);
			}
			view.getDiagram().getSelectionModel().removeAllFromSelectionList();
			AppCore.getInstance().getStatusBar().Update();
		}
	}*/



}
