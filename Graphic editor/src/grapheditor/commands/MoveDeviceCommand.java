package grapheditor.commands;

import grapheditor.model.DiagramModel;
import grapheditor.model.DiagramSelectionModel;
import grapheditor.model.elements.DiagramDevice;
import grapheditor.model.elements.DiagramElement;
import grapheditor.model.elements.LinkElement;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;


public class MoveDeviceCommand implements AbstractCommandInterface{
	
	//referenca na model, trenutno se ne koristi
	DiagramModel model;
	
	//reference na elemente modela koji su pomerani
	ArrayList<DiagramElement> movedElements=new ArrayList<DiagramElement>();
	
	//referenca na selekcioni model dijagrama služi za osvežavanje prikaza view-a
	DiagramSelectionModel tempSelectionModel=new DiagramSelectionModel();
	
	//indikator da li je izvršenje akcije, a ne ponavljanje
	//ako je izvršenje presko�?i doCommand() jer je već pomeren u MoveState-u
	boolean firstAction;
	
	
	//pomeraj elementa u jednoj akciji pomeranja
	double deltaX;
	double deltaY;
	
	
	

	public MoveDeviceCommand(DiagramModel model, DiagramSelectionModel gsm,double x,double y) {
		this.model = model;
		for(int i = 0; i < gsm.getSelectionListSize(); i++){
			DiagramElement element =  gsm.getElementFromSelectionListAt(i);
			if (element instanceof DiagramDevice || element instanceof LinkElement){
				movedElements.add(gsm.getElementFromSelectionListAt(i));
			}
		}
		
		this.tempSelectionModel=gsm;
		firstAction=true;
		deltaX=x;
		deltaY=y;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void doCommand() {
		if(firstAction){			
			firstAction=false;
		}
		else{
			tempSelectionModel.addToSelectionList((ArrayList<DiagramElement>) movedElements.clone());
			Iterator<DiagramElement> it = movedElements.iterator();
			while(it.hasNext()){
				DiagramElement element =  it.next();
				if (element instanceof DiagramDevice ){
						DiagramDevice device=(DiagramDevice) element;
						Point2D newPosition = (Point2D)device.getPosition().clone();
						newPosition.setLocation(newPosition.getX() + deltaX,newPosition.getY() + deltaY); 
						device.setPosition(newPosition);
				}
				else if (element instanceof LinkElement) {
					LinkElement link = (LinkElement) element;
					Iterator<Point2D> it1 = link.getPointsIterator();
					while (it1.hasNext()) {
						Point2D tren = it1.next();
						tren.setLocation(tren.getX() + deltaX, tren.getY()+ deltaY);
					}
				}
			}
			tempSelectionModel.removeAllFromSelectionList();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void undoCommand() {
		tempSelectionModel.addToSelectionList((ArrayList<DiagramElement>) movedElements.clone());
		Iterator<DiagramElement> it =movedElements.iterator();
		while(it.hasNext()){
			DiagramElement element =  it.next();
			if (element instanceof DiagramDevice ){
					DiagramDevice device=(DiagramDevice) element;
					Point2D newPosition = (Point2D)device.getPosition().clone();
					newPosition.setLocation(newPosition.getX() - deltaX,newPosition.getY() - deltaY); 
					device.setPosition(newPosition);

			}else if (element instanceof LinkElement) {
				LinkElement link = (LinkElement) element;
				Iterator<Point2D> it1 = link.getPointsIterator();
				while (it1.hasNext()) {
					Point2D tren = it1.next();
					tren.setLocation(tren.getX() - deltaX, tren.getY() - deltaY);
				}
			}
		}
		tempSelectionModel.removeAllFromSelectionList();
	}

}

