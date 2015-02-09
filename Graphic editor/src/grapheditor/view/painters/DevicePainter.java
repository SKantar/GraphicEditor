package grapheditor.view.painters;

import grapheditor.model.elements.DiagramDevice;
import grapheditor.model.elements.DiagramElement;
import grapheditor.model.elements.InputOutputElement;
import grapheditor.model.elements.RectangleElement;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;

/**
 * DevicePainter je zadužen za crtanje uređaja kao i za detekciju pogotka za
 * sta koristi Shape.
 * 
 * @author igor
 * 
 */
@SuppressWarnings("serial")
public class DevicePainter extends ElementPainter {

	public DevicePainter(DiagramElement device) {
		super(device);
	}

	public void paint(Graphics2D g, DiagramElement element) {
			AffineTransform oldTransform = g.getTransform();
		// uzimamo device kome painter pripada
			DiagramDevice device = (DiagramDevice) element;
			Iterator<InputOutputElement> it = device.getInputIterator();
			while (it.hasNext()) {
				InputOutputElement d = (InputOutputElement) it.next();
				d.getPainter().paint(g, d);
			}

			Iterator<InputOutputElement> it2 = device.getOutputIterator();
			while (it2.hasNext()) {
				InputOutputElement d2 = (InputOutputElement) it2.next();
				d2.getPainter().paint(g, d2);
			}

			g.translate(device.getPosition().getX(), device.getPosition().getY());
			g.rotate(device.getRotation(), device.getSize().getWidth() / 2, device
				.getSize().getHeight() / 2);
			g.scale(device.getScale(), device.getScale());

			g.setPaint(element.getStrokeColor());
			g.setStroke(element.getStroke());
			g.draw(getShape());
			
			g.setPaint(element.getPaint());
			if (element.getFillColor() != null)
				g.setColor(element.getFillColor());
			g.fill(getShape());
			//g.rotate(-device.getRotation(), device.getSize().getWidth()/2, device.getSize().getHeight()/2);
			g.setPaint(Color.BLACK);
			Font fnt = new Font("monospaced", Font.BOLD, 9);
			g.setFont(fnt);
			FontMetrics metrics = g.getFontMetrics(fnt);
			
			
			
			g.setTransform(oldTransform);	
			AffineTransform ooldTransform = g.getTransform();
			g.translate(device.getPosition().getX(), device.getPosition().getY());
			g.scale(device.getScale(), device.getScale());
			if(device.isFliped() && (device instanceof RectangleElement))
					g.drawString(element.getName(),
							(int) (device.getInitSize().getHeight() - metrics
									.stringWidth(element.getName()) / 2.0), (int) device
									.getInitSize().getWidth() - 5);
			else{
				g.drawString(element.getName(),
					(int) (device.getInitSize().getWidth() / 2.0 - metrics
							.stringWidth(element.getName()) / 2.0), (int) device
							.getInitSize().getHeight() + 10);
			}
			g.setTransform(ooldTransform);
			//g.draw(device.getRepaintBounds());
	}

	public boolean elementAt(DiagramElement element, Point pos){
		DiagramDevice device = (DiagramDevice) element;
		AffineTransform pom = new AffineTransform();
		pom.translate(device.getPosition().getX(), device.getPosition().getY());
		pom.rotate(device.getRotation(), device.getSize().getWidth() / 2,
				device.getSize().getHeight() / 2);
		pom.scale(device.getScale(), device.getScale());
		Rectangle2D rect = (Rectangle2D) pom.createTransformedShape(
				getShape().getBounds2D()).getBounds2D();
		return rect.contains(pos);
	} 


	public void setShape(Shape shape) {
		this.shape = shape;
	}

}
