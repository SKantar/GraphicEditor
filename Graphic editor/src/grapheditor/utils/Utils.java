package grapheditor.utils;

import grapheditor.app.AppCore;
import grapheditor.model.workspace.nodes.Diagram;
import grapheditor.view.DiagramView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JInternalFrame;

public class Utils {
	
	public enum Direction {
		Up, Down, Left, Right
	}
	
	public static List<DiagramView> getVisibleDiagrams() {
		List<DiagramView> visibleDiagrams = new ArrayList<DiagramView>();
		JInternalFrame[] frames = AppCore.getInstance().getDesktop().getAllFrames();
		for(JInternalFrame frame : frames) {
			if(frame.isVisible())
				visibleDiagrams.add((DiagramView)frame);
		}
		
		return visibleDiagrams;
	}
	
	public static List<DiagramView> getSortedVisibleDiagrams() {
		List<DiagramView> visibleDiagrams = new ArrayList<DiagramView>();
		JInternalFrame[] frames = AppCore.getInstance().getDesktop().getAllFrames();
		DiagramView[] diagramFrames = new DiagramView[frames.length];
		int i = 0;
		for(JInternalFrame frame : frames) {
			diagramFrames[i++] = (DiagramView) frame;
		}
		
		Arrays.sort(diagramFrames);
		
		for(DiagramView diagramFrame : diagramFrames){
			if(diagramFrame.isVisible())
				visibleDiagrams.add((DiagramView) diagramFrame);
		}	
		return visibleDiagrams;
	}
	
	/**
	 * Funkcija vraca Frame u kom se nalazi prosledjeni dijagram
	 * @param diagram
	 * @return JFrame 
	 */
		public static JInternalFrame getFrameByDiagram(Diagram diagram){
			JInternalFrame[] frames = AppCore.getInstance().getDesktop().getAllFrames();
			for(JInternalFrame frame : frames){
				if(frame.getTitle().equals(diagram.toString() + " " + diagram.getParent().toString()))
					if(((DiagramView)frame).getDiagram().getId() == diagram.getId())
							return frame;
			}
			return null;
		}
	
	
	
}
