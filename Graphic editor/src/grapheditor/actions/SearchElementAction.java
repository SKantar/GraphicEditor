package grapheditor.actions;

import grapheditor.app.AppCore;
import grapheditor.model.elements.DiagramElement;
import grapheditor.view.DiagramView;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public class SearchElementAction extends AbstractGEDAction {

	public SearchElementAction() {

		putValue(ACCELERATOR_KEY,
				KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));

		putValue(SMALL_ICON, loadIcon("images/find.png"));
		putValue(NAME, "Find Element");
		putValue(SHORT_DESCRIPTION, "Pronađi element");
	}

	public void actionPerformed(ActionEvent arg0) {

		DiagramView view = (DiagramView) AppCore.getInstance()
				.getDesktop().getSelectedFrame();
		
		if (view == null) return;
		
		String response = JOptionPane.showInputDialog(null,
				"Šta želite da pronađete?", "Enter your query",
				JOptionPane.QUESTION_MESSAGE);
		String regex = "";
		for (int i = 0; i < response.length(); i++) {
			if (response.charAt(i) == '*')
				regex += "\\p{ASCII}";
			regex += response.charAt(i);
		}
		ArrayList<DiagramElement> res = new ArrayList<DiagramElement>();
		for (int i = 0; i < view.getDiagram().getDiagramModel().getDiagramElements().size(); i++) {
			if (view.getDiagram().getDiagramModel().getDiagramElements().get(i).getName()
					.matches(regex)) {
				res.add(view.getDiagram().getDiagramModel().getDiagramElements().get(i));
			}
		}
		view.getDiagram().getSelectionModel().removeAllFromSelectionList();
		if(res.size() == 0)
			JOptionPane.showMessageDialog(null,"\tElement does not exist");
		else
			view.getDiagram().getSelectionModel().addToSelectionList(res);
			view.getDiagram().getSelectionModel().updateJTree();
	}

}
