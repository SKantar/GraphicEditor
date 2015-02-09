package grapheditor.actions;

import grapheditor.app.AppCore;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JInternalFrame;
import javax.swing.KeyStroke;

/**Klasa CloseAllDiagramsAction nasledjuje klasu AbstractGEDAction i modeluje akciju
 * zatvara sve otvorene prozore unutar glavnog prozora
 * @author Kantar
 */

@SuppressWarnings("serial")
public class CloseAllDiagramsAction extends AbstractGEDAction {


	public CloseAllDiagramsAction() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_W, ActionEvent.CTRL_MASK+ActionEvent.SHIFT_MASK));
		putValue(SMALL_ICON, loadIcon("images/closealldiagrams.png"));
		putValue(NAME, "Close all");
		putValue(SHORT_DESCRIPTION, "Close all diagrams");
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		JInternalFrame[] frames = AppCore.getInstance().getDesktop().getAllFrames();
		for(JInternalFrame frame : frames){
			frame.setVisible(false);
		}
	}

}
