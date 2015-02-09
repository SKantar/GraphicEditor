package grapheditor.gui;

import grapheditor.app.AppCore;
import grapheditor.model.elements.CircleElement;
import grapheditor.model.elements.DiagramDevice;
import grapheditor.model.elements.DiagramElement;
import grapheditor.model.elements.LinkElement;
import grapheditor.model.elements.RectangleElement;
import grapheditor.model.elements.StarElement;
import grapheditor.model.elements.TriangleElement;
import grapheditor.state.DeviceState;
import grapheditor.state.LassoState;
import grapheditor.state.LassoZoomState;
import grapheditor.state.LinkState;
import grapheditor.state.MoveState;
import grapheditor.state.ResizeState;
import grapheditor.state.SelectState;
import grapheditor.view.DiagramView;
import grapheditor.view.DeviceType;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class StatusBar extends JPanel{
	
	private StatusPane status=new StatusPane("State");
	private StatusPane elementType=new StatusPane("Element type");
	private StatusPane elementName=new StatusPane("Element name");
	private StatusPane position=new StatusPane("Position");
	private StatusPane dimension=new StatusPane("Dimension");
	
	public StatusBar(){
		setLayout(new FlowLayout(FlowLayout.LEFT));
		add(status);
		add(elementType);
		add(elementName);
		add(position);
		add(dimension);
	}
	
	private class StatusPane extends JLabel{
		public StatusPane (String text){
			setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
			setBackground(Color.GRAY);
			setPreferredSize(new Dimension(170,20));
			setHorizontalAlignment(CENTER);
			setText(text);
		}
	}
	
	public void setStatus(String status){
		this.status.setText(status);
	}
	public void setElementType(String elementType){
		this.elementType.setText(elementType);
	}
	public void setElementName(String elementName){
		this.elementName.setText(elementName);
	}
	public void setPosition(String position){
		this.position.setText(position);
	}
	public void setDimension(String dimension){
		this.dimension.setText(dimension);
	}
	
	public void update_status_bar(){
		DiagramView diagramView = (DiagramView)AppCore.getInstance().getDesktop().getSelectedFrame();
		if(diagramView != null){
			if(diagramView.getStateManager().getCurrentState() instanceof DeviceState){
				AppCore.getInstance().getStatusBar().setStatus("Add Element");
				DeviceType deviceType = diagramView.getStateManager().getDeviceState().getDeviceType();
					 if(deviceType == DeviceType.RECTANGLE)
						 setElementType("Rectangle");
					 if(deviceType == DeviceType.TRIANGLE)
						 setElementType("Triangle");
					 if(deviceType == DeviceType.CIRCLE)
						 setElementType("Circle");
					 if(deviceType == DeviceType.STAR)
						 setElementType("Star");
			}else if(diagramView.getStateManager().getCurrentState() instanceof LinkState){
				setStatus("Add Link");
				setElementType("");
			}else if(diagramView.getStateManager().getCurrentState() instanceof SelectState){
				setStatus("Select");
				setElementType("");
			}else if(diagramView.getStateManager().getCurrentState() instanceof LassoState){
				setStatus("Lasso select");
				setElementType("");
			}else if(diagramView.getStateManager().getCurrentState() instanceof LassoZoomState){
				setStatus("Lasso zoom");
				setElementType("");
			}else if(diagramView.getStateManager().getCurrentState() instanceof MoveState){
				setStatus("Move");
			}else if(diagramView.getStateManager().getCurrentState() instanceof ResizeState){
				setStatus("Resize");
			}
			
			
			int selectedSize = diagramView.getDiagram().getSelectionModel().getSelectionListSize();
			if( selectedSize > 0){
				if(selectedSize == 1){
					if(!diagramView.getDiagram().getSelectionModel().checkCutCopy()){
						AppCore.getInstance().getActionManager().getEditCopyAction().setEnabled(false);
						AppCore.getInstance().getActionManager().getEditCutAction().setEnabled(false);
						AppCore.getInstance().getActionManager().getRotateElementLeftAction().setEnabled(false);
						AppCore.getInstance().getActionManager().getRotateElementRightAction().setEnabled(false);
					}else{
						AppCore.getInstance().getActionManager().getEditCopyAction().setEnabled(true);
						AppCore.getInstance().getActionManager().getEditCutAction().setEnabled(true);
						AppCore.getInstance().getActionManager().getRotateElementLeftAction().setEnabled(true);
						AppCore.getInstance().getActionManager().getRotateElementRightAction().setEnabled(true);
					}
					DiagramElement diagramElement = (DiagramElement)diagramView.getDiagram().getSelectionModel().getSelectionList().get(0);
					if(diagramElement instanceof DiagramDevice){
						DiagramDevice diagramDevice = (DiagramDevice) diagramElement;
						if(diagramDevice instanceof RectangleElement)
							setElementType("Rectangle");
						if(diagramDevice instanceof TriangleElement)
							setElementType("Triangle");
						if(diagramDevice instanceof CircleElement)
							setElementType("Circle");
						if(diagramDevice instanceof StarElement)
							setElementType("Star");
						setElementName(diagramDevice.getName());
						setDimension(diagramDevice.getSize().getWidth() +" x" +diagramDevice.getSize().getHeight());	
					}
					else if(diagramElement instanceof LinkElement){
						LinkElement linkElement = (LinkElement) diagramElement;
						setElementType("Link");
						setElementName(linkElement.getName());
						setDimension("");
					}
				}
				else{
					setElementName("");
					setDimension("");
					if(!diagramView.getDiagram().getSelectionModel().checkCutCopy()){
						AppCore.getInstance().getActionManager().getEditCopyAction().setEnabled(false);
						AppCore.getInstance().getActionManager().getEditCutAction().setEnabled(false);
					}else{
						AppCore.getInstance().getActionManager().getEditCopyAction().setEnabled(true);
						AppCore.getInstance().getActionManager().getEditCutAction().setEnabled(true);	
					}

					AppCore.getInstance().getActionManager().getRotateElementLeftAction().setEnabled(false);
					AppCore.getInstance().getActionManager().getRotateElementRightAction().setEnabled(false);
				}
			}else{
				setElementName("");
				setDimension("");
				
				AppCore.getInstance().getActionManager().getEditCopyAction().setEnabled(false);
				AppCore.getInstance().getActionManager().getEditCutAction().setEnabled(false);

				AppCore.getInstance().getActionManager().getRotateElementLeftAction().setEnabled(false);
				AppCore.getInstance().getActionManager().getRotateElementRightAction().setEnabled(false);
			}
		}
	}
}
