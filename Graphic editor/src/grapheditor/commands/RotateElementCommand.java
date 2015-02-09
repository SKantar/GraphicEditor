package grapheditor.commands;

import grapheditor.model.elements.DiagramDevice;
import grapheditor.model.elements.DiagramElement;
import grapheditor.view.DiagramView;

import java.util.ArrayList;

public class RotateElementCommand implements AbstractCommandInterface{
	private DiagramView diagramView;
	private ArrayList<DiagramElement> rotatedElements;
	private boolean direction; //true = RIGHT
	
	@SuppressWarnings("unchecked")
	public RotateElementCommand(DiagramView diagramView , boolean direction) {
		this.direction = direction;
		this.diagramView = diagramView;
		this.rotatedElements = ((ArrayList<DiagramElement>)diagramView.getDiagram().getSelectionModel().getSelected().clone());
	}

	@Override
	public void doCommand() {
		DiagramDevice device;
		for(DiagramElement element : rotatedElements){
			if(element instanceof DiagramDevice){
				device = (DiagramDevice) element;
				if(direction)
					device.setRotation(device.getRotation() + Math.PI/2);
				else
					device.setRotation(device.getRotation() - Math.PI/2);
			//	System.out.println(device.getRotation());
			}
			diagramView.getDiagram().getDiagramModel().fireUpdatePerformed(element.getRepaintBounds());
		}
		
	}

	@Override
	public void undoCommand() {
		DiagramDevice device;
		for(DiagramElement element : rotatedElements){
			if(element instanceof DiagramDevice){
				device = (DiagramDevice) element;
				if(direction)
					device.setRotation(device.getRotation() - Math.PI/2);
				else
					device.setRotation(device.getRotation() + Math.PI/2);
			}
			diagramView.getDiagram().getDiagramModel().fireUpdatePerformed(element.getRepaintBounds());
		}
		
	}
	
	
}
