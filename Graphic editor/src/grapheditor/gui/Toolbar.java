package grapheditor.gui;

import grapheditor.app.AppCore;

import javax.swing.JToolBar;

/**Klasa Toolbar nasledjuje klasu JToolBar i predtavlja
 * meni aplikacije
 * @author Kantar
 */

@SuppressWarnings("serial")
public class Toolbar extends JToolBar{

	public Toolbar() {
		super();
		add(AppCore.getInstance().getActionManager().getOpenWorkspaceAction());
		add(AppCore.getInstance().getActionManager().getOpenProjectAction());
		addSeparator();
		add(AppCore.getInstance().getActionManager().getNewProjectAction());
		add(AppCore.getInstance().getActionManager().getNewDiagramAction());
		addSeparator();
		add(AppCore.getInstance().getActionManager().getSaveWorkspaceAction());
		add(AppCore.getInstance().getActionManager().getSaveProjectAction());
		addSeparator();
		add(AppCore.getInstance().getActionManager().getCascadeDiagramAction());
		add(AppCore.getInstance().getActionManager().getTileHorizontallyDiagramAction());
		add(AppCore.getInstance().getActionManager().getTileVerticallyDiagramAction());
		add(AppCore.getInstance().getActionManager().getPreviousDiagramAction());
		add(AppCore.getInstance().getActionManager().getNextDiagramAction());
		addSeparator();
		add(AppCore.getInstance().getActionManager().getCloseAllDiagramsAction());
		add(AppCore.getInstance().getActionManager().getCloseDiagramAction());
		add(AppCore.getInstance().getActionManager().getCloseProjectAction());
		addSeparator();
		add(AppCore.getInstance().getActionManager().getZoomInAction());
		add(AppCore.getInstance().getActionManager().getZoomOutAction());
		add(AppCore.getInstance().getActionManager().getZoomBestFitAction());
		add(AppCore.getInstance().getActionManager().getZoomLassoAction());
		addSeparator();
		add(AppCore.getInstance().getActionManager().getEditCopyAction());
		add(AppCore.getInstance().getActionManager().getEditPasteAction());
		add(AppCore.getInstance().getActionManager().getEditCutAction());
		addSeparator();
		add(AppCore.getInstance().getActionManager().getUndoAction());
		add(AppCore.getInstance().getActionManager().getRedoAction());
		addSeparator();
		add(AppCore.getInstance().getActionManager().getRotateElementLeftAction());
		add(AppCore.getInstance().getActionManager().getRotateElementRightAction());
		//addSeparator();
		setFloatable(false);
	}

}
