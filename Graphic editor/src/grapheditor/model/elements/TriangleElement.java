package grapheditor.model.elements;

import grapheditor.app.AppCore;
import grapheditor.view.painters.TrianglePainter;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.Point2D;

@SuppressWarnings("serial")
public class TriangleElement extends DiagramDevice {

	public TriangleElement(Point2D position, Dimension size, Stroke stroke,
			Paint paint, Color strokeColor) {
		super(position, size, stroke, paint, strokeColor, AppCore.getInstance().getPalette().getNumberOfInputs(), 1);
		elementPainter = new TrianglePainter(this);
	}
	
	public TriangleElement(TriangleElement triangle){
		super(triangle);
		setName("kopija");
		//elementPainter=new TrianglePainter(this);
	}

	public static DiagramDevice createDefault(Point2D pos, int elemNo) {
		Point2D position = (Point2D) pos.clone();

		Paint fill = Color.WHITE;
		TriangleElement triangleElement = new TriangleElement(position,
				new Dimension(58, 50), new BasicStroke((float) (2),
						BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL), fill,
				Color.BLACK);
		triangleElement.setName("Triangle" + elemNo);
		return triangleElement;
	}
	
	public static DiagramDevice createDefaultDashed (Point2D pos, int elemNo){
		Point2D position = (Point2D) pos.clone();

		Paint fill = Color.WHITE;
		TriangleElement triangleElement = new TriangleElement(position,
				new Dimension(58, 50), new BasicStroke((float)1, BasicStroke.CAP_SQUARE, 
						BasicStroke.JOIN_BEVEL, 1f, new float[]{3, 6}, 0 ), fill,
				Color.BLACK);
		triangleElement.setName("Triangle" + elemNo);
		return triangleElement;
	}

	@Override
	public DiagramElement clone() {
		// TODO Auto-generated method stub
		return new TriangleElement(this);
	}
}
