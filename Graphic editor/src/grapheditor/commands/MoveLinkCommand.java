package grapheditor.commands;

import grapheditor.view.DiagramView;

import java.awt.geom.Point2D;

public class MoveLinkCommand implements AbstractCommandInterface{
	private DiagramView diagramView;
	private Point2D breakPoint;
	private double deltaX;
	private double deltaY;
	private boolean firstTime;

	public MoveLinkCommand(DiagramView diagramView, Point2D breakPoint, double x, double y) {
		this.firstTime = true;
		this.diagramView = diagramView;
		this.breakPoint = breakPoint;
		this.deltaX = x;
		this.deltaY = y;
	}

	public void doCommand() {
		if (this.firstTime) {
			this.firstTime = false;
		} else {
			Point2D newPosition = (Point2D) this.breakPoint.clone();
			newPosition.setLocation(newPosition.getX() + this.deltaX, newPosition.getY() + this.deltaY);
			this.breakPoint.setLocation(newPosition);
			this.diagramView.updatePerformed(null);
		}
	}

	public void undoCommand() {

		Point2D newPosition = (Point2D) this.breakPoint.clone();
		newPosition.setLocation(newPosition.getX() - this.deltaX, newPosition.getY() - this.deltaY);
		this.breakPoint.setLocation(newPosition);
		this.diagramView.updatePerformed(null);
	}
}
