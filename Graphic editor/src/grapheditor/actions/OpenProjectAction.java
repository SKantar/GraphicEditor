package grapheditor.actions;

import grapheditor.app.AppCore;
import grapheditor.files.DiagramFileFilter;
import grapheditor.model.workspace.nodes.Project;
import grapheditor.model.workspace.nodes.Workspace;
import grapheditor.view.DiagramView;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public class OpenProjectAction extends AbstractGEDAction {

public OpenProjectAction() {
		
		/**
		 * Pomoću metode apstraktne klase AbstractAction putValue 
		 * postavljamo vrednosti 4 od 8 konstanti
		 * Kada povežemo ovaj Action sa bilo kojom komponentom koja nasleđuje JComponent
		 * komponenta će iz ovih konstanti postaviti svoj Accelerator, Icon, Name i Description 
		 */
		
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_O, ActionEvent.CTRL_MASK + ActionEvent.SHIFT_MASK));
		putValue(SMALL_ICON, loadIcon("images/openproject.png"));
		putValue(NAME, "Open project");
		putValue(SHORT_DESCRIPTION, "Open project");
	}

	public void actionPerformed(ActionEvent arg0) {
		JFileChooser jfc = new JFileChooser();
		jfc.setFileFilter(new DiagramFileFilter());
		
		if(jfc.showOpenDialog(AppCore.getInstance())==JFileChooser.APPROVE_OPTION){
			try {
				ObjectInputStream os = new ObjectInputStream(new FileInputStream(jfc.getSelectedFile()));
				  
				Project p=null;
				
					try {
						p = (Project) os.readObject();
					} catch (ClassNotFoundException e) {
						JOptionPane.showMessageDialog(AppCore.getInstance(),"File demiged");
					}

				Workspace root = (Workspace) AppCore.getInstance().getWorkspaceModel().getRoot();
				
				if(!root.isAvelableName(p.getName(), p) || !root.isAvelableName("* "+p.getName(), p)){
					for (int i = 1; i <= root.getChildCount() + 1; i++)
						if (root.isAvelableName(p.getName() + "(" + String.valueOf(i) + ")",p) && root.isAvelableName("* " + p.getName() + "(" + String.valueOf(i) + ")",p)) {
							p.setName(p.getName() + "(" + String.valueOf(i) + ")");
						break;
						}
				}
				
				
			     AppCore.getInstance().getWorkspaceTree().addOpenedProject(p, p.getName());
				
				 for (int i=0;i<p.getDiagramCount();i++){
				    DiagramView view=new DiagramView();
				    p.getDiagram(i).getDiagramModel().addUpdateListener(p);
				    p.getDiagram(i).setID();
				    view.setDiagram(p.getDiagram(i));
				    view.setVisible(false);
				    AppCore.getInstance().getDesktop().add(view);
				 }
				   os.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			} 
		}
		AppCore.getInstance().getWorkspaceTree().expandRow(0);
	}
}
