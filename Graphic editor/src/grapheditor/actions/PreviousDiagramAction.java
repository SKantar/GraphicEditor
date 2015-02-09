package grapheditor.actions;

import grapheditor.utils.Utils;
import grapheditor.view.DiagramView;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;
import java.util.List;

import javax.swing.KeyStroke;

/**Klasa NextDiagramAction nasledjuje klasu AbstractGEDAction i modeluje akciju
 * postavlja na sledeci prozor , odnosno, prozor koji je prethodno bio otvoren
 * @author Kantar
 */

@SuppressWarnings("serial")
public class PreviousDiagramAction extends AbstractGEDAction{

	public PreviousDiagramAction() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_G, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON, loadIcon("images/previousdiagram.png"));
		putValue(NAME, "Previous diagram");
		putValue(SHORT_DESCRIPTION, "Previous diagram");
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		List<DiagramView> visibleDiagrams = Utils.getSortedVisibleDiagrams();
		
		for (int i = 0; i < visibleDiagrams.size(); i++) {
			if (visibleDiagrams.get(i).isSelected()) {
				try {
					visibleDiagrams.get((i + visibleDiagrams.size() - 1) % visibleDiagrams.size()).setSelected(true);
				} catch (PropertyVetoException e) {
					e.printStackTrace();
				}
				break;
			}
		}
	}

}
