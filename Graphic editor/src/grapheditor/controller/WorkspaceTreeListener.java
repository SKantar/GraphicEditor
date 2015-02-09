package grapheditor.controller;

import grapheditor.app.AppCore;
import grapheditor.gui.PopUpMenu;
import grapheditor.model.elements.DiagramElement;
import grapheditor.model.workspace.nodes.Diagram;
import grapheditor.model.workspace.nodes.Project;
import grapheditor.utils.Utils;
import grapheditor.view.DiagramView;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;

import javax.swing.SwingUtilities;
import javax.swing.tree.TreePath;

/**
 * Klasa WorkspaceTreeListener implementira MouseListener i osluskuje dogadjaje
 * misa na stablu aplikacije
 * 
 * @author Kantar
 */

public class WorkspaceTreeListener extends MouseAdapter {

	TreePath selPath;
	int selRow;

	@Override
	public void mouseClicked(MouseEvent arg0) {
		super.mouseClicked(arg0);
		if (SwingUtilities.isRightMouseButton(arg0)) {
			selRow = AppCore.getInstance().getWorkspaceTree()
					.getRowForLocation(arg0.getX(), arg0.getY());
			selPath = AppCore.getInstance().getWorkspaceTree()
					.getPathForLocation(arg0.getX(), arg0.getY());
			if (selRow != -1) {
				AppCore.getInstance().getWorkspaceTree()
						.setSelectionRow(selRow);
				if (selPath.getLastPathComponent() instanceof Diagram) {
					PopUpMenu.setType(2);
					AppCore.getInstance()
							.getWorkspaceTree()
							.getPopUpMenu()
							.show(arg0.getComponent(), arg0.getX(), arg0.getY());
				}
				if (selPath.getLastPathComponent() instanceof Project) {
					PopUpMenu.setType(1);
					AppCore.getInstance()
							.getWorkspaceTree()
							.getPopUpMenu()
							.show(arg0.getComponent(), arg0.getX(), arg0.getY());

				}
				
				if (selPath.getLastPathComponent() instanceof DiagramElement) {
					PopUpMenu.setType(3);
					AppCore.getInstance()
							.getWorkspaceTree()
							.getPopUpMenu()
							.show(arg0.getComponent(), arg0.getX(), arg0.getY());

				}

			}
		}

			/*selRow = AppCore.getInstance().getWorkspaceTree()
					.getRowForLocation(arg0.getX(), arg0.getY());
			selPath = AppCore.getInstance().getWorkspaceTree()
					.getPathForLocation(arg0.getX(), arg0.getY());

			if (selRow != -1) {

				if (selPath.getLastPathComponent() instanceof DiagramElement) {
					DiagramElement element = (DiagramElement) selPath
							.getLastPathComponent();
					Diagram diagram = (Diagram) element.getParent().getParent();
					if(!arg0.isControlDown())
					diagram.getSelectionModel().removeAllFromSelectionList();

					Iterator<DiagramElement> it = diagram.getDiagramModel()
							.getDeviceIterator();
					while (it.hasNext()) {
						DiagramElement diagramElement = (DiagramElement) it
								.next();
						if (diagramElement.getName().equals(element.getName()))
							diagram.getSelectionModel().addToSelectionList(
									diagramElement);
					}

				}

			}*/

		if (arg0.getClickCount() == 2) {
			selRow = AppCore.getInstance().getWorkspaceTree()
					.getRowForLocation(arg0.getX(), arg0.getY());
			selPath = AppCore.getInstance().getWorkspaceTree()
					.getPathForLocation(arg0.getX(), arg0.getY());

			if (selRow != -1) {

				if (selPath.getLastPathComponent() instanceof Diagram) {

					DiagramView diagramView = (DiagramView) Utils.getFrameByDiagram(((Diagram) selPath.getLastPathComponent()));
					if (!diagramView.isVisible()) {
							diagramView.setVisible(true);

						try {
							diagramView.setSelected(true);
						} catch (PropertyVetoException e1) {
							e1.printStackTrace();
						}

					}
				}
			}
		}
	}

}
