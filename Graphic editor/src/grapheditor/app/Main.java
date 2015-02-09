package grapheditor.app;

import grapheditor.app.AppCore;

/**Klasa Main sadrzi main metodu i pravi instancu glavne klase GEDa
 * @author Kantar
 */


public class Main {
	public static void main(String[] args) {
		AppCore app=AppCore.getInstance();
        app.setVisible(true);
	}
}
