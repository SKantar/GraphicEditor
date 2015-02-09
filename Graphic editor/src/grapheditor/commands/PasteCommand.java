package grapheditor.commands;

import grapheditor.app.AppCore;
import grapheditor.model.DiagramElementsSelection;
import grapheditor.model.elements.DiagramDevice;
import grapheditor.model.elements.DiagramElement;
import grapheditor.model.elements.LinkElement;
import grapheditor.model.workspace.nodes.Diagram;

import java.awt.Point;
import java.awt.datatransfer.Transferable;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class PasteCommand implements AbstractCommandInterface {
	int trans;
	private Diagram diagram;
	ArrayList<DiagramElement> elements = null;

	public PasteCommand(Diagram diagram) {
		this.diagram = diagram;
	}

	@Override
	public void doCommand() {
		
		elements = new ArrayList<>();
		trans = AppCore.getInstance().getPasteCounter();
		Transferable clipboardContent = AppCore.getInstance().getClipboard().getContents(AppCore.getInstance());
		if (clipboardContent != null && clipboardContent.isDataFlavorSupported(DiagramElementsSelection.elementFlavor)) {
			try {
				@SuppressWarnings("unchecked")
				ArrayList<DiagramElement> tempElements = (ArrayList<DiagramElement>) clipboardContent.getTransferData(DiagramElementsSelection.elementFlavor);
				
				for (int i = 0; i < tempElements.size(); i++) {
					if (tempElements.get(i) instanceof DiagramDevice) {
							DiagramDevice device=(DiagramDevice) tempElements.get(i).clone();
							Point2D newLocation=(Point2D) device.getPosition().clone();
							newLocation.setLocation(device.getPosition().getX()+40 * trans,device.getPosition().getY()+40*trans);
							device.setPosition(newLocation);
							int temp = trans;
							while(!diagram.getDiagramModel().isAvelableName(((DiagramDevice) tempElements.get(i)).getName() + "(" + temp + ")")){
								temp++;
							}
							device.setName(((DiagramDevice) tempElements.get(i)).getName() + "(" + temp + ")");

							AddDeviceCommand.makeElementNode(device, diagram);
							diagram.getDiagramModel().addDiagramElement(device);
							elements.add(device);
					}
				}
				
				for (int i = 0; i < tempElements.size(); i++) {
					if (tempElements.get(i) instanceof LinkElement) {
						LinkElement link = (LinkElement) tempElements.get(i).clone();
						elements.add(link);
						Point p = new Point();
						
						p.setLocation(link.getStartDevice().getPosition().getX() +40 * trans+10, link.getStartDevice().getPosition().getY()+40 * trans+10);
						int el = diagram.getDiagramModel().getElementAtPosition(p);
						DiagramElement startElement= diagram.getDiagramModel().getElementAt(el);
						
						p.setLocation(link.getEndDevice().getPosition().getX() +40 * trans+10, link.getEndDevice().getPosition().getY()+40 * trans+10);
						el = diagram.getDiagramModel().getElementAtPosition(p);
						DiagramElement endElement= diagram.getDiagramModel().getElementAt(el);
						
						link.setStartDevice((DiagramDevice)startElement);
						link.setEndDevice((DiagramDevice)endElement);
						link.setInput(((DiagramDevice)endElement).getInputAt(link.getInM()));
						((DiagramDevice)endElement).getInputAt(link.getInM()).setFree(false);
						link.setOutput(((DiagramDevice)startElement).getOutputAt(link.getOutM()));
						((DiagramDevice)startElement).getOutputAt(link.getOutM()).setFree(false);
						//System.out.println(startElement.getName() + " " + endElement.getName());
						
						//AddDeviceCommand.makeElementNode(device, diagram);
						for(Point2D point : link.getPoints()){
							point.setLocation(point.getX()+40*trans, point.getY()+40*trans);
						}
						int temp = trans;
						while(!diagram.getDiagramModel().isAvelableName(((LinkElement) tempElements.get(i)).getName() + "(" + temp + ")")){
							temp++;
						}
						link.setName(((LinkElement) tempElements.get(i)).getName() + "(" + temp + ")");
						AddLinkCommand.makeElementNode(link, diagram);
						diagram.getDiagramModel().addDiagramElement(link);
						elements.add(link);
						
					}
				}
				diagram.getSelectionModel().removeAllFromSelectionList();
				diagram.getSelectionModel().addToSelectionList(elements);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	@Override
	public void undoCommand() {
		for (DiagramElement element : elements) {
			diagram.getDiagramModel().removeFromJTree(element);
			diagram.getDiagramModel().removeDiagramElement(element);
			diagram.getSelectionModel().removeFromSelectionList(element);
			AppCore.getInstance().setPasteCounter(AppCore.getInstance().getPasteCounter()-2);
		}
	}

}
