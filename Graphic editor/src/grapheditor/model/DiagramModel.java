package grapheditor.model;


import grapheditor.app.AppCore;
import grapheditor.event.UpdateEvent;
import grapheditor.event.UpdateListener;
import grapheditor.model.elements.DiagramDevice;
import grapheditor.model.elements.DiagramElement;
import grapheditor.model.elements.LinkElement;
import grapheditor.model.workspace.nodes.Diagram;
import grapheditor.model.workspace.nodes.ElementFolder;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.SwingUtilities;
import javax.swing.event.EventListenerList;
import javax.swing.tree.TreePath;


/**
 * 
 * U sebi će sadržati kolekciju grafi�?kih elemenata
 * takođe će imati i podršku za tree view komponentu
 * @author ASCOM
 *
 */
@SuppressWarnings("serial")
public class DiagramModel implements Serializable{

	private int count=0;
	private String name;
	
	protected ArrayList<DiagramElement> diagramElements =new ArrayList<DiagramElement>();
	
	transient EventListenerList listenerList = new EventListenerList();
	transient UpdateEvent updateEvent = null;
	
	private Object readResolve() {
		listenerList = new EventListenerList();
		return this;
	}
	 

	public DiagramModel(String name) {
		this.name = name;
	}

	public int getCount() {
		return count++;
	}

	public DiagramElement getElementAt(int i){
		return diagramElements.get(i);
	}

	public void setCount(int count) {
		this.count = count;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	public String toString() {
		return name;
	}	
	public int getElementCount(){
		return diagramElements.size();
	}
	
	public ArrayList<DiagramElement> getDiagramElements() {
		return diagramElements;
	}
	
	public Iterator<DiagramElement> getDeviceIterator(){
		return diagramElements.iterator();
	}
	
	public void addDiagramElement(DiagramElement device){
		//System.out.println("Iz stangle u broj brate moj");
		diagramElements.add(device);
		fireUpdatePerformed(device.getRepaintBounds());
		SwingUtilities.updateComponentTreeUI(AppCore.getInstance().getWorkspaceTree());
		
	}
	
	public void removeDiagramElement(DiagramElement element) {
		diagramElements.remove(element);
		fireUpdatePerformed(element.getRepaintBounds());
		SwingUtilities.updateComponentTreeUI(AppCore.getInstance().getWorkspaceTree());
	}
	
	 public void addUpdateListener(UpdateListener l) {
	     listenerList.add(UpdateListener.class, l);
	 }

	 public void removeUpdateListener(UpdateListener l) {
		 //System.out.println("Ovo je ono kad mi dosla devojka");
	     listenerList.remove(UpdateListener.class, l);
	 }
	 
	 public void removeLink(DiagramElement link){

			diagramElements.remove(link);
			for (int i=0;i<diagramElements.size();i++) if (diagramElements.get(i).toString().equals(link.toString())) diagramElements.remove(i);

			fireUpdatePerformed(link.getRepaintBounds());
			SwingUtilities.updateComponentTreeUI(AppCore.getInstance().getWorkspaceTree());
	}

	 /**
	 * Javljamo svim listenerima da se dogadjaj desio 
	 */

	public void fireUpdatePerformed(Rectangle r) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == UpdateListener.class) {
				updateEvent = new UpdateEvent(this, r);
				((UpdateListener) listeners[i + 1])
						.updatePerformed(updateEvent);
			}
		}
	}
	
	public int getElementAtPosition(Point point) {
		//System.out.println("Sta je sad u usta te jebem");
		for(int i=getElementCount()-1;i>=0;i--){
			DiagramElement device = getElementAt(i);
			if(device.getPainter().elementAt(device, point)){
				return i;
			}
		}
		return -1;
	}
	
	public void deleteLinkForDevice(DiagramDevice device){
		ArrayList<DiagramElement> forRemove = new ArrayList<DiagramElement>();
		for (int i=0; i < getElementCount();i++) 
			if (getElementAt(i) instanceof LinkElement) {
				LinkElement link = (LinkElement) getElementAt(i);
				DiagramDevice startDevice = link.getStartDevice();
				DiagramDevice endDevice = link.getEndDevice();
				
				if(startDevice.equals(device) || endDevice.equals(device)){
						link.setFree();
						forRemove.add(link);
				}
			}
		
		for(DiagramElement element : forRemove){
			removeFromJTree(element);
			removeDiagramElement(element);
		}
	}
	
	public boolean isAvelableName(String name){
		for(DiagramElement element : getDiagramElements()){
			if(element.getName().equals(name))
				return false;
		}
		return true;
	}
	
	
	public void removeFromJTree(DiagramElement element){

		ElementFolder elementFolder = (ElementFolder)element.getParent();
		if(elementFolder == null) return;
		Diagram diagram = (Diagram)elementFolder.getParent();
		
		elementFolder.remove(element);
		
		if(elementFolder.getChildCount() == 0){
				diagram.remove(elementFolder);
				if(elementFolder.toString().equals("rectangles")) diagram.setHasRectangles(false);
				if(elementFolder.toString().equals("triangles")) diagram.setHasTriangles(false);
				if(elementFolder.toString().equals("circles")) diagram.setHasCircles(false);
				if(elementFolder.toString().equals("stars")) diagram.setHasStars(false);
		}
	}
	
	
	public void refreshElementsSelection(Diagram diagram) {
		diagram.getSelectionModel().removeAllFromSelectionList();
		for (DiagramElement diagramElement : diagramElements){
			if (AppCore.getInstance().getWorkspaceTree().getSelectionModel().isPathSelected(new TreePath(diagramElement.getPath()))) {
				diagram.getSelectionModel().addToSelectionList(diagramElement);
			}
		}
	}
	
}
