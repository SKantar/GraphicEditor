package grapheditor.model.workspace.nodes;

import java.io.Serializable;

import grapheditor.model.DiagramModel;
import grapheditor.model.DiagramSelectionModel;
import grapheditor.state.State;
import grapheditor.view.DeviceType;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Klasa Diagram modeluje dijagram
 * 
 * @author Kantar
 */

@SuppressWarnings("serial")
public class Diagram extends DefaultMutableTreeNode implements Serializable {

	private static int currentId = 0;
	private String name;
	private int id;
	
	private DiagramModel diagramModel;
	private DiagramSelectionModel selectionModel;
	
	private State currentState;
	private DeviceType deviceType;
	private boolean hasRectangles, hasCircles, hasTriangles, hasStars, hasLinks; 
	
	public Diagram(String name) {
		super();
		this.name = name;
		id = currentId++;
		diagramModel = new DiagramModel(name);
		currentState = new State();
		hasRectangles = false;
		hasCircles = false;
		hasTriangles = false;
		hasStars = false;
		hasLinks = false;
	}

	public String toString() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public DiagramModel getDiagramModel() {
		return diagramModel;
	}

	public State getCurrentState() {
		return currentState;
	}

	public void setCurrentState(State currentState) {
		this.currentState = currentState;
	}

	public DeviceType getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(DeviceType deviceType) {
		this.deviceType = deviceType;
	}

	public boolean isHasRectangles() {
		return hasRectangles;
	}

	public void setHasRectangles(boolean hasRectangles) {
		this.hasRectangles = hasRectangles;
	}

	public boolean isHasCircles() {
		return hasCircles;
	}

	public void setHasCircles(boolean hasCircles) {
		this.hasCircles = hasCircles;
	}

	public boolean isHasTriangles() {
		return hasTriangles;
	}

	public void setHasTriangles(boolean hasTriangles) {
		this.hasTriangles = hasTriangles;
	}

	public boolean isHasStars() {
		return hasStars;
	}

	public void setHasStars(boolean hasStars) {
		this.hasStars = hasStars;
	}

	public boolean isHasLinks() {
		return hasLinks;
	}

	public void setHasLinks(boolean hasLinks) {
		this.hasLinks = hasLinks;
	}
	

	public DiagramSelectionModel getSelectionModel() {
		if(selectionModel == null)
			selectionModel = new DiagramSelectionModel();
		return selectionModel;
	}

	public void refreshSelectedElements(){
		diagramModel.refreshElementsSelection(this);
	}
	
	
	public void setID() {
		id = currentId++;
	}
}