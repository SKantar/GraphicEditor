package grapheditor.view;

import grapheditor.model.elements.CircleElement;
import grapheditor.model.elements.LinkElement;
import grapheditor.model.elements.RectangleElement;
import grapheditor.model.elements.StarElement;
import grapheditor.model.elements.TriangleElement;
import grapheditor.model.workspace.nodes.Diagram;
import grapheditor.model.workspace.nodes.ElementFolder;
import grapheditor.model.workspace.nodes.Project;

import java.awt.Component;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;


/**Klasa WorkspaceTreeCellRendered nasledjuje DefaultTreeCellRenderer 
 * klasa renderuje prikaz JTree-ja, svaka stavka ima svoju sliku
 * @author Kantar
 */

@SuppressWarnings("serial")
public class WorkspaceTreeCellRendered extends DefaultTreeCellRenderer{

public WorkspaceTreeCellRendered() {
		
		// TODO Auto-generated constructor stub
	}

	  public Component getTreeCellRendererComponent(
              JTree tree,
              Object value,
              boolean sel,
              boolean expanded,
              boolean leaf,
              int row,
              boolean hasFocus) {
                  super.getTreeCellRendererComponent(tree, value, sel,expanded, leaf, row,hasFocus);
                  
             
             if (value instanceof Diagram ) {
                 URL imageURL = getClass().getResource("images/tdiagram.png");
                 Icon icon = null;
                 if (imageURL != null)                       
                     icon = new ImageIcon(imageURL);
                 setIcon(icon);
 
             } else if (value instanceof Project ) {
                 URL imageURL = getClass().getResource("images/tproject.png");
                 Icon icon = null;
                 if (imageURL != null)                       
                     icon = new ImageIcon(imageURL);
                 setIcon(icon);    
            } else if (value instanceof Project ){
            	URL imageURL = getClass().getResource("images/tworkspace.png");
                Icon icon = null;
                if (imageURL != null)                       
                    icon = new ImageIcon(imageURL);
                setIcon(icon);
            	
            } else if (value instanceof ElementFolder ){
            	URL imageURL = getClass().getResource("images/tworkspace.png");
                Icon icon = null;
                if (imageURL != null)                       
                    icon = new ImageIcon(imageURL);
                setIcon(icon);
            } else if (value instanceof RectangleElement ){
            	URL imageURL = getClass().getResource("images/tdefault.png");
                Icon icon = null;
                if (imageURL != null)                       
                    icon = new ImageIcon(imageURL);
                setIcon(icon);
            }else if (value instanceof TriangleElement ){
            	URL imageURL = getClass().getResource("images/tdefault.png");
                Icon icon = null;
                if (imageURL != null)                       
                    icon = new ImageIcon(imageURL);
                setIcon(icon);
            }else if (value instanceof CircleElement ){
            	URL imageURL = getClass().getResource("images/tdefault.png");
                Icon icon = null;
                if (imageURL != null)                       
                    icon = new ImageIcon(imageURL);
                setIcon(icon);
            }else if (value instanceof StarElement ){
            	URL imageURL = getClass().getResource("images/tdefault.png");
                Icon icon = null;
                if (imageURL != null)                       
                    icon = new ImageIcon(imageURL);
                setIcon(icon);
            }else if (value instanceof LinkElement ){
            	URL imageURL = getClass().getResource("images/tdefault.png");
                Icon icon = null;
                if (imageURL != null)                       
                    icon = new ImageIcon(imageURL);
                setIcon(icon);
            } else {
            	URL imageURL = getClass().getResource("images/tworkspace.png");
                Icon icon = null;
                if (imageURL != null)                       
                    icon = new ImageIcon(imageURL);
                setIcon(icon);
            }
             
             
            return this;
	  	}
	  }  
