package grapheditor.actions;

import grapheditor.app.AppCore;
import grapheditor.utils.Utils;
import grapheditor.view.DiagramView;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.KeyStroke;

/**Klasa TileVerticallyDiagramAction nasledjuje klasu AbstractGEDAction i modeluje akciju
 * postavlja sve otvorene prozore unutar glavnog prozora vertikalno i izjednacuje im velicinu
 * @author Kantar
 */

@SuppressWarnings("serial")
public class TileVerticallyDiagramAction  extends AbstractGEDAction{

public TileVerticallyDiagramAction() {
		
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_E, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON, loadIcon("images/vertically.png"));
		putValue(NAME, "Tile Vertically");
		putValue(SHORT_DESCRIPTION, "Tile Vertically");
	}

	public void actionPerformed(ActionEvent arg0) {
		List<DiagramView> visibleDiagrams = Utils.getVisibleDiagrams();
		int i = 0;
		
		Dimension dimension = AppCore.getInstance().getDesktop().getSize();
		int width = dimension.width / visibleDiagrams.size();
		for(DiagramView frame : visibleDiagrams){
			if(frame.isVisible() == true){
				frame.setBounds(i * width, 0, width, dimension.height );
				i++;
			}
		}
	}

}
