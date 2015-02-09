package grapheditor.commands;

import grapheditor.app.AppCore;
import grapheditor.model.elements.DiagramDevice;
import grapheditor.model.elements.DiagramElement;
import grapheditor.model.elements.LinkElement;
import grapheditor.view.DiagramView;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.SwingUtilities;

public class DeleteElementsCommand implements AbstractCommandInterface{
	
	private DiagramView diagramView;
	private ArrayList<DiagramElement> deletedElements;

	@SuppressWarnings("unchecked")
	public DeleteElementsCommand(DiagramView diagramView) {
		this.diagramView = diagramView;
		this.deletedElements = ((ArrayList<DiagramElement>)diagramView.getDiagram().getSelectionModel().getSelected().clone());
		if (!diagramView.getDiagram().getSelectionModel().getSelectionList().isEmpty()){
			
			for (int i = 0; i < diagramView.getDiagram().getDiagramModel().getElementCount(); i++) {
				if (diagramView.getDiagram().getDiagramModel().getElementAt(i) instanceof LinkElement) {
					DiagramElement dd1=((LinkElement)diagramView.getDiagram().getDiagramModel().getElementAt(i)).getStartDevice();
					DiagramElement dd2=((LinkElement)diagramView.getDiagram().getDiagramModel().getElementAt(i)).getEndDevice();
					System.out.println(dd1.getName() + " - " + dd2.getName());
					if (diagramView.getDiagram().getSelectionModel().hasElement(dd1) || diagramView.getDiagram().getSelectionModel().hasElement(dd2)){
						deletedElements.add((DiagramElement)diagramView.getDiagram().getDiagramModel().getElementAt(i));
						//System.out.println(((DiagramElement)diagramView.getDiagram().getDiagramModel().getElementAt(i)).getName());
					}
				}
			}
		}
	}
	
	@Override
	public void doCommand() {
		Iterator<DiagramElement> it = this.deletedElements.iterator();
		while (it.hasNext()) {
			DiagramElement element = (DiagramElement) it.next();
			if (element instanceof LinkElement) {
				LinkElement link = (LinkElement) element;
				link.getInput().setFree(true);
				link.getOutput().setFree(true);
			}
			this.diagramView.getDiagram().getSelectionModel().removeFromSelectionList(element);
			this.diagramView.getDiagram().getDiagramModel().removeFromJTree(element);
			this.diagramView.getDiagram().getDiagramModel().removeDiagramElement(element);
			SwingUtilities.updateComponentTreeUI(AppCore.getInstance().getWorkspaceTree());
		}
	}

	@Override
	public void undoCommand() {
		Iterator<DiagramElement> it = this.deletedElements.iterator();
		while (it.hasNext()) {
			DiagramElement element = (DiagramElement) it.next();
			if (element instanceof LinkElement) {
				LinkElement link = (LinkElement) element;
				link.getInput().setFree(false);
				link.getOutput().setFree(false);
			}
			if(element instanceof LinkElement)
				AddLinkCommand.makeElementNode((LinkElement) element, diagramView.getDiagram());
			else{
				AddDeviceCommand.makeElementNode((DiagramDevice)element, diagramView.getDiagram());
			}
			this.diagramView.getDiagram().getDiagramModel().addDiagramElement(element);
		}
		
	}

}
