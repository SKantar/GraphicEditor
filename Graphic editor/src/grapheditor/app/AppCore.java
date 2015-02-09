package grapheditor.app;

import grapheditor.actions.ActionManager;
import grapheditor.controller.ApplicationFrameListener;
import grapheditor.gui.Menu;
import grapheditor.gui.Palette;
import grapheditor.gui.StatusBar;
import grapheditor.gui.Toolbar;
import grapheditor.gui.WorkspaceTree;
import grapheditor.model.workspace.WorkspaceModel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

/**Klasa AppCore nasledjuje klasu JFrame i predstavlja glavnu
 * klasu aplikacije GED
 * @author Kantar
 */

@SuppressWarnings("serial")
public class AppCore extends JFrame implements ClipboardOwner{
	
	private static AppCore instance = null;
    
	private ActionManager actionManager;
    private Toolbar toolbar;
    private JMenuBar menu;
    private Palette palette;
    private StatusBar statusBar;
    private int pasteCounter = 0;
    
    private WorkspaceModel workspaceModel;
    private WorkspaceTree workspaceTree;
    
    private JDesktopPane desktop;
    
    private Clipboard clipboard = new Clipboard("Graphic editor clipboard");
    
	private AppCore() {
	}
	
	private void initialise(){
		actionManager=new ActionManager();
		
		
		initialiseWorkspaceTree();
		initialiseGUI();
		/*JFrame.setDefaultLookAndFeelDecorated(true);
		
		try {
			UIManager.setLookAndFeel(
			    UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	private void initialiseGUI(){
		menu=new Menu();
		setJMenuBar(menu);
		
		
		toolbar = new Toolbar();
		getContentPane().add(toolbar,BorderLayout.NORTH);
		
		palette = new Palette();
		getContentPane().add(palette, BorderLayout.EAST);
		
		statusBar = new StatusBar();
		getContentPane().add(statusBar, BorderLayout.SOUTH);
		
		desktop=new JDesktopPane();
		
		JScrollPane scroll=new JScrollPane(workspaceTree);
		scroll.setMinimumSize(new Dimension(200,150));
		JSplitPane split=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,scroll,desktop);
		add(split,BorderLayout.CENTER);
		split.setDividerLocation(250);
		setSize(1000,700);
		setExtendedState(MAXIMIZED_BOTH);
		setLocationRelativeTo(null);
		setTitle("Grafički editor");
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setIconImage((new ImageIcon(getClass().getResource("images/icon.png"))).getImage());
		addWindowListener(new ApplicationFrameListener());
	
	}
	private void initialiseWorkspaceTree(){
		workspaceTree=new WorkspaceTree();
		
		workspaceModel = new WorkspaceModel();
		
		//Workspace root = (Workspace) workspaceModel.getRoot();
		//workspaceTree.setRootVisible(false);
		
		
		workspaceTree.setModel(workspaceModel);
		ToolTipManager.sharedInstance().registerComponent(workspaceTree);

	}
	public ActionManager getActionManager(){
		return actionManager;
	}
	
	
	public static AppCore getInstance(){
		if (instance==null){
			instance=new AppCore();
			instance.initialise();
		}
		return instance;
	}


	public JDesktopPane getDesktop() {
		return desktop;
	}


	public WorkspaceTree getWorkspaceTree() {
		return workspaceTree;
	}


	public WorkspaceModel getWorkspaceModel() {
		return workspaceModel;
	}
	
	public Palette getPalette() {
		return palette;
	}
	
	public StatusBar getStatusBar() {
		return statusBar;
	}
	
	public Clipboard getClipboard() {
		return clipboard;
	}
	
	public int getPasteCounter() {
		return ++pasteCounter;
	}
	
	public void setPasteCounter(int pasteCounter) {
		this.pasteCounter = pasteCounter;
	}

	@Override
	public void lostOwnership(Clipboard arg0, Transferable arg1) {
		System.out.println("lostOwnership");
		
	}
}
