package grapheditor.view.painters;

import grapheditor.model.elements.DiagramElement;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.io.Serializable;


/**
 * ElementPainter je apstraktna klasa koja deklariše metode za iscrtavnje Diagram
 * elementa i detekciju pogotka
 * @author Igor Z.
 *
 */
@SuppressWarnings("serial")
public abstract class ElementPainter implements Serializable {
	
	protected Shape shape;
	/**
	 * Referenca na Element objekat kome painter pripada.
	 */
	public ElementPainter(DiagramElement element) {	}

	public Shape getShape() {
		return shape;
	}

	public abstract void paint(Graphics2D g, DiagramElement element);
	
	public abstract boolean elementAt(DiagramElement element, Point pos);

	
}
