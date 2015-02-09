package grapheditor.actions;

import grapheditor.app.AppCore;
import grapheditor.utils.Utils;
import grapheditor.view.DiagramView;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.KeyStroke;

/**Klasa TileHorizontallyDiagramAction nasledjuje klasu AbstractGEDAction i modeluje akciju
 * postavlja sve otvorene prozore unutar glavnog prozora horizontalno i izjednacuje im velicinu
 * @author Kantar
 */

@SuppressWarnings("serial")
public class TileHorizontallyDiagramAction extends AbstractGEDAction{

public TileHorizontallyDiagramAction() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_R, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON, loadIcon("images/horizontally.png"));
		putValue(NAME, "Tile Horizontally");
		putValue(SHORT_DESCRIPTION, "Tile Horizontally");
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		List<DiagramView> visibleDiagrams = Utils.getVisibleDiagrams();
		int i = 0;
		
		Dimension dimension = AppCore.getInstance().getDesktop().getSize();
		int height = dimension.height / visibleDiagrams.size();
		for(DiagramView frame : visibleDiagrams){
			if(frame.isVisible()){
				frame.setBounds(0, i  * height, dimension.width, height );
				i++;
			}
			
		}
		
	}

}
