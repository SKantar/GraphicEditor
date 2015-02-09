package grapheditor.commands;

import grapheditor.model.DiagramModel;
import grapheditor.model.DiagramSelectionModel;
import grapheditor.model.elements.CircleElement;
import grapheditor.model.elements.DiagramElement;
import grapheditor.model.elements.LinkElement;
import grapheditor.model.elements.RectangleElement;
import grapheditor.model.elements.StarElement;
import grapheditor.model.elements.TriangleElement;
import grapheditor.model.workspace.nodes.Diagram;
import grapheditor.model.workspace.nodes.ElementFolder;
import grapheditor.view.DeviceType;

import java.awt.geom.Point2D;


public class AddDeviceCommand implements AbstractCommandInterface{
	private DiagramModel model;
	private DiagramSelectionModel selectionModel;
	private Point2D position;
	private DiagramElement device = null;
	private DeviceType stateType;
	private Diagram diagram;
	
	
	public AddDeviceCommand(Diagram diagram, Point2D position, DeviceType stateType) {
			this.diagram = diagram;
			this.model = diagram.getDiagramModel();
			this.position = position;
			this.stateType = stateType;
			this.selectionModel = diagram.getSelectionModel();
	}

	@Override
	public void doCommand() {
		int elementNumber = model.getCount();
		if(device == null){
			if(stateType == DeviceType.CIRCLE){
				device = CircleElement.createDefault(position, elementNumber);
				
				while(!model.isAvelableName(device.getName())){
					device.setName("Star " + model.getCount() );
				}
			} else if(stateType == DeviceType.TRIANGLE){
				device = TriangleElement.createDefault(position, elementNumber);
				
				while(!model.isAvelableName(device.getName())){
					device.setName("Star " + model.getCount() );
				}
				
			} else if(stateType == DeviceType.RECTANGLE){
				device = RectangleElement.createDefault(position, elementNumber);
				
				while(!model.isAvelableName(device.getName())){
					device.setName("Star " + model.getCount() );
				}
				
			} else if(stateType == DeviceType.STAR){
				device = StarElement.createDefault(position, elementNumber);
				
				while(!model.isAvelableName(device.getName())){
					device.setName("Star " + model.getCount() );
				}
			}
		}
		
		makeElementNode(device,diagram);
		model.addDiagramElement(device);
		selectionModel.removeAllFromSelectionList();
		selectionModel.addToSelectionList(device);
		selectionModel.updateJTree();
		
	}

	public void undoCommand() {
		selectionModel.removeAllFromSelectionList();
		model.removeFromJTree(device);
		model.removeDiagramElement(device);
		selectionModel.updateJTree();
		
	}
	
	
	public static void makeElementNode(DiagramElement element, Diagram diagram){		
		ElementFolder elementFolder = null;
		String elementType = "rectangles";
		if(element instanceof RectangleElement) elementType = "rectangles";
		if(element instanceof TriangleElement) elementType = "triangles";
		if(element instanceof CircleElement) elementType = "circles";
		if(element instanceof StarElement) elementType = "stars";
		if(element instanceof LinkElement) elementType = "links";
		int i ;
		for(i = 0; i < diagram.getChildCount(); i++)
			if(diagram.getChildAt(i).toString().equals(elementType)){
				elementFolder = (ElementFolder)diagram.getChildAt(i);
				elementFolder.add(element);
				break;
			}
		
		if(i!= diagram.getChildCount()) return;
		
		if(elementType.equals("rectangles")){
			diagram.setHasRectangles(true);
			elementFolder = new ElementFolder("rectangles", diagram.getDiagramModel());
			diagram.insert(elementFolder, 0);
		} else if(elementType.equals("triangles")){
			elementFolder = new ElementFolder("triangles", diagram.getDiagramModel());
			if(diagram.isHasRectangles()) diagram.insert(elementFolder, 1);
			else diagram.insert(elementFolder, 0);
			diagram.setHasTriangles(true);
		} else if(elementType.equals("circles")){
			elementFolder = new ElementFolder("circles", diagram.getDiagramModel());
			if(diagram.isHasRectangles() && diagram.isHasTriangles()) diagram.insert(elementFolder, 2);
			else if(diagram.isHasRectangles() && !diagram.isHasTriangles()) diagram.insert(elementFolder, 1);
			else if(!diagram.isHasRectangles() && diagram.isHasTriangles()) diagram.insert(elementFolder, 1);
			else diagram.insert(elementFolder, 0);
			diagram.setHasCircles(true);
		} else if(elementType.equals("stars")){
			int number = diagram.getChildCount();
			elementFolder = new ElementFolder("stars", diagram.getDiagramModel());
			if(diagram.isHasLinks()) number--;
			diagram.insert(elementFolder, number);
		}
		elementFolder.add(element);
		
	}
	

}
