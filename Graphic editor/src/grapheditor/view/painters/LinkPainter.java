package grapheditor.view.painters;

import grapheditor.model.elements.DiagramElement;
import grapheditor.model.elements.LinkElement;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.Iterator;

/**
 * Konkretan painter je zadužen za definisanje Shape objekta koji predstavlja dati element
 * @author Igor Z.
 *
 */
@SuppressWarnings("serial")
public class LinkPainter extends ElementPainter{

	public LinkPainter(DiagramElement device) {
		super(device);

	
	}
	
	public void paint(Graphics2D g, DiagramElement element){

		LinkElement link = (LinkElement) element;
		if(link.isFinished())
			paintLink(g, link);
		else 
			paintdashed(g, link);
	}

	@Override
	public boolean elementAt(DiagramElement element, Point pos) {
		LinkElement link = (LinkElement) element;
		if (link.getInput() == null)
			return false;

		Rectangle rect = new Rectangle(0, 0, 0, 0);

		Point2D sbp = link.getOutput().getPosition();
		Point2D dbp = link.getInput().getPosition();

		rect.setRect(sbp.getX() - 2, sbp.getY() - 2, 4, 4);
		if (rect.contains(pos)) {
			return true;
		}
		rect.setRect(dbp.getX() - 2, dbp.getY() - 2, 4, 4);
		if (rect.contains(pos)) {
			return true;
		}

		Iterator<Point2D> it = link.getPointsIterator();
		Point2D last = sbp;
		Point2D bp = null;
		while (it.hasNext()) {
			bp = (Point2D) it.next();
			if (last != null) {
				double xpoints[] = { last.getX() - 4, last.getX() + 4,
						bp.getX() + 4, bp.getX() - 4 };
				double ypoints[] = { last.getY(), last.getY(), bp.getY(),
						bp.getY() };
				Shape p = new GeneralPath();
				((GeneralPath) p).moveTo(xpoints[0], ypoints[0]);
				((GeneralPath) p).lineTo(xpoints[1], ypoints[1]);
				((GeneralPath) p).lineTo(xpoints[2], ypoints[2]);
				((GeneralPath) p).lineTo(xpoints[3], ypoints[3]);
				((GeneralPath) p).lineTo(xpoints[0], ypoints[0]);
				((GeneralPath) p).closePath();
				if (p.contains(pos))
					return true;
				double xpoints1[] = { last.getX(), last.getX(), bp.getX(),
						bp.getX() };
				double ypoints1[] = { last.getY() - 4, last.getY() + 4,
						bp.getY() + 4, bp.getY() - 4 };
				p = new GeneralPath();
				((GeneralPath) p).moveTo(xpoints1[0], ypoints1[0]);
				((GeneralPath) p).lineTo(xpoints1[1], ypoints1[1]);
				((GeneralPath) p).lineTo(xpoints1[2], ypoints1[2]);
				((GeneralPath) p).lineTo(xpoints1[3], ypoints1[3]);
				((GeneralPath) p).lineTo(xpoints1[0], ypoints1[0]);
				((GeneralPath) p).closePath();
				if (p.contains(pos))
					return true;
			}
			rect.setRect(bp.getX() - 2, bp.getY() - 2, 4, 4);
			if (rect.contains(pos)) {
				return true;
			}
			last = bp;
		}
		bp = dbp;
		double xpoints[] = { last.getX() - 4, last.getX() + 4, bp.getX() + 4,
				bp.getX() - 4 };
		double ypoints[] = { last.getY(), last.getY(), bp.getY(), bp.getY() };
		Shape p = new GeneralPath();
		((GeneralPath) p).moveTo(xpoints[0], ypoints[0]);
		((GeneralPath) p).lineTo(xpoints[1], ypoints[1]);
		((GeneralPath) p).lineTo(xpoints[2], ypoints[2]);
		((GeneralPath) p).lineTo(xpoints[3], ypoints[3]);
		((GeneralPath) p).lineTo(xpoints[0], ypoints[0]);
		((GeneralPath) p).closePath();
		if (p.contains(pos))
			return true;
		double xpoints1[] = { last.getX(), last.getX(), bp.getX(), bp.getX() };
		double ypoints1[] = { last.getY() - 4, last.getY() + 4, bp.getY() + 4,
				bp.getY() - 4 };
		p = new GeneralPath();
		((GeneralPath) p).moveTo(xpoints1[0], ypoints1[0]);
		((GeneralPath) p).lineTo(xpoints1[1], ypoints1[1]);
		((GeneralPath) p).lineTo(xpoints1[2], ypoints1[2]);
		((GeneralPath) p).lineTo(xpoints1[3], ypoints1[3]);
		((GeneralPath) p).lineTo(xpoints1[0], ypoints1[0]);
		((GeneralPath) p).closePath();
		if (p.contains(pos))
			return true;
		return false;
	}	
	
