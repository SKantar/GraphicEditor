package grapheditor.controller;

import grapheditor.app.AppCore;
import grapheditor.model.workspace.nodes.Diagram;
import grapheditor.state.DeviceState;
import grapheditor.state.LinkState;
import grapheditor.state.SelectState;
import grapheditor.view.DiagramView;
import grapheditor.view.DeviceType;

import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.tree.TreePath;

/**Klasa DiagramViewFrameListener implementira InternalFrameListener
 * i osluskuje dogadjaje na JInternalFrameu
 * @author Kantar
 */

public class DiagramViewFrameListener extends InternalFrameAdapter {

	@Override
	public void internalFrameActivated(InternalFrameEvent arg0) {
		 Diagram diagram = ((DiagramView)arg0.getInternalFrame()).getDiagram();
		 AppCore.getInstance().getWorkspaceTree().setSelectionPath(new TreePath(diagram.getPath()));
		 
		 if(diagram.getCurrentState() instanceof DeviceState){
			 AppCore.getInstance().getPalette().showInputCountBox();
			 if(diagram.getDeviceType() == DeviceType.RECTANGLE)
				 AppCore.getInstance().getPalette().getRectangle().setSelected(true);
			 if(diagram.getDeviceType() == DeviceType.TRIANGLE)
				 AppCore.getInstance().getPalette().getTriangle().setSelected(true);
			 if(diagram.getDeviceType() == DeviceType.CIRCLE)
				 AppCore.getInstance().getPalette().getCircle().setSelected(true);
			 if(diagram.getDeviceType() == DeviceType.STAR)
				 AppCore.getInstance().getPalette().getStar().setSelected(true);
		 }
		 else if(diagram.getCurrentState() instanceof LinkState){
			 AppCore.getInstance().getPalette().hideInputCountBox();
			 AppCore.getInstance().getPalette().getLink().setSelected(true);
		 }
		 else if(diagram.getCurrentState() instanceof SelectState){
			 AppCore.getInstance().getPalette().hideInputCountBox();
			 AppCore.getInstance().getPalette().getHandCursor().setSelected(true);
		 }

			AppCore.getInstance().getStatusBar().update_status_bar();
	}

	@Override
	public void internalFrameClosed(InternalFrameEvent arg0) {
		DiagramView.setOpenFrameCount(DiagramView.getOpenFrameCount()-1);
	}
	
	@Override
	public void internalFrameDeactivated(InternalFrameEvent arg0) {
		Diagram diagram = ((DiagramView)arg0.getInternalFrame()).getDiagram();
		diagram.setCurrentState(((DiagramView)arg0.getInternalFrame()).getStateManager().getCurrentState());
		diagram.setDeviceType(((DiagramView)arg0.getInternalFrame()).getStateManager().getDeviceState().getDeviceType());
	}

}
