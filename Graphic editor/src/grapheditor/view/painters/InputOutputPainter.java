package grapheditor.view.painters;

import grapheditor.model.elements.DiagramElement;
import grapheditor.model.elements.InputOutputElement;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;

/**
 * Konkretan painter je zadužen za definisanje Shape objekta koji predstavlja dati element
 * @author Igor Z.
 *
 */
@SuppressWarnings("serial")
public class InputOutputPainter extends DevicePainter{

	public InputOutputPainter(InputOutputElement io) {
		super(io);

		shape=new GeneralPath();
		
		if (io.getType()==InputOutputElement.INPUT){
 	           	((GeneralPath)shape).moveTo(0,0);
                ((GeneralPath)shape).lineTo(0-5,0);
		}else if (io.getType()==InputOutputElement.OUTPUT){
			    ((GeneralPath)shape).moveTo(0,0);
                ((GeneralPath)shape).lineTo(0+5,0);
        }
	}
	public void paint(Graphics2D g, DiagramElement element){
		AffineTransform oldTranform=g.getTransform();
		//uzimamo device kome painter pripada
		InputOutputElement io=(InputOutputElement) element;
		g.translate(io.getPosition().getX(), io.getPosition().getY());
		g.rotate(io.getDevice().getRotation());
		g.scale(io.getDevice().getScale(), io.getDevice().getScale());
		g.setPaint(element.getStrokeColor());
		g.setStroke(element.getStroke());
		g.draw(getShape());

		g.setPaint(element.getPaint());
		g.fill(getShape());

		g.setTransform(oldTranform);
	}
}
