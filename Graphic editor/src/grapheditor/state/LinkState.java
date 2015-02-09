package grapheditor.state;

import grapheditor.commands.AddLinkCommand;
import grapheditor.model.elements.DiagramDevice;
import grapheditor.model.elements.DiagramElement;
import grapheditor.model.elements.InputOutputElement;
import grapheditor.model.elements.LinkElement;
import grapheditor.utils.Utils.Direction;
import grapheditor.view.AutoScrollThread;
import grapheditor.view.DiagramView;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

@SuppressWarnings("serial")
public class LinkState extends State {
	private DiagramView diagramView;
	private DiagramElement link;
	private boolean firstPoints;

	public LinkState(DiagramView diagramView) {
		this.diagramView = diagramView;
		thread = new AutoScrollThread(diagramView);
		thread.start();
	}

	public void mousePressed(MouseEvent e) {
		Point position = e.getPoint();
		diagramView.transformToUserSpace(position);
		if (e.getButton() == MouseEvent.BUTTON1) {
			if (link == null) {
				int devicePos = diagramView.getDiagram().getDiagramModel().getElementAtPosition(position);
				if (devicePos != -1) {
					DiagramElement diagramElement = diagramView.getDiagram().getDiagramModel().getElementAt(devicePos);
					if(diagramElement instanceof LinkElement) return;
					DiagramDevice startDevice = (DiagramDevice) diagramElement;
					if (startDevice.hasFreeOutput()) {
						InputOutputElement ouput = startDevice.getFreeOutput();
						link = (LinkElement) LinkElement.createDefault(startDevice, ouput, diagramView.getDiagram().getDiagramModel().getElementCount());
						diagramView.getDiagram().getDiagramModel().addDiagramElement((LinkElement) link);
						firstPoints = false;
					}
				}
			}

		} else if (e.getButton() == MouseEvent.BUTTON3) {
			// pritisnut desni taster misa, ako je u toku iscrtavanje linka
			// treba prekinuti iscrtavanje i obrisati link
			if (link != null) {
				diagramView.getDiagram().getDiagramModel().removeDiagramElement(link);
				link = null;
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
		Point position = e.getPoint();
		diagramView.transformToUserSpace(position);
		if (link == null) return;

		// u toku je iscrtavajne linka

		if (e.getButton() == MouseEvent.BUTTON1) {// otpusten levi taster misa
			int devicePos = diagramView.getDiagram().getDiagramModel().getElementAtPosition(position);
			if (devicePos != -1) {
				DiagramElement diagramElement = diagramView.getDiagram().getDiagramModel().getElementAt(devicePos);
				if(diagramElement instanceof LinkElement) return;
				DiagramDevice endDevice = (DiagramDevice) diagramView.getDiagram().getDiagramModel().getElementAt(devicePos);
				if (endDevice.hasFreeInput() && endDevice != ((LinkElement) link).getStartDevice()) {
					InputOutputElement input = endDevice.getFreeInput();
					((LinkElement) link).setInput(input);
					((LinkElement) link).setEndDevice(endDevice);
					((LinkElement) link).removeLastPoint();
					((LinkElement) link).setPainter(link);
					diagramView.getDiagram().getDiagramModel().fireUpdatePerformed(link.getRepaintBounds());
					diagramView.getCommandManager().addCommand(new AddLinkCommand(diagramView.getDiagram(), (LinkElement) link));
					link = null;
				} else {
					diagramView.getDiagram().getDiagramModel().removeDiagramElement(link);
					link = null;
				}

			} else {
				
				((LinkElement) link).addPoint(position);
				firstPoints = true;
				//((LinkElement) link).addPoint(position);
				//System.out.println(((LinkElement)link).getPointsSize());
				
			}

		}
	}

	public void mouseDragged(MouseEvent e) {
		if (e.getPoint().getX() <= 10) {
			thread.setScroll(false);
			thread.setDirection(Direction.Left);
			thread.setScroll(true);
		} else if (e.getPoint().getY() >= diagramView.getFramework().getSize().getHeight() - 10) {
			thread.setScroll(false);
			thread.setDirection(Direction.Up);
			thread.setScroll(true);
		} else if (e.getPoint().getX() >= diagramView.getFramework().getSize().getWidth() - 10) {
			thread.setScroll(false);
			thread.setDirection(Direction.Right);
			thread.setScroll(true);
		} else if (e.getPoint().getY() <= 10) {
			thread.setScroll(false);
			thread.setDirection(Direction.Down);
			thread.setScroll(true);
		} else {
			thread.setScroll(false);
		}
		Point mousePoint = e.getPoint();
		diagramView.transformToUserSpace(mousePoint);
		if (link!=null){
			

			/*
			 * Ukoliko se kursor nalazi iznad nekog diagram elementa != linka	
			 */
			
			int devicePos = diagramView.getDiagram().getDiagramModel().getElementAtPosition(mousePoint);
			if (devicePos != -1) {
				DiagramElement diagramElement = diagramView.getDiagram().getDiagramModel().getElementAt(devicePos);
				if(diagramElement instanceof DiagramDevice){
					DiagramDevice endDevice = (DiagramDevice) diagramView.getDiagram().getDiagramModel().getElementAt(devicePos);
					if (endDevice.hasFreeInput() && endDevice != ((LinkElement) link).getStartDevice()) {
						InputOutputElement input = endDevice.getFreeInput();
						mousePoint.setLocation(input.getPosition().getX()-5, input.getPosition().getY());
						((LinkElement) link).solveMinMaxCurr(mousePoint);
					}
				}
			}
			Point2D newPoint;
			if(!firstPoints){
				Point2D thirdLast = ((LinkElement) link).getThirdLastPoint();
				newPoint = newPoint(thirdLast, mousePoint );
			}else{
				Point2D fourthLast = ((LinkElement) link).getPointAt(((LinkElement)link).getPointsSize()-4);
				Point2D thirdLast = ((LinkElement) link).getThirdLastPoint();
				newPoint = newPointAgain(fourthLast, thirdLast, mousePoint);
			}
			((LinkElement) link).getSecondLastPoint().setLocation(newPoint);
			((LinkElement) link).solveMinMaxCurr(newPoint);
			// 	povlačenje linka, ažuriranje lokacije poslednje tačke
			//((LinkElement)link).getSecondLastPoint().setLocation();
			((LinkElement) link).getLastPoint().setLocation(mousePoint);
			((LinkElement) link).solveMinMaxCurr(mousePoint);
			
			
			((LinkElement) link).setPainter(link);
			diagramView.getDiagram().getDiagramModel().fireUpdatePerformed(link.getRepaintBounds());
		}
	}

	public void mouseMoved(MouseEvent e) {
		if (e.getPoint().getX() <= 10) {
			thread.setScroll(false);
			thread.setDirection(Direction.Left);
			thread.setScroll(true);
		} else if (e.getPoint().getY() >= diagramView.getFramework().getSize().getHeight() - 10) {
			thread.setScroll(false);
			thread.setDirection(Direction.Up);
			thread.setScroll(true);
		} else if (e.getPoint().getX() >= diagramView.getFramework().getSize().getWidth() - 10) {
			thread.setScroll(false);
			thread.setDirection(Direction.Right);
			thread.setScroll(true);
		} else if (e.getPoint().getY() <= 10) {
			thread.setScroll(false);
			thread.setDirection(Direction.Down);
			thread.setScroll(true);
		} else {
			thread.setScroll(false);
		}
		Point mousePoint = e.getPoint();
		diagramView.transformToUserSpace(mousePoint);
		if (link!=null){
		/*
		 * Ukoliko se kursor nalazi iznad nekog diagram elementa != linka	
		 */
			int devicePos = diagramView.getDiagram().getDiagramModel().getElementAtPosition(mousePoint);
			if (devicePos != -1) {
				DiagramElement diagramElement = diagramView.getDiagram().getDiagramModel().getElementAt(devicePos);
				if(diagramElement instanceof DiagramDevice){
					DiagramDevice endDevice = (DiagramDevice) diagramView.getDiagram().getDiagramModel().getElementAt(devicePos);
					if (endDevice.hasFreeInput() && endDevice != ((LinkElement) link).getStartDevice()) {
						InputOutputElement input = endDevice.getFreeInput();
						mousePoint.setLocation(input.getPosition().getX()-5, input.getPosition().getY());
						((LinkElement) link).solveMinMaxCurr(mousePoint);
					}
				}
			}
			Point2D newPoint;
			if(!firstPoints){
				Point2D thirdLast = ((LinkElement) link).getThirdLastPoint();
				newPoint = newPoint(thirdLast, mousePoint );
			}else{
				Point2D fourthLast = ((LinkElement) link).getPointAt(((LinkElement)link).getPointsSize()-4);
				Point2D thirdLast = ((LinkElement) link).getThirdLastPoint();
				newPoint = newPointAgain(fourthLast, thirdLast, mousePoint);
			}
			((LinkElement) link).getSecondLastPoint().setLocation(newPoint);
			((LinkElement) link).solveMinMaxCurr(newPoint);
			// 	povlačenje linka, ažuriranje lokacije poslednje tačke
			//((LinkElement)link).getSecondLastPoint().setLocation();
			((LinkElement) link).getLastPoint().setLocation(mousePoint);
			((LinkElement) link).solveMinMaxCurr(mousePoint);
			
			
			((LinkElement) link).setPainter(link);

			diagramView.getDiagram().getDiagramModel().fireUpdatePerformed(link.getRepaintBounds());
		}
	}
	
	
	private Point2D newPoint(Point2D secondLast, Point2D last){
		int newX;
		int newY;
		int width = (int) Math.abs(secondLast.getX() - last.getX());
		int height = (int) Math.abs(secondLast.getY() - last.getY());
		if(width < height){
				newY = (int) secondLast.getY();
				newX = (int) last.getX();
		}else{
				newY = (int) last.getY();
				newX = (int) secondLast.getX();
		}

		Point a = new Point(newX, newY);
		//System.out.println("POZIVA SE NEWPOINT");
		return (Point2D)a;
	}
	
	//treca i cetvrta od pozadi
	
	private Point2D newPointAgain(Point2D fourthLast, Point2D thirdLast, Point2D last){
		boolean horizontal = false;
		int newX;
		int newY;
		
		if(fourthLast.getX() == thirdLast.getX()) horizontal = false;
		else horizontal = true;
		
		if(horizontal){
			newX = (int) thirdLast.getX();
			newY = (int) last.getY();
		}else{
			newY = (int) thirdLast.getY();
			newX = (int) last.getX();
		}
		//System.out.println("POZIVA SE NEWPOINT AGAIN");
		Point a = new Point(newX, newY);
		return (Point2D)a;
	}
	
	@Override
	public void exitState(){
		if(link!=null){
			diagramView.getDiagram().getDiagramModel().removeDiagramElement(link);
			link = null;
		}
	}

}
