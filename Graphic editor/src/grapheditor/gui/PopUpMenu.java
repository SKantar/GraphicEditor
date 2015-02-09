package grapheditor.gui;

import java.awt.Component;

import grapheditor.app.AppCore;

import javax.swing.JPopupMenu;

/**Klasa PopUpMenuDiagram nasledjuje klasu JPopupMenu i predtavlja
 * popup meni koji se otvara prilikom otvaranja popupmenija na dijagram 
 * instanci u stablu
 * @author Kantar
 */

@SuppressWarnings("serial")
public class PopUpMenu extends JPopupMenu{

	private static int type;
	
	public PopUpMenu() {
		super();
	}
	
	@Override
	public void show(Component invoker, int x, int y) {
		removeAll();
		if(type == 1){
			this.initProjectPopUpMenu();
		}else if(type == 2){
			this.initDiagramPopUpMenu();
		}else if(type == 3){
			this.initElementPopUpMenu();
		}else if(type == 4){
			this.initElementsPopUpMenu();
		}else if(type == 5){
			this.initElementInDiagramPopUpMenu();
		}else if(type == 6){
			this.initLinkInDiagramPopUpMenu();
		}else if(type==7){
			this.initClearPopUpMenu();
		}
			
		super.show(invoker, x, y);
		
	}
	
	
	private void initLinkInDiagramPopUpMenu() {

		add(AppCore.getInstance().getActionManager().getDeleteElementsAction());
		addSeparator();
		add(AppCore.getInstance().getActionManager().getElementPropertiesAction());
		
	}

	public void initProjectPopUpMenu() {
		add(AppCore.getInstance().getActionManager().getNewDiagramAction());
		add(AppCore.getInstance().getActionManager().getRenameAction());
		add(AppCore.getInstance().getActionManager().getSaveProjectAction());
		add(AppCore.getInstance().getActionManager().getCloseProjectAction());
		add(AppCore.getInstance().getActionManager().getDeleteAction());
		
	}
	
	public void initDiagramPopUpMenu() {
		add(AppCore.getInstance().getActionManager().getRenameAction());
		add(AppCore.getInstance().getActionManager().getCloseDiagramAction());
		add(AppCore.getInstance().getActionManager().getDeleteAction());
	}
	
	public void initElementPopUpMenu(){
		add(AppCore.getInstance().getActionManager().getRenameAction());
		add(AppCore.getInstance().getActionManager().getDeleteElementsAction());
		addSeparator();
		add(AppCore.getInstance().getActionManager().getElementPropertiesAction());
	}
	
	public void initElementInDiagramPopUpMenu(){

		add(AppCore.getInstance().getActionManager().getEditCopyAction());
		add(AppCore.getInstance().getActionManager().getEditCutAction());
		add(AppCore.getInstance().getActionManager().getEditPasteAction());
		addSeparator();
		add(AppCore.getInstance().getActionManager().getRotateElementLeftAction());
		add(AppCore.getInstance().getActionManager().getRotateElementRightAction());
		addSeparator();
		add(AppCore.getInstance().getActionManager().getDeleteElementsAction());
		addSeparator();
		add(AppCore.getInstance().getActionManager().getElementPropertiesAction());
	}
	
	public void initElementsPopUpMenu(){
		add(AppCore.getInstance().getActionManager().getEditCopyAction());
		add(AppCore.getInstance().getActionManager().getEditCutAction());
		add(AppCore.getInstance().getActionManager().getEditPasteAction());
		addSeparator();
		add(AppCore.getInstance().getActionManager().getDeleteElementsAction());
	}
	
	public void initClearPopUpMenu(){
		add(AppCore.getInstance().getActionManager().getEditPasteAction());
	}

	
	public static int getType() {
		return type;
	}
	
	public static void setType(int type) {
		PopUpMenu.type = type;
	}

}
