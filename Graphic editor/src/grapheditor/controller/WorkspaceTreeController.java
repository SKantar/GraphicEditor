package grapheditor.controller;

import grapheditor.model.elements.DiagramElement;
import grapheditor.model.workspace.nodes.Diagram;
import grapheditor.model.workspace.nodes.ElementFolder;
import grapheditor.utils.Utils;

import java.beans.PropertyVetoException;

import javax.swing.JInternalFrame;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

/**
 * Klasa WorkspaceTreeListener implementira TreeSelectionListener i osluskuje
 * dogadjaje selekcije elemenata stabla aplikacije
 * 
 * @author Kantar
 */

public class WorkspaceTreeController implements TreeSelectionListener {

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		TreePath path = e.getPath();
		
		if (path.getLastPathComponent() instanceof Diagram) {
			Diagram diagram = (Diagram) path.getLastPathComponent();
			if (Utils.getFrameByDiagram(diagram) != null) {

				JInternalFrame frame = Utils.getFrameByDiagram(diagram);
				try {
					frame.setSelected(true);
				} catch (PropertyVetoException e1) {
					e1.printStackTrace();
				}
			
			}
		}else if(path.getLastPathComponent() instanceof DiagramElement){
			DiagramElement diagramElement = (DiagramElement) path.getLastPathComponent();
			ElementFolder elementFolder = (ElementFolder)diagramElement.getParent();
			if(elementFolder != null){
				Diagram diagram = (Diagram)elementFolder.getParent();
				diagram.refreshSelectedElements();
			}
		}
		
	}

}
