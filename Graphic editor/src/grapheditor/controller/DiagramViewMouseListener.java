package grapheditor.controller;

import grapheditor.view.DiagramView.Framework;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DiagramViewMouseListener extends MouseAdapter{
	
	@Override
	public void mouseExited(MouseEvent e) {
		((Framework)e.getSource()).stop();
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		((Framework)e.getSource()).start();
	}

}