	public static Rectangle findRectangle(LinkElement link){
		double minX=0,minY=0,maxX=0,maxY=0;
		minX=link.getOutput().getPosition().getX();
		minY=link.getOutput().getPosition().getY(); 
		maxX=link.getOutput().getPosition().getX()+5; 
		maxY=link.getOutput().getPosition().getY()+5;		
		Iterator<Point2D> it = link.getPointsIterator();
		while(it.hasNext()){
			Point2D  point  =  it.next();
			if(point.getX()<minX)
				minX = point.getX();
			if(point.getX()>maxX)
				maxX = point.getX();
			if(point.getY()<minY)
				minY = point.getY();
			if(point.getY()>maxY)
				maxY = point.getY();
		}
		Point2D  point  =  link.getInput().getPosition();
		if(point.getX()<minX)
			minX = point.getX();
		if(point.getX()>maxX)
			maxX = point.getX();
		if(point.getY()<minY)
			minY = point.getY();
		if(point.getY()>maxY)
			maxY = point.getY();

		
		Rectangle rect=new Rectangle((int)minX-5,(int)minY-5,(int)(maxX-minX+10),(int)(maxY-minY+10));
		return rect;
	}
	
	
	private void paintdashed(Graphics2D g, LinkElement link){
		g.setPaint(link.getStrokeColor());
		Stroke dashed = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{5}, 0);
		//g.setStroke(element.getStroke());
		g.setStroke(dashed);
		Point2D last =(Point2D) link.getOutput().getPosition().clone();
		
		last.setLocation(last.getX(),last.getY());
		 
		Iterator<Point2D> it = link.getPointsIterator();
		while(it.hasNext()){
			Point2D current = (Point2D) it.next();
			g.drawLine((int)last.getX(), (int)last.getY(), (int)current.getX(), (int)current.getY());
			last=current;
		}
		 if (link.getInput()!=null)
			 g.drawLine((int)last.getX(), (int)last.getY(), (int)link.getInput().getPosition().getX(),(int)link.getInput().getPosition().getY());
		// g.draw(link.getRepaintBounds());
	}
	
	
	private void paintLink(Graphics2D g, LinkElement link) {
		g.setPaint(link.getStrokeColor());
		//Stroke dashed = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{5}, 0);
		g.setStroke(link.getStroke());
		Point2D last =(Point2D) link.getOutput().getPosition().clone();
		
		
		last.setLocation(last.getX(),last.getY());
		 
		Iterator<Point2D> it = link.getPointsIterator();
		while(it.hasNext()){
			Point2D current = (Point2D) it.next();
			g.drawLine((int)last.getX(), (int)last.getY(), (int)current.getX(), (int)current.getY());
			last=current;
			g.drawRect((int)last.getX()-2, (int)last.getY()-2, 4, 4);
			
			
		}
		 if (link.getInput()!=null){
				 g.drawLine((int)last.getX(), (int)last.getY(), (int)link.getInput().getPosition().getX(),(int)link.getInput().getPosition().getY());
		 }
		 // g.draw(link.getRepaintBounds());
	}
	

}
