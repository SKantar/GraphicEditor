package grapheditor.gui;

import grapheditor.app.AppCore;

import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

/**Klasa Menu nasledjuje klasu JMenuBar i predtavlja
 * meni aplikacije
 * @author Kantar
 */


@SuppressWarnings("serial")
public class Menu extends JMenuBar{

	public Menu() {
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		fileMenu.add(AppCore.getInstance().getActionManager().getOpenWorkspaceAction());
		fileMenu.add(AppCore.getInstance().getActionManager().getOpenProjectAction());
		fileMenu.addSeparator();
		fileMenu.add(AppCore.getInstance().getActionManager().getNewProjectAction());
		fileMenu.add(AppCore.getInstance().getActionManager().getNewDiagramAction());
		fileMenu.addSeparator();
		fileMenu.add(AppCore.getInstance().getActionManager().getSaveWorkspaceAction());
		fileMenu.add(AppCore.getInstance().getActionManager().getSaveProjectAction());
		fileMenu.addSeparator();
		fileMenu.add(AppCore.getInstance().getActionManager().getDeleteAction());
		fileMenu.addSeparator();
		fileMenu.add(AppCore.getInstance().getActionManager().getCloseApplicationAction());
		add(fileMenu);
		

		JMenu editMenu = new JMenu("Edit");
		editMenu.setMnemonic(KeyEvent.VK_E);
		editMenu.add(AppCore.getInstance().getActionManager().getUndoAction());
		editMenu.add(AppCore.getInstance().getActionManager().getRedoAction());
		editMenu.addSeparator();
		editMenu.add(AppCore.getInstance().getActionManager().getEditCutAction());
		editMenu.add(AppCore.getInstance().getActionManager().getEditCopyAction());
		editMenu.add(AppCore.getInstance().getActionManager().getEditPasteAction());
		editMenu.addSeparator();
		editMenu.add(AppCore.getInstance().getActionManager().getRotateElementLeftAction());
		editMenu.add(AppCore.getInstance().getActionManager().getRotateElementRightAction());
		editMenu.addSeparator();
		editMenu.add(AppCore.getInstance().getActionManager().getSelectAllAction());
		editMenu.add(AppCore.getInstance().getActionManager().getSearchElementAction());
		add(editMenu);
		
		JMenu windowMenu = new JMenu("Window");
		windowMenu.setMnemonic(KeyEvent.VK_W);
		windowMenu.add(AppCore.getInstance().getActionManager().getCascadeDiagramAction());
		windowMenu.add(AppCore.getInstance().getActionManager().getTileHorizontallyDiagramAction());
		windowMenu.add(AppCore.getInstance().getActionManager().getTileVerticallyDiagramAction());
		windowMenu.addSeparator();
		windowMenu.add(AppCore.getInstance().getActionManager().getCloseAllDiagramsAction());
		windowMenu.add(AppCore.getInstance().getActionManager().getCloseDiagramAction());
		windowMenu.add(AppCore.getInstance().getActionManager().getCloseProjectAction());
		windowMenu.addSeparator();
		windowMenu.add(AppCore.getInstance().getActionManager().getPreviousDiagramAction());
		windowMenu.add(AppCore.getInstance().getActionManager().getNextDiagramAction());
		add(windowMenu);
		
		JMenu helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_H);
		helpMenu.add(AppCore.getInstance().getActionManager().getAboutAuthorAction());
		helpMenu.add(AppCore.getInstance().getActionManager().getAboutApplicationAction());
		add(helpMenu);
	}
	
}
