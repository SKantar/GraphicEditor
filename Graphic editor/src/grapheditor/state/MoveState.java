package grapheditor.state;

import grapheditor.commands.MoveDeviceCommand;
import grapheditor.commands.MoveLinkCommand;
import grapheditor.model.elements.DiagramDevice;
import grapheditor.model.elements.DiagramElement;
import grapheditor.model.elements.LinkElement;
import grapheditor.utils.Utils.Direction;
import grapheditor.view.AutoScrollThread;
import grapheditor.view.DiagramView;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.Iterator;

@SuppressWarnings("serial")
public class MoveState extends State{
	private DiagramView diagramView; 
	private double x=0;
	private double y=0;
	private Point2D selectedBP;
	private LinkElement linkElement;
	private MouseEvent e;
	
	
	public MoveState(DiagramView diagramView) {
		this.diagramView = diagramView;
		this.selectedBP = null;
		this.linkElement = null;
		thread = new AutoScrollThread(diagramView);
		thread.start();
	}

	public void mouseDragged(MouseEvent e) {
		this.e = e;
		if (e.getPoint().getX() <= 10) {
			thread.setScroll(false);
			thread.setDirection(Direction.Left);
			thread.setScroll(true);
		} else if (e.getPoint().getY() >= diagramView.getFramework().getSize()
				.getHeight() - 10) {
			thread.setScroll(false);
			thread.setDirection(Direction.Up);
			thread.setScroll(true);
		} else if (e.getPoint().getX() >= diagramView.getFramework().getSize()
				.getWidth() - 10) {
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
	
		
		diagramView.getFramework().setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
		Point2D lastPosition = e.getPoint();
		diagramView.transformToUserSpace(lastPosition);

		double xx=lastPosition.getX()-diagramView.getLastPosition().getX(); 
		double yy=lastPosition.getY()-diagramView.getLastPosition().getY();
		if(diagramView.getDiagram().getSelectionModel().getSelectionListSize() == 1){
			if(diagramView.getDiagram().getSelectionModel().getElementFromSelectionListAt(0) instanceof LinkElement){
				
				if(selectedBP == null){
					linkElement = (LinkElement) diagramView.getDiagram().getSelectionModel().getElementFromSelectionListAt(0);
					//System.out.println(link.getName());
					Iterator<Point2D> it = linkElement.getPointsIterator();
					while (it.hasNext()) {
						Point2D temp = it.next();
						if (Point2D.distance(lastPosition.getX(), lastPosition.getY(), temp.getX(), temp.getY()) <= 4.0) {
							selectedBP = temp;
							break;
						}
					}
				}else{
					Point2D newPosition = (Point2D) selectedBP;
					newPosition.setLocation(newPosition.getX() + xx, newPosition.getY() + yy);
					selectedBP.setLocation(newPosition);
					linkElement.solveMinMaxTranslate();
				}
				
			}
		}
		if(selectedBP == null){
		//pomeranje elementa:
		Iterator<DiagramElement> it = diagramView.getDiagram().getSelectionModel().getSelectionListIterator();
			while(it.hasNext()){
				DiagramElement element =  it.next();
				if (element instanceof DiagramDevice ){				
					DiagramDevice device=(DiagramDevice) element;
					Point2D newPosition = (Point2D)device.getPosition().clone();
					newPosition.setLocation(newPosition.getX() + xx,newPosition.getY() + yy); 
					device.setPosition(newPosition);
				}
				if (element instanceof LinkElement) {
					LinkElement link = (LinkElement) element;
					Iterator<Point2D> itl = link.getPointsIterator();
					while (itl.hasNext()) {
						Point2D tren = itl.next();
						tren.setLocation(tren.getX() + xx, tren.getY() + yy);
					}
				}
			}
		}
		x=x+xx;
		y=y+yy;		
		diagramView.setLastPosition(lastPosition);
		diagramView.updatePerformed(null);
	}
	
	public void mouseReleased(MouseEvent e){
		if(selectedBP == null)
			diagramView.getCommandManager().addCommand(new MoveDeviceCommand(diagramView.getDiagram().getDiagramModel(),diagramView.getDiagram().getSelectionModel(),x,y));
		else
			diagramView.getCommandManager().addCommand(new MoveLinkCommand(diagramView, selectedBP, x, y));
		selectedBP = null;
		diagramView.getFramework().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		x=0;y=0;
		Point2D lastPosition = e.getPoint();
		diagramView.transformToUserSpace(lastPosition);
		diagramView.setLastPosition(lastPosition);
		thread.setScroll(false);
		diagramView.startSelectState();
	}
	
	public MouseEvent getE() {
		return e;
	}
}
