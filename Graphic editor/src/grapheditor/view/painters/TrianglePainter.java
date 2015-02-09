package grapheditor.view.painters;

import grapheditor.model.elements.DiagramElement;
import grapheditor.model.elements.TriangleElement;

import java.awt.geom.GeneralPath;

@SuppressWarnings("serial")
public class TrianglePainter extends DevicePainter{

	public TrianglePainter(DiagramElement device) {
		super(device);
		TriangleElement triangle = (TriangleElement) device;

		double x = triangle.getSize().getWidth() / 2;
		double y = triangle.getSize().getHeight() / 2;

		double dx = triangle.getSize().getWidth() / 2;
		double dy = triangle.getSize().getHeight() / 2;

		shape = new GeneralPath();
		((GeneralPath) shape).moveTo(x, y - dy);

		((GeneralPath) shape).lineTo(x - dx, y + dy);

		((GeneralPath) shape).lineTo(x + dx, y + dy);

		((GeneralPath) shape).lineTo(x, y - dy);

		((GeneralPath) shape).closePath();

	}

}
