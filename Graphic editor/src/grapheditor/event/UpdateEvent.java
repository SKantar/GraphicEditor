package grapheditor.event;

import java.awt.Rectangle;
import java.util.EventObject;

@SuppressWarnings("serial")
public class UpdateEvent extends EventObject{

	private Rectangle r;
	
	public UpdateEvent(Object arg0) {
		super(arg0);
	}
	
	public UpdateEvent(Object source, Rectangle r) {
		super(source);
		this.r = r;
	}

	public Rectangle getR() {
		return r;
	}

}
