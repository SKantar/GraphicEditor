package grapheditor.files;

import java.io.File;

import javax.swing.filechooser.FileFilter;


public class WorkspaceFileFilter extends FileFilter {

	@Override
	public String getDescription() {
		return "GrafEditor Project Files (*.gpw)";
	}

	@Override
	public boolean accept(File f) {
		return (f.isDirectory() || 
                f.getName().toLowerCase().endsWith(".gpw") || !f.getName().contains("."));
	}

}
