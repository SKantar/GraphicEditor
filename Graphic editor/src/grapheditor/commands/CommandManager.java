package grapheditor.commands;

import grapheditor.app.AppCore;

import java.util.ArrayList;

public class CommandManager {
	//lista koja predstavlja stek na kome se nalaze konkretne izvršene komande
		private ArrayList<AbstractCommandInterface> commands = new ArrayList<AbstractCommandInterface>();
		//pokaziva�? steka, sadrži redni broj komande za undo / redo operaciju
		private int currentCommand = 0;
		
		/*
		 * Dodaje novu komandu na stek i poziva izvršavanje komande
		 */
		public void addCommand(AbstractCommandInterface command){
			while(currentCommand < commands.size())
				commands.remove(currentCommand);
			commands.add(command);
			doCommand();
		}
		
		/*
		 * Metoda koja poziva izvršavanje konkretne komande 
		 */
		public void doCommand(){
			if(currentCommand < commands.size()){
				commands.get(currentCommand++).doCommand();
				AppCore.getInstance().getActionManager().getUndoAction().setEnabled(true);
			}
			if(currentCommand==commands.size()){
				AppCore.getInstance().getActionManager().getRedoAction().setEnabled(false);
			}
		}

		/*
		 * Metoda koja poziva redo konkretne komande
		 */
		public void undoCommand(){
			if(currentCommand > 0){
				AppCore.getInstance().getActionManager().getRedoAction().setEnabled(true);
				commands.get(--currentCommand).undoCommand();
			}
			if(currentCommand==0){
				AppCore.getInstance().getActionManager().getUndoAction().setEnabled(false);
			}
		}
}
