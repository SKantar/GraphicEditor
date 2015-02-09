package grapheditor.actions;


/**Klasa ActionManager vodi racuna o svim akcijama i inicijalizuje ih.
 * Sadrzi get metode za sve akcije pod njenom jurizdikcijom
 * @author Kantar
 */

public class ActionManager {
	private NewProjectAction newProjectAction;
	private NewDiagramAction newDiagramAction;
	
	private DeleteAction deleteAction;
	private DeleteElementsAction deleteElementsAction;
	
	private CascadeDiagramAction cascadeDiagramAction;
	private TileHorizontallyDiagramAction tileHorizontallyDiagramAction;
	private TileVerticallyDiagramAction tileVerticallyDiagramAction;
	
	private NextDiagramAction nextDiagramAction;
	private PreviousDiagramAction previousDiagramAction;
	
	private CloseApplicationAction closeApplicationAction;
	
	private AboutAuthorAction aboutAuthorAction;
	private AboutApplicationAction aboutApplicationAction;
	
	private CloseAllDiagramsAction closeAllDiagramsAction;
	private CloseDiagramAction closeDiagramAction;
	private CloseProjectAction closeProjectAction;
	
	private RenameAction renameAction;
	
	private PCircleAction pCircleAction;
	private PRectangleAction pRectangleAction;
	private PTriangleAction pTriangleAction;
	private PHandCursorAction pHandCursorAction;
	private PStarAction pStarAction;
	private PLinkAction pLinkAction;
	
	private SelectAllAction selectAllAction;
	private SearchElementAction searchElementAction;
	
	private ElementPropertiesAction elementPropertiesAction;
	
	private SaveProjectAction saveProjectAction;
	private SaveWorkspaceAction saveWorkspaceAction;
	
	private OpenProjectAction openProjectAction;
	private OpenWorkspaceAction openWorkspaceAction;
	
	private ZoomInAction zoomInAction;
	private ZoomOutAction zoomOutAction;
	private ZoomBestFitAction zoomBestFitAction;
	private ZoomLassoAction zoomLassoAction;
	
	private EditCopyAction editCopyAction;
	private EditCutAction editCutAction;
	private EditPasteAction editPasteAction;
	
	private UndoAction undoAction;
	private RedoAction redoAction;
	
	private RotateElementLeftAction rotateElementLeftAction;
	private RotateElementRightAction rotateElementRightAction;
	
	public ActionManager() {
			newProjectAction = new NewProjectAction();
			newDiagramAction = new NewDiagramAction();
			
			deleteAction = new DeleteAction();
			deleteElementsAction = new DeleteElementsAction();
			
			cascadeDiagramAction = new CascadeDiagramAction();
			tileHorizontallyDiagramAction = new TileHorizontallyDiagramAction();
			tileVerticallyDiagramAction = new TileVerticallyDiagramAction();
			
			nextDiagramAction = new NextDiagramAction();
			previousDiagramAction = new PreviousDiagramAction();
			
			closeApplicationAction = new CloseApplicationAction();
			
			aboutAuthorAction = new AboutAuthorAction();
			aboutApplicationAction = new AboutApplicationAction();
			
			closeAllDiagramsAction = new CloseAllDiagramsAction();
			closeDiagramAction = new CloseDiagramAction();
			closeProjectAction = new CloseProjectAction();
			
			renameAction = new RenameAction();
			
			pCircleAction = new PCircleAction();
			pTriangleAction = new PTriangleAction();
			pRectangleAction = new PRectangleAction();
			pHandCursorAction = new PHandCursorAction();
			pStarAction = new PStarAction();
			pLinkAction = new PLinkAction();
			
			selectAllAction = new SelectAllAction();
			searchElementAction = new SearchElementAction();
			
			elementPropertiesAction = new ElementPropertiesAction();
			
			saveProjectAction = new SaveProjectAction();
			saveWorkspaceAction = new SaveWorkspaceAction();
			
			openProjectAction = new OpenProjectAction();
			openWorkspaceAction = new OpenWorkspaceAction();
			
			zoomInAction = new ZoomInAction();
			zoomOutAction = new ZoomOutAction();
			zoomBestFitAction = new ZoomBestFitAction();
			zoomLassoAction = new ZoomLassoAction();
			
			editCopyAction = new EditCopyAction();
			editCutAction = new EditCutAction();
			editPasteAction = new EditPasteAction();
			
			undoAction = new UndoAction();
			redoAction = new RedoAction();
			
			rotateElementLeftAction = new RotateElementLeftAction();
			rotateElementRightAction = new RotateElementRightAction();
	}

