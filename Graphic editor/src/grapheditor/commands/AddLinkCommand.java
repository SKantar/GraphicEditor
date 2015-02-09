package grapheditor.commands;

import grapheditor.app.AppCore;
import grapheditor.model.DiagramModel;
import grapheditor.model.elements.LinkElement;
import grapheditor.model.workspace.nodes.Diagram;
import grapheditor.model.workspace.nodes.ElementFolder;

import javax.swing.SwingUtilities;

public class AddLinkCommand implements AbstractCommandInterface{
	private Diagram diagram;
	private LinkElement link;
	private DiagramModel model;
	private boolean firstTime;
	
	public AddLinkCommand(Diagram diagram, LinkElement link) {
		this.diagram = diagram;
		this.link = link;
		this.model = diagram.getDiagramModel();
		this.firstTime = true;
	}

	public void doCommand() {
		
		if (this.firstTime){
			this.firstTime = false;
			//System.out.println("Desilo se first time");
			int elementNumber = model.getCount();
			link.getInput().setFree(false);
			link.getOutput().setFree(false);
			link.setName("Link " + elementNumber);
			link.setFinished(true);
			link.removePointAt(0);
			makeElementNode(link, diagram);
			diagram.getSelectionModel().removeAllFromSelectionList();
			diagram.getSelectionModel().addToSelectionList(link);
			diagram.getSelectionModel().updateJTree();
			SwingUtilities.updateComponentTreeUI(AppCore.getInstance().getWorkspaceTree());
				
			diagram.getDiagramModel().fireUpdatePerformed(link.getRepaintBounds());
			return;
		}
		
		makeElementNode(link,diagram);
		diagram.getDiagramModel().addDiagramElement(link);
		link.getInput().setFree(false);
		link.getOutput().setFree(false);
		diagram.getSelectionModel().removeAllFromSelectionList();
		diagram.getSelectionModel().addToSelectionList(link);
		diagram.getSelectionModel().updateJTree();
		SwingUtilities.updateComponentTreeUI(AppCore.getInstance().getWorkspaceTree());
		diagram.getDiagramModel().fireUpdatePerformed(link.getRepaintBounds());
		
	}

	public void undoCommand() {
		link.getInput().setFree(true);
		link.getOutput().setFree(true);
		this.diagram.getDiagramModel().removeFromJTree(this.link);
		this.diagram.getSelectionModel().removeFromSelectionList(this.link);
		model.removeLink(this.link);
		model.fireUpdatePerformed(link.getRepaintBounds());
	}
	
	public static void makeElementNode(LinkElement link, Diagram diagram){		
		
		ElementFolder elementFolder = null;
		int i ;
		for(i = 0; i < diagram.getChildCount(); i++)
			if(diagram.getChildAt(i).toString().equals("links")){
				elementFolder = (ElementFolder)diagram.getChildAt(i);
				elementFolder.add(link);
				break;
			}
		
		if(i!= diagram.getChildCount()) return;
		elementFolder = new ElementFolder("links", diagram.getDiagramModel());
		diagram.add(elementFolder);
		elementFolder.add(link);
		
	}
	
	
}
