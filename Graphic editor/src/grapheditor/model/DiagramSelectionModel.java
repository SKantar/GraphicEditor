package grapheditor.model;

import grapheditor.app.AppCore;
import grapheditor.event.UpdateEvent;
import grapheditor.event.UpdateListener;
import grapheditor.model.elements.DiagramDevice;
import grapheditor.model.elements.DiagramElement;
import grapheditor.model.elements.LinkElement;
import grapheditor.view.DiagramView;
import grapheditor.view.painters.LinkPainter;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.DefaultSingleSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.EventListenerList;
import javax.swing.tree.TreePath;


@SuppressWarnings("serial")
public class DiagramSelectionModel extends DefaultSingleSelectionModel {


	/**
	 * Lista sa elementima koji su selektovani.
	 */
	private ArrayList<DiagramElement> selectionList = new ArrayList<DiagramElement>();
	
	transient  EventListenerList listenerList = new EventListenerList();
	UpdateEvent updateEvent = null;	
	
	private Object readResolve() {
		listenerList = new EventListenerList();
		return this;
	}
	
	/**
	 * Metoda dodaje element u listu selekcije.
	 * 
	 *
	 */
	public void addToSelectionList(DiagramElement element) {
		selectionList.add(element);
		fireUpdatePerformed(element.getRepaintBounds());
		AppCore.getInstance().getStatusBar().update_status_bar();
	}
	
	
	/**
	 * Metoda dodaje listu elemenata u selekcionu listu.
	 */
	public void addToSelectionList(ArrayList<DiagramElement> list) {
		selectionList.addAll(list);
		for(DiagramElement element : selectionList){
			fireUpdatePerformed(element.getRepaintBounds());
		}
		AppCore.getInstance().getStatusBar().update_status_bar();
	}
	
	
	/**
	 * Vraca broj elemenata u selekcionoj listi.
	 */
	public int getSelectionListSize() {
		return selectionList.size();
	}
	
	
	/**
	 * Vraca element iz selekcione liste koji se nalazi na indeksu koji se navodi
	 * kao argument.
	 */
	public DiagramElement getElementFromSelectionListAt(int index) {
		return (DiagramElement)selectionList.get(index);
	}
	
	
	/**
	 * Vraca indeks zadatog elementa u selekcionoj listi.
	 * @param element - element za koji se trazi indeks u listi
	 * @return - indeks elementa u listi ili -1

	 */
	public int getIndexByObject(DiagramElement element) {
		return selectionList.indexOf(element);
	}
	
	
	public ArrayList<DiagramElement> getSelected() {
		ArrayList<DiagramElement> selected=new ArrayList<DiagramElement>();
		selected.addAll(selectionList);
		
		return selected;
	}
	
	/**
	 * Uklanja element iz selekcione liste na datom indeksu.
	 * @param index - indeks elementa koji se uklanja iz selekcione liste.
	 */
	public void removeFromSelectionList(DiagramElement element) {
		selectionList.remove(element);
		fireUpdatePerformed(element.getRepaintBounds());
		AppCore.getInstance().getStatusBar().update_status_bar();
	}
	
	
	/**
	 * Uklanja sve elemente iz selekcione liste.
	 */
	public void removeAllFromSelectionList() {
		for(DiagramElement element : selectionList){
			fireUpdatePerformed(element.getRepaintBounds());
		}
		selectionList.clear();
		AppCore.getInstance().getStatusBar().update_status_bar();
	}
	
	
	/**
	 * Vraca objekat selekcione liste.
	 * @return - objekat selekcione liste

	 */
	public ArrayList<DiagramElement>  getSelectionList() {
		return selectionList;
	}
	
	public Iterator<DiagramElement> getSelectionListIterator(){
		return selectionList.iterator();
	}
	
	public boolean isElementSelected(DiagramElement element){
		return selectionList.contains(element);
		
	}

	/**
	 * Metoda selektuje sve elemente koji se seku ili su obuhvaceni
	 * pravougaonikom
	 * 
	 * @param rec
	 *            - selekcioni pravougaonik
	 * @param elements
	 *            - niz elemenata iz modela
	 * 
	 */
	public void selectElements(Rectangle2D rec,
			ArrayList<DiagramElement> elements) {
		Iterator<DiagramElement> it = elements.iterator();
		while (it.hasNext()) {
			DiagramElement element = it.next();
			if (element instanceof DiagramDevice) {
				DiagramDevice device = (DiagramDevice) element;
				if (rec.intersects(device.getPosition().getX(), device
						.getPosition().getY(), device.getSize().getWidth(),
						device.getSize().getHeight())) {
					if (!isElementSelected(device))
						selectionList.add(device);
				}
			} else {
				LinkElement link = (LinkElement) element;
				if (rec.contains(LinkPainter.findRectangle(link))) {
					if (!isElementSelected(link))
						selectionList.add(link);

				}
			}
		}
		AppCore.getInstance().getStatusBar().update_status_bar();

	}
	
	 public void addUpdateListener(UpdateListener l) {
	     listenerList.add(UpdateListener.class, l);
	 }

	 public void removeUpdateListener(UpdateListener l) {
	     listenerList.remove(UpdateListener.class, l);
	 }
	 
	 /**
		 * Javljamo svim listenerima da se događaj desio 
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
		
		public void updateJTree(){
			DiagramView dv = (DiagramView) AppCore.getInstance().getDesktop().getSelectedFrame();
			TreePath[] paths = new TreePath[dv.getDiagram().getSelectionModel().getSelectionListSize()];
			for (int i = 0; i < dv.getDiagram().getSelectionModel().getSelectionListSize(); i++) {
				paths[i] = dv.getPath(dv.getDiagram().getSelectionModel().getSelectionList().get(i));
			}
			AppCore.getInstance().getWorkspaceTree().clearSelection();
			AppCore.getInstance().getWorkspaceTree().addSelectionPaths(paths);
			SwingUtilities.updateComponentTreeUI(AppCore.getInstance().getWorkspaceTree());
		}
	
		public boolean checkCutCopy(){
			for(DiagramElement element : selectionList){
				if(element instanceof LinkElement){
					LinkElement link = (LinkElement) element;
					if(!selectionList.contains(link.getStartDevice()) || !selectionList.contains(link.getEndDevice()))
						return false;
				}
			}
			return true;
		}

		public boolean hasElement(DiagramElement dd1) {
			if(selectionList.contains(dd1)) return true;
			for(DiagramElement element : selectionList){
				if(element.getName().equals(dd1.getName())) return true;
			}
			return false;
		}
	
	
	
}
