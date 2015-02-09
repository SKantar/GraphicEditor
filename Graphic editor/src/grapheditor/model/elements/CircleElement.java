package grapheditor.model.elements;

import grapheditor.app.AppCore;
import grapheditor.view.painters.CirclePainter;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.Point2D;

@SuppressWarnings("serial")
public class CircleElement extends DiagramDevice {

	public CircleElement(Point2D position, Dimension size, Stroke stroke, Paint paint, Color strokeColor) {
		super(position, size, stroke, paint, strokeColor, AppCore.getInstance().getPalette().getNumberOfInputs(), 1);
		elementPainter = new CirclePainter(this);
	}

	public static DiagramDevice createDefault(Point2D pos, int elemNo) {
		
		Point2D position = (Point2D) pos.clone();
		Paint fill = Color.WHITE;
		CircleElement circleElement = new CircleElement(position, new Dimension(50, 50),
											new BasicStroke((float) (2), BasicStroke.CAP_SQUARE,
											BasicStroke.JOIN_BEVEL), fill, Color.BLACK);
		circleElement.setName("Circle " + elemNo);
		return circleElement;
	}
	
	
	public CircleElement(CircleElement circle){
		super(circle);
		setName(circle.getName()+"-");
		//elementPainter = new El
		//elementPainter=new CirclePainter(this);
	}
	
	public static DiagramDevice createDefaultDashed (Point2D pos, int elemNo){
		Point2D position = (Point2D) pos.clone();
		Paint fill = Color.WHITE;
		CircleElement circleElement = new CircleElement(position, new Dimension(50, 50),
				new BasicStroke((float)1, BasicStroke.CAP_SQUARE, 
						BasicStroke.JOIN_BEVEL, 1f, new float[]{3, 6}, 0 ), fill, Color.BLACK);
		circleElement.setName("Circle " + elemNo);
		return circleElement;
	}

	@Override
	public DiagramElement clone() {
		// TODO Auto-generated method stub
		return new CircleElement(this);
	}

}
