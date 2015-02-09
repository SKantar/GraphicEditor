package grapheditor.model.workspace.nodes;

import java.io.Serializable;

import grapheditor.model.DiagramModel;

import javax.swing.tree.DefaultMutableTreeNode;

@SuppressWarnings("serial")
public class ElementFolder extends DefaultMutableTreeNode implements Serializable{
	
	String name;
	DiagramModel model;
	
	public ElementFolder(String name, DiagramModel model) {
		this.name = name;
		this.model = model;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	

}