	public NewProjectAction getNewProjectAction() {
		return newProjectAction;
	}

	public NewDiagramAction getNewDiagramAction() {
		return newDiagramAction;
	}

	public DeleteAction getDeleteAction() {
		return deleteAction;
	}

	public CascadeDiagramAction getCascadeDiagramAction() {
		return cascadeDiagramAction;
	}

	public TileHorizontallyDiagramAction getTileHorizontallyDiagramAction() {
		return tileHorizontallyDiagramAction;
	}

	public TileVerticallyDiagramAction getTileVerticallyDiagramAction() {
		return tileVerticallyDiagramAction;
	}

	public NextDiagramAction getNextDiagramAction() {
		return nextDiagramAction;
	}

	public PreviousDiagramAction getPreviousDiagramAction() {
		return previousDiagramAction;
	}

	public CloseApplicationAction getCloseApplicationAction() {
		return closeApplicationAction;
	}

	public AboutAuthorAction getAboutAuthorAction() {
		return aboutAuthorAction;
	}

	public AboutApplicationAction getAboutApplicationAction() {
		return aboutApplicationAction;
	}

	public CloseAllDiagramsAction getCloseAllDiagramsAction() {
		return closeAllDiagramsAction;
	}

	public CloseDiagramAction getCloseDiagramAction() {
		return closeDiagramAction;
	}

	public CloseProjectAction getCloseProjectAction() {
		return closeProjectAction;
	}

	public RenameAction getRenameAction() {
		return renameAction;
	}

	public PCircleAction getpCircleAction() {
		return pCircleAction;
	}

	public PRectangleAction getpRectangleAction() {
		return pRectangleAction;
	}

	public PTriangleAction getpTriangleAction() {
		return pTriangleAction;
	}

	public PHandCursorAction getpHandCursorAction() {
		return pHandCursorAction;
	}
	
	public PStarAction getpStarAction() {
		return pStarAction;
	}
	
	public PLinkAction getpLinkAction() {
		return pLinkAction;
	}
	
	public SelectAllAction getSelectAllAction() {
		return selectAllAction;
	}
	
	public SearchElementAction getSearchElementAction() {
		return searchElementAction;
	}
	
	public DeleteElementsAction getDeleteElementsAction() {
		return deleteElementsAction;
	}
	
	public ElementPropertiesAction getElementPropertiesAction() {
		return elementPropertiesAction;
	}
	
	public SaveProjectAction getSaveProjectAction() {
		return saveProjectAction;
	}
	
	public SaveWorkspaceAction getSaveWorkspaceAction() {
		return saveWorkspaceAction;
	}
	
	public OpenWorkspaceAction getOpenWorkspaceAction() {
		return openWorkspaceAction;
	}
	
	public OpenProjectAction getOpenProjectAction() {
		return openProjectAction;
	}
	
	public ZoomInAction getZoomInAction() {
		return zoomInAction;
	}

	public ZoomOutAction getZoomOutAction() {
		return zoomOutAction;
	}
	
	public ZoomBestFitAction getZoomBestFitAction() {
		return zoomBestFitAction;
	}
	
	public ZoomLassoAction getZoomLassoAction() {
		return zoomLassoAction;
	}
	
	public EditCopyAction getEditCopyAction() {
		return editCopyAction;
	}
	
	public EditCutAction getEditCutAction() {
		return editCutAction;
	}
	
	public EditPasteAction getEditPasteAction() {
		return editPasteAction;
	}
	
	public UndoAction getUndoAction() {
		return undoAction;
	}
	
	public RedoAction getRedoAction() {
		return redoAction;
	}
	
	public RotateElementLeftAction getRotateElementLeftAction() {
		return rotateElementLeftAction;
	}
	
	public RotateElementRightAction getRotateElementRightAction() {
		return rotateElementRightAction;
	}
	
}
