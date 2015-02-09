package grapheditor.model.elements;

import grapheditor.app.AppCore;
import grapheditor.view.painters.StarPainter;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.Point2D;

@SuppressWarnings("serial")
public class StarElement extends DiagramDevice {

	public StarElement(Point2D position, Dimension size, Stroke stroke,
			Paint paint, Color strokeColor) {
		super(position, size, stroke, paint, strokeColor, AppCore.getInstance().getPalette().getNumberOfInputs(), 1);
		elementPainter = new StarPainter(this);
	}
	
	public StarElement(StarElement star){
		super(star);
		setName("kopija");
		//elementPainter=new StarPainter(this);
	}

	public static DiagramDevice createDefault(Point2D pos, int elemNo) {
		Point2D position = (Point2D) pos.clone();

		Paint fill = Color.WHITE;
		StarElement starElement = new StarElement(position, new Dimension(62,
				66), new BasicStroke((float) (2), BasicStroke.CAP_SQUARE,
				BasicStroke.JOIN_BEVEL), fill, Color.BLACK);
		starElement.setName("Star" + elemNo);
		return starElement;
	}
	
	
	public static DiagramDevice createDefaultDashed (Point2D pos, int elemNo){
		Point2D position = (Point2D) pos.clone();

		Paint fill = Color.WHITE;
		StarElement starElement = new StarElement(position, new Dimension(62,
				66), new BasicStroke((float)1, BasicStroke.CAP_SQUARE, 
						BasicStroke.JOIN_BEVEL, 1f, new float[]{3, 6}, 0 ), fill, Color.BLACK);
		starElement.setName("Star" + elemNo);
		return starElement;
	}

	@Override
	public DiagramElement clone() {
		// TODO Auto-generated method stub
		return new StarElement(this);
	}
}
