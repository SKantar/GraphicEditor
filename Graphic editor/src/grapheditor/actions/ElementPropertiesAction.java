package grapheditor.actions;

import grapheditor.app.AppCore;
import grapheditor.gui.ElementDialog;
import grapheditor.model.elements.DiagramDevice;
import grapheditor.model.elements.DiagramElement;
import grapheditor.model.elements.LinkElement;
import grapheditor.view.DiagramView;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class ElementPropertiesAction extends AbstractGEDAction{
	
	public ElementPropertiesAction() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_DELETE,ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON, loadIcon("images/properties.png"));
		putValue(NAME, "Properties");
		putValue(SHORT_DESCRIPTION, "Properties");
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		DiagramView view= (DiagramView) AppCore.getInstance().getDesktop().getSelectedFrame();
		DiagramElement element = view.getDiagram().getSelectionModel().getElementFromSelectionListAt(0);
		ElementDialog ed = new ElementDialog(element);
		ed.setModal(true);
		ed.setVisible(true);
		if(!ed.isOk()) return;
		if(!ed.getNewName().equals(element.getName()))
		if(view.getDiagram().getDiagramModel().isAvelableName(ed.getNewName()))
			element.setName(ed.getNewName());
		else
			JOptionPane.showMessageDialog(null,"Name exists, not changed");
		
			element.setDescription(ed.getDescription());
			if(element instanceof DiagramDevice){
				element.setFillColor(ed.getColor());
				if(((DiagramDevice) element).getInputNo() != ed.getInputNo()){
					view.getDiagram().getDiagramModel().deleteLinkForDevice((DiagramDevice)element);
					((DiagramDevice) element).setInputNo(ed.getInputNo());
					
				}
				if(((DiagramDevice) element).getOutputNo() != ed.getOutputNo()){
					view.getDiagram().getDiagramModel().deleteLinkForDevice((DiagramDevice)element);
					((DiagramDevice) element).setOutputNo(ed.getOutputNo());
				}
			}
			else if(element instanceof LinkElement)
				if(ed.getColor() != Color.WHITE)
				  	((LinkElement)element).setStrokeColor(ed.getColor());
		view.getDiagram().getDiagramModel().fireUpdatePerformed(element.getRepaintBounds());
		SwingUtilities.updateComponentTreeUI(AppCore.getInstance().getWorkspaceTree());
	}
	
}
