package grapheditor.actions;

import grapheditor.utils.Utils;
import grapheditor.view.DiagramView;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.KeyStroke;

/**Klasa CascadeDiagramAction nasledjuje klasu AbstractGEDAction i modeluje akciju
 * postavlja sve otvorene prozore unutar glavnog prozora kaskadno
 * @author Kantar
 */

@SuppressWarnings("serial")
public class CascadeDiagramAction extends AbstractGEDAction{
		
public CascadeDiagramAction() {	
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_T, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON, loadIcon("images/cascade.png"));
		putValue(NAME, "Cascade");
		putValue(SHORT_DESCRIPTION, "Cascade");
	}

public void actionPerformed(ActionEvent arg0) {
		List<DiagramView> visibleDiagrams = Utils.getVisibleDiagrams();
		int i = visibleDiagrams.size();
		for(DiagramView frame : visibleDiagrams){
			frame.setBounds(i  * DiagramView.xOffset, i * DiagramView.yOffset, DiagramView.defaultWidth, DiagramView.defaultHeight);
			i--;
		}
	}

}
