package grapheditor.commands;

import grapheditor.model.DiagramModel;
import grapheditor.model.DiagramSelectionModel;
import grapheditor.model.elements.DiagramDevice;
import grapheditor.model.elements.DiagramElement;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class ResizeElementsCommand implements AbstractCommandInterface{
	
	DiagramModel model;	
	boolean first;
	//reference na elemente modela koji su pomerani
		ArrayList<Point2D> newElementsPositions=new ArrayList<Point2D>();
		ArrayList<Point2D> oldElementsPositions=new ArrayList<Point2D>();
		ArrayList<Double> newElementsScale = new ArrayList<Double>();
		ArrayList<Double> oldElementsScale = new ArrayList<Double>();
		
		//referenca na selekcioni model dijagrama služi za osvežavanje prikaza view-a
		DiagramSelectionModel tempSelectionModel=new DiagramSelectionModel();
		//reference na elemente modela koji su pomerani
		ArrayList<DiagramElement> movedElements=new ArrayList<DiagramElement>();
	@SuppressWarnings("unchecked")
	public ResizeElementsCommand(DiagramModel model, DiagramSelectionModel gsm, ArrayList<Point2D> oldEP,
								ArrayList<Point2D> newEP, ArrayList<Double> oldES, ArrayList<Double> newES) {
		this.model = model;
		for(int i = 0; i < gsm.getSelectionListSize(); i++){
				this.movedElements.add(gsm.getElementFromSelectionListAt(i));

		}
		this.tempSelectionModel = gsm;
		this.first = true;
		oldElementsPositions = (ArrayList<Point2D>) oldEP.clone();
		newElementsPositions = (ArrayList<Point2D>) newEP.clone();
		oldElementsScale = (ArrayList<Double>) oldES.clone();
		newElementsScale = (ArrayList<Double>) newES.clone();
		/*for(Point2D point : oldElementsPositions){
			System.out.print("( " + point.getX() + "," + point.getY()+ " ) " );
		}
		for(Point2D point : newElementsPositions){
			System.out.print("( " + point.getX() + "," + point.getY()+ " ) " );
		}*/
		//System.out.println(movedElements.size() + " " + oldElementsPositions.size() + " " + newElementsPositions.size());
	}

	@SuppressWarnings("unchecked")
	@Override
	public void doCommand() {
		if(first){
			first = false;
			return;
		}
		tempSelectionModel.addToSelectionList((ArrayList<DiagramElement>) movedElements.clone());
		for(int i = 0; i < newElementsPositions.size(); i++){
			DiagramElement element = tempSelectionModel.getElementFromSelectionListAt(i);
			if (element instanceof DiagramDevice ){
					DiagramDevice device=(DiagramDevice) element;
					device.setPosition(newElementsPositions.get(i));
					device.setScale(newElementsScale.get(i));
			}
		}
		tempSelectionModel.removeAllFromSelectionList();
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void undoCommand() {
		tempSelectionModel.addToSelectionList((ArrayList<DiagramElement>) movedElements.clone());
		for(int i = 0; i < oldElementsPositions.size(); i++){
			DiagramElement element = tempSelectionModel.getElementFromSelectionListAt(i);
			if (element instanceof DiagramDevice ){
					DiagramDevice device=(DiagramDevice) element;
					device.setPosition(oldElementsPositions.get(i));
					device.setScale(oldElementsScale.get(i));
			}
		}
		tempSelectionModel.removeAllFromSelectionList();
	}
	
}
