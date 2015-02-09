package grapheditor.actions;

import grapheditor.app.AppCore;
import grapheditor.files.WorkspaceFileFilter;
import grapheditor.model.workspace.nodes.Project;
import grapheditor.model.workspace.nodes.Workspace;
import grapheditor.view.DiagramView;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public class OpenWorkspaceAction extends AbstractGEDAction {

	public OpenWorkspaceAction() {

		/**
		 * Pomoću metode apstraktne klase AbstractAction putValue postavljamo
		 * vrednosti 4 od 8 konstanti Kada povežemo ovaj Action sa bilo kojom
		 * komponentom koja nasleđuje JComponent komponenta će iz ovih
		 * konstanti postaviti svoj Accelerator, Icon, Name i Description
		 */

		putValue(ACCELERATOR_KEY,
				KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON, loadIcon("images/openworkspace.png"));
		putValue(NAME, "Open workspace");
		putValue(SHORT_DESCRIPTION, "Open workspace");
	}

	public void actionPerformed(ActionEvent arg0) {
		JFileChooser jfc = new JFileChooser();
		jfc.setFileFilter(new WorkspaceFileFilter());
		Workspace w = (Workspace) AppCore.getInstance().getWorkspaceTree().getModel().getRoot();
		
		for (int i = 0; i < w.getChildCount(); i++) {
			if (((Project) w.getChildAt(i)).isChanged()) {
				int result = JOptionPane.showConfirmDialog(AppCore.getInstance(),"Do you want to save workspace?");
				if (result == JOptionPane.YES_OPTION) {
					AppCore.getInstance().getActionManager()
							.getSaveWorkspaceAction().actionPerformed(null);
				}else{
					JOptionPane.showMessageDialog(AppCore.getInstance(),"You will lost unsaved data");
				}
				break;
			}
		}
		
		if (jfc.showOpenDialog(AppCore.getInstance()) == JFileChooser.APPROVE_OPTION) {
			w.reset();
			try {
				//WorkspaceTree workspaceTree = AppCore.getInstance().getWorkspaceTree();
				//workspaceTree.setModel(new WorkspaceModel());
				AppCore.getInstance().getDesktop().repaint();

				ObjectInputStream os = new ObjectInputStream(new FileInputStream(jfc.getSelectedFile()));
				int projectCount;
				projectCount = (Integer) os.readObject();
				//System.out.println(projectCount + " ");
				for (int ii = 0; ii < projectCount; ii++) {
					File f = (File) os.readObject();
					Project p;
					if (f != null) {

						//System.out.println("Kuraaaaa");
						ObjectInputStream os1 = new ObjectInputStream(new FileInputStream(f));
						p = (Project) os1.readObject();
						//System.out.println(p.toString());
						AppCore.getInstance().getWorkspaceTree().addOpenedProject(p, p.getName());
						for (int i=0;i<p.getDiagramCount();i++){
						    DiagramView view=new DiagramView();
						    p.getDiagram(i).getDiagramModel().addUpdateListener(p);
						    p.getDiagram(i).setID();
						    view.setDiagram(p.getDiagram(i));
						    view.setVisible(false);
						    AppCore.getInstance().getDesktop().add(view);
						 }
						os1.close();
					}
				}
				os.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}

		}
		AppCore.getInstance().getWorkspaceTree().expandRow(0);

	}

}
