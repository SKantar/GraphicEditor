package grapheditor.controller;

import grapheditor.app.AppCore;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**Klasa ApplicationFrameListener implementira WindowListener
 * i osluskuje dogadjaje na prozoru
 * @author Kantar
 */

public class ApplicationFrameListener extends WindowAdapter{

	@Override
	public void windowClosing(WindowEvent arg0) {
		AppCore.getInstance().getActionManager().getCloseApplicationAction().actionPerformed(null);
	}

}
