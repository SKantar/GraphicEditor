package grapheditor.actions;

import grapheditor.app.AppCore;
import grapheditor.files.WorkspaceFileFilter;
import grapheditor.model.workspace.nodes.Project;
import grapheditor.model.workspace.nodes.Workspace;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public class SaveWorkspaceAction extends AbstractGEDAction {

	public SaveWorkspaceAction() {
		/**
		 * Pomoću metode apstraktne klase AbstractAction putValue postavljamo
		 * vrednosti 4 od 8 konstanti Kada povežemo ovaj Action sa bilo kojom
		 * komponentom koja nasleđuje JComponent komponenta će iz ovih
		 * konstanti postaviti svoj Accelerator, Icon, Name i Description
		 */

		putValue(ACCELERATOR_KEY,
				KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK + ActionEvent.SHIFT_MASK));
		putValue(SMALL_ICON, loadIcon("images/saveworkspace.png"));
		putValue(NAME, "Save workspace");
		putValue(SHORT_DESCRIPTION, "Save workspace");
	}

	public void actionPerformed(ActionEvent arg0) {
		JFileChooser jfc = new JFileChooser();
		jfc.setFileFilter(new WorkspaceFileFilter());
		if (jfc.showSaveDialog(AppCore.getInstance()) == JFileChooser.APPROVE_OPTION) {
			File workspaceFile = jfc.getSelectedFile();
			String o = workspaceFile.getPath();
			String n;
			if(!o.contains(".gpw"))
				o+=".gpw";
			
			n = o.substring(0, o.length()-4);
			n.trim();
			
			workspaceFile.delete();
			workspaceFile = new File(o);
			ObjectOutputStream os;
			try {
				Workspace w = (Workspace) AppCore.getInstance().getWorkspaceTree().getModel().getRoot();
				os = new ObjectOutputStream(new FileOutputStream(workspaceFile));
				os.writeObject(w.getChildCount());
				for (int i = 0; i < w.getChildCount(); i++) {
					saveProject((Project) w.getChildAt(i),n);
					os.writeObject(((Project) w.getChildAt(i)).getProjectFile());
				}
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

	}
	
	private void saveProject(Project project, String path){
				String o;
				File dir = new File(path);
				o = path + "\\" + project.getName()+".gpf";
				o.trim();
				File projectFile = new File(o);

		ObjectOutputStream os;
		try {

			if(dir.exists())
				dir.delete();
			dir.mkdir();
			
			if(projectFile.exists())
				projectFile.delete();
			projectFile.createNewFile();
			os = new ObjectOutputStream(new FileOutputStream(projectFile));
			os.writeObject(project);
			project.setProjectFile(projectFile);
			project.setChanged(false);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

}
