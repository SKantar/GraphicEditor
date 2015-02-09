package grapheditor.state;

import grapheditor.view.AutoScrollThread;

import java.awt.event.MouseEvent;
import java.io.Serializable;

@SuppressWarnings("serial")
public class State implements Serializable{

	protected AutoScrollThread thread;
	public void mousePressed(MouseEvent e) {
	
	}

	public void mouseDragged(MouseEvent e) {
	
	}

	public void mouseReleased(MouseEvent e) {
	
	}

	public void mouseMoved(MouseEvent e) {
	
	}
	
	public AutoScrollThread getThread() {
		return thread;
	}

	public void exitState(){
		
	}
}


