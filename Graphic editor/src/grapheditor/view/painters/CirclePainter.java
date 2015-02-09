package grapheditor.view.painters;

import grapheditor.model.elements.CircleElement;
import grapheditor.model.elements.DiagramElement;

import java.awt.geom.Ellipse2D;

@SuppressWarnings("serial")
public class CirclePainter extends DevicePainter{

	public CirclePainter(DiagramElement device) {
		super(device);
		CircleElement circle = (CircleElement) device;
		shape = new Ellipse2D.Double(0, 0, circle.getSize().getHeight(), circle
				.getSize().getHeight());

	}

}
