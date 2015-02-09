package grapheditor.state;

import grapheditor.app.AppCore;
import grapheditor.gui.PopUpMenu;
import grapheditor.model.elements.DiagramDevice;
import grapheditor.model.elements.DiagramElement;
import grapheditor.view.DiagramView;
import grapheditor.view.DiagramView.Handle;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class SelectState extends State{
	private DiagramView diagramView; 
	//indeks elementa koji je selektovan
	private int elementInMotion = -1;
	private int elementID = -1;
	private boolean selected = false;
	private Handle handleInMotion = null;
	private PopUpMenu popUpMenu= AppCore.getInstance().getWorkspaceTree().getPopUpMenu();
	
	public SelectState(DiagramView diagramView) {
		this.diagramView = diagramView;
	}
	
	
	public void mousePressed(MouseEvent e) {
		Point position = e.getPoint();
		diagramView.transformToUserSpace(position);
		/**
		 * ako je pritisnut levi taster misa
		 */

		if(SwingUtilities.isLeftMouseButton(e)){
			if(e.getClickCount() == 2){
				elementInMotion = diagramView.getDiagram().getDiagramModel().getElementAtPosition(position);
				if (elementInMotion != -1) {
					DiagramElement element = diagramView.getDiagram().getDiagramModel().getElementAt(elementInMotion);
					diagramView.getDiagram().getSelectionModel().removeAllFromSelectionList();
					diagramView.getDiagram().getSelectionModel().addToSelectionList(element);
					diagramView.getDiagram().getSelectionModel().updateJTree();
					AppCore.getInstance().getActionManager().getElementPropertiesAction().actionPerformed(null);
				}else{
					if(!e.isControlDown())
						diagramView.getDiagram().getSelectionModel().removeAllFromSelectionList();
						diagramView.getDiagram().getSelectionModel().updateJTree();
				}
			}else{
				handleInMotion = diagramView.getDeviceAndHandleForPoint(position);
				if(handleInMotion == null){
					elementInMotion = diagramView.getDiagram().getDiagramModel().getElementAtPosition(position);
					if(elementInMotion != -1){
						selected = true;
						DiagramElement element = diagramView.getDiagram().getDiagramModel().getElementAt(elementInMotion);
						if(diagramView.getDiagram().getSelectionModel().isElementSelected(element)){
							elementID = elementInMotion;
						}else{
							if(!e.isControlDown())
								diagramView.getDiagram().getSelectionModel().removeAllFromSelectionList();
							diagramView.getDiagram().getSelectionModel().addToSelectionList(element);
							diagramView.getDiagram().getSelectionModel().updateJTree();
						}
					}else{
						selected = false;
						elementID = -1;
						diagramView.getDiagram().getSelectionModel().removeAllFromSelectionList();
						diagramView.getDiagram().getSelectionModel().updateJTree();
						AppCore.getInstance().getStatusBar().update_status_bar();
					}	
				}
			}
			diagramView.getDiagram().getSelectionModel().updateJTree();
		}	
	}
	
	public void mouseReleased(MouseEvent e) {

		selected = false;
		Point position = e.getPoint();
		diagramView.transformToUserSpace(position);
		if(SwingUtilities.isRightMouseButton(e)){
			elementInMotion = diagramView.getDiagram().getDiagramModel().getElementAtPosition(position);
			if (elementInMotion != -1) {
				DiagramElement element = diagramView.getDiagram().getDiagramModel().getElementAt(elementInMotion);
				if (!diagramView.getDiagram().getSelectionModel().isElementSelected(element)){
					if(!e.isControlDown())
						diagramView.getDiagram().getSelectionModel().removeAllFromSelectionList();
					diagramView.getDiagram().getSelectionModel().addToSelectionList(element);
					if(diagramView.getDiagram().getSelectionModel().getSelectionListSize() == 1)
						if(diagramView.getDiagram().getSelectionModel().getElementFromSelectionListAt(0) instanceof DiagramDevice)
							PopUpMenu.setType(5);
						else
							PopUpMenu.setType(6);
					else 
						PopUpMenu.setType(4);
					popUpMenu.show(e.getComponent(), e.getX(), e.getY());
				}else{
					if(diagramView.getDiagram().getSelectionModel().getSelectionListSize() == 1)
						PopUpMenu.setType(5);
					else 
						PopUpMenu.setType(4);
					popUpMenu.show(e.getComponent(), e.getX(), e.getY());
				}
			}else{
				PopUpMenu.setType(7);
				popUpMenu.show(e.getComponent(), e.getX(), e.getY());
			}

			diagramView.getDiagram().getSelectionModel().updateJTree();
		}else{
			elementInMotion = diagramView.getDiagram().getDiagramModel().getElementAtPosition(position);
			if (elementInMotion != -1) {
				if(elementInMotion == elementID){
					DiagramElement element = diagramView.getDiagram().getDiagramModel().getElementAt(elementInMotion);
					if (diagramView.getDiagram().getSelectionModel().isElementSelected(element)) {
						if (!e.isControlDown()) {
							diagramView.getDiagram().getSelectionModel().removeAllFromSelectionList();
							diagramView.getDiagram().getSelectionModel().addToSelectionList(element);
						} else if (e.isControlDown()) {
							diagramView.getDiagram().getSelectionModel().removeFromSelectionList(element);
						}
					}else{
						if(!e.isControlDown())
							diagramView.getDiagram().getSelectionModel().removeAllFromSelectionList();
						diagramView.getDiagram().getSelectionModel().addToSelectionList(element);
						diagramView.getDiagram().getSelectionModel().updateJTree();
					}
					elementID = -1;
				}
			}
		}
	}
	
	
	public void mouseMoved(MouseEvent e) {
		Point2D point = e.getPoint();
		diagramView.transformToUserSpace(point);
		diagramView.setMouseCursor(point);
		
		
	}	

	public void mouseDragged(MouseEvent e) {
		if(SwingUtilities.isLeftMouseButton(e)){
			//vrši se povlačenje sa levim tasterom miša
			//provera da li je selektovan handle elementa, tada se radi resize elementa
			Point position = e.getPoint();
			diagramView.transformToUserSpace(position);
			handleInMotion = diagramView.getDeviceAndHandleForPoint(position);
			if(handleInMotion != null){
				diagramView.startResizeState();
			}else{
				//nije selektovan handle, da li je selektovan element
				elementInMotion = diagramView.getDiagram().getDiagramModel().getElementAtPosition(position);
				//DiagramElement element = diagramView.getDiagram().getDiagramModel().getElementAt(elementInMotion);
				if(selected ){
					//selektovan je element ili grupa elemenata
					//preci u MoveState
					//if(elementID == -1)
					if(!e.isControlDown())
					 diagramView.startMoveState();
					return;
					
					
				}else
					//nije pogodjen element, prelazimo u Laso stanje
				//if(diagramView.getDiagram().getSelectionModel().isElementSelected(element))
					diagramView.startLassoState();	
				}
		}

		AppCore.getInstance().getStatusBar().update_status_bar();
		
	}
}
