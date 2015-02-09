package grapheditor.state;

import grapheditor.app.AppCore;
import grapheditor.view.DiagramView;

import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

@SuppressWarnings("serial")
public class LassoZoomState extends State {
	
	protected Rectangle2D rect=new Rectangle2D.Double();
	private DiagramView diagramView; 
	
	
	public LassoZoomState(DiagramView diagramView) {
		this.diagramView = diagramView;
	}
	
	
	public void mouseDragged(MouseEvent e) {
		Point2D position = e.getPoint();
		diagramView.transformToUserSpace(position);
		if (!e.isControlDown()) {
			diagramView.getDiagram().getSelectionModel().removeAllFromSelectionList();
		}

		double width = position.getX() - diagramView.getLastPosition().getX();
		double height = position.getY() - diagramView.getLastPosition().getY();
		if ((width < 0) && (height < 0)) {
			rect.setRect(position.getX(), position.getY(), Math.abs(width),
					Math.abs(height));
		} else if ((width < 0) && (height >= 0)) {
			rect.setRect(position.getX(), diagramView.getLastPosition().getY(),
					Math.abs(width), Math.abs(height));
		} else if ((width > 0) && (height < 0)) {
			rect.setRect(diagramView.getLastPosition().getX(), position.getY(),
					Math.abs(width), Math.abs(height));
		} else {
			rect.setRect(diagramView.getLastPosition().getX(), diagramView.getLastPosition()
					.getY(), Math.abs(width), Math.abs(height));
		}
		
		diagramView.setSelectionRectangle(rect);
		diagramView.repaint();

		AppCore.getInstance().getStatusBar().update_status_bar();

	}

	public void mouseReleased(MouseEvent e) {	
		diagramView.areaZoom(rect.getMinX(), rect.getMaxX(), rect.getMinY(), rect.getMaxY());
		diagramView.setSelectionRectangle(new Rectangle2D.Double(0,0,0,0));
		diagramView.repaint();
		diagramView.getStateManager().setLastState();
	}
}
