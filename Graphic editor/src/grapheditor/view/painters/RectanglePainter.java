package grapheditor.view.painters;


import grapheditor.model.elements.DiagramElement;
import grapheditor.model.elements.RectangleElement;

import java.awt.geom.GeneralPath;

/**
 * Konkretan painter je zadužen za definisanje Shape objekta koji predstavlja dati element
 * @author Igor Z.
 *
 */
@SuppressWarnings("serial")
public class RectanglePainter extends DevicePainter{

	public RectanglePainter(DiagramElement device) {
		super(device);
		RectangleElement rectangle = (RectangleElement) device;

		shape = new GeneralPath();
		((GeneralPath)shape).moveTo(0,0);
		
		((GeneralPath)shape).lineTo(0+rectangle.getSize().width,0);
		
		((GeneralPath)shape).lineTo(0+rectangle.getSize().width,0+rectangle.getSize().height);
		
		((GeneralPath)shape).lineTo(0,0+rectangle.getSize().height);
		
		((GeneralPath)shape).closePath();
	}
	

	
}
