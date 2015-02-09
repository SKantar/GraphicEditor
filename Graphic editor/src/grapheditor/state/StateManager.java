package grapheditor.state;

import grapheditor.app.AppCore;
import grapheditor.view.DiagramView;
import grapheditor.view.DeviceType;

import java.io.Serializable;

@SuppressWarnings("serial")
public class StateManager implements Serializable {

	private State currentState;

	private DeviceState deviceState;
	private LinkState linkState;
	private SelectState selectState;
	private LassoState lassoState;
	private LassoZoomState lassoZoomState;
	private MoveState moveState;
	private ResizeState resizeState;
	private State lastState = new State();
	
	public StateManager(DiagramView diagramView){	
		deviceState = new DeviceState(diagramView);
		linkState = new LinkState(diagramView); 
		selectState=new SelectState(diagramView);
		lassoState = new LassoState(diagramView);
		lassoZoomState = new LassoZoomState(diagramView);
		moveState = new MoveState(diagramView);
		resizeState = new ResizeState(diagramView);
     	currentState = selectState;
	}
	
	public void setCircleState() {

		currentState.exitState();
		deviceState.setDeviceType(DeviceType.CIRCLE);
		currentState = deviceState;
		AppCore.getInstance().getPalette().showInputCountBox();
		AppCore.getInstance().getStatusBar().update_status_bar();
	}
	
	public void setLinkState(){ 

		currentState.exitState();
		currentState = linkState;
		AppCore.getInstance().getStatusBar().update_status_bar();
		AppCore.getInstance().getPalette().hideInputCountBox();
	}
	
	public void setRectangleState(){

		currentState.exitState();
		deviceState.setDeviceType(DeviceType.RECTANGLE);
		currentState = deviceState; 
		AppCore.getInstance().getPalette().showInputCountBox();
		AppCore.getInstance().getStatusBar().update_status_bar();
	}
	
	public void setTriangleState(){

		currentState.exitState();
		deviceState.setDeviceType(DeviceType.TRIANGLE);
		currentState = deviceState;
		AppCore.getInstance().getPalette().showInputCountBox();
		AppCore.getInstance().getStatusBar().update_status_bar();
	}
	
	public void setStarState(){

		currentState.exitState();
		deviceState.setDeviceType(DeviceType.STAR);
		currentState = deviceState;
		AppCore.getInstance().getPalette().showInputCountBox();
		AppCore.getInstance().getStatusBar().update_status_bar();
	}
	
	public void setSelectState(){
		currentState.exitState();
		currentState = selectState;
		AppCore.getInstance().getStatusBar().update_status_bar();
		AppCore.getInstance().getPalette().hideInputCountBox();
	}
	
	public void setLassoState(){ 
		currentState.exitState();
		currentState = lassoState;
		AppCore.getInstance().getStatusBar().update_status_bar();
	}
	
	public void setLassoZoomState() {
		lastState = currentState;
		currentState.exitState();
		currentState = lassoZoomState;
		AppCore.getInstance().getPalette().hideInputCountBox();
		AppCore.getInstance().getStatusBar().update_status_bar();
	}
	
	public void setMoveState() {
		currentState = moveState;
		AppCore.getInstance().getStatusBar().update_status_bar();
	}
	
	public void setResizeState() {

		currentState.exitState();
		currentState = resizeState;
		AppCore.getInstance().getStatusBar().update_status_bar();
		resizeState.startState();
		AppCore.getInstance().getStatusBar().update_status_bar();
	}
	
	public State getCurrentState() {
		return currentState;
	}
	
	public DeviceState getDeviceState() {
		return deviceState;
	}
	
	public void setLastState() {
		currentState.exitState();
		currentState = lastState;
		AppCore.getInstance().getStatusBar().update_status_bar();
	}

	
}
