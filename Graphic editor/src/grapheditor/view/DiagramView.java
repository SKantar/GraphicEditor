package grapheditor.view;

import grapheditor.app.AppCore;
import grapheditor.commands.CommandManager;
import grapheditor.commands.PasteCommand;
import grapheditor.controller.DiagramViewFrameListener;
import grapheditor.controller.DiagramViewMouseListener;
import grapheditor.event.UpdateEvent;
import grapheditor.event.UpdateListener;
import grapheditor.model.elements.DiagramDevice;
import grapheditor.model.elements.DiagramElement;
import grapheditor.model.elements.LinkElement;
import grapheditor.model.workspace.nodes.Diagram;
import grapheditor.model.workspace.nodes.Project;
import grapheditor.model.workspace.nodes.Workspace;
import grapheditor.state.MoveState;
import grapheditor.state.StateManager;
import grapheditor.utils.Utils.Direction;
import grapheditor.view.painters.ElementPainter;

import java.awt.Adjustable;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Dimension2D;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/**
 * Predstavlja View deo MVC arhitekture, buduÄ‡e unutraÅ¡nje klase Ä‡e
 * predstavljati Controller-a Ova klasa Ä‡e na sebe iscrtati sve komponente koje
 * se nalaze u DiagramModelu DiagramModel se dobija iz dijagrama
 * 
 * @author Igor Z.
 * 
 */

@SuppressWarnings("serial")
public class DiagramView extends JInternalFrame implements UpdateListener,
		Comparable<DiagramView>, AdjustmentListener {
	private StateManager stateManager = new StateManager(this);

	static int openFrameCount = 0;

	// sluÅ¾e nam za odreÄ‘ivanje pozicije unutraÅ¡njeg prozora
	public static final int xOffset = 30, yOffset = 30;
	public static final int defaultWidth = 400, defaultHeight = 400;
	private Diagram diagram;
	// framework nam predstavlja radnu povrsinu za dijagram

	private JPanel framework;

	private JScrollBar sbVertical;
	private JScrollBar sbHorizontal;

	// inicijalna pozicija skrol bara, vazna kod pomeranja skrol bara
	private int hScrollValue = 140;
	private int vScrollValue = 140;

	// tacka koja nam za sada sluzi za lasso
	private Point2D lastPosition = null;
	private Rectangle2D selectionRectangle = new Rectangle2D.Double();

	private CommandManager commandManager = new CommandManager();

	public enum Handle {
		SouthEast, SouthWest, NorthEast, NorthWest
	}

	static final int handleSize = 7;

	double translateX = 0;
	double translateY = 0;
	double scaling = 1;
	final static double translateFactor = 10;
	final static double scalingFactor = 1.2;

	private AffineTransform transformation = new AffineTransform();

	public DiagramView() {

		super("", true, // resizable
				true, // closable
				true, // maximizable
				true);// iconifiable
		++openFrameCount;
		setLocation(xOffset * openFrameCount, yOffset * openFrameCount);
		setIconifiable(true);
		setClosable(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		addInternalFrameListener(new DiagramViewFrameListener());
		setSize(400, 400);
		this.startSelectState();
		AppCore.getInstance().getPalette().getHandCursor().setSelected(true);
		setVisible(true);
		framework = new Framework();
		framework.setCursor(new Cursor(Cursor.HAND_CURSOR));
		framework.setBackground(Color.GRAY);
		getContentPane().add(framework, BorderLayout.CENTER);

		// postavljanje horizontalnog i vertikalnog skrol
		// bara----------------------------
		sbHorizontal = new JScrollBar(JScrollBar.HORIZONTAL, 140, 20, 0, 300);
		sbVertical = new JScrollBar(JScrollBar.VERTICAL, 140, 20, 0, 300);

		sbHorizontal.addAdjustmentListener(this);
		sbVertical.addAdjustmentListener(this);

		this.add(sbHorizontal, BorderLayout.SOUTH);
		this.add(sbVertical, BorderLayout.EAST);
		// --------------------------------------------------------------------------

		MouseController mouseController = new MouseController();
		framework.addMouseListener(mouseController);
		framework.addMouseMotionListener(mouseController);
		framework.addMouseWheelListener(mouseController);
		framework.addMouseListener(new DiagramViewMouseListener());
	}

	public void setDiagram(Diagram diagram) {
		this.diagram = diagram;
		this.setName(diagram.toString());
		this.diagram.getDiagramModel().addUpdateListener(this);
		this.diagram.getSelectionModel().addUpdateListener(this);
		setTitle(diagram.toString() + " "
				+ ((Project) diagram.getParent()).toString());
	}

	public Diagram getDiagram() {
		return diagram;
	}

	public static int getOpenFrameCount() {
		return openFrameCount;
	}

	public CommandManager getCommandManager() {
		return commandManager;
	}
	
	public int gethScrollValue() {
		return hScrollValue;
	}
	
	public int getvScrollValue() {
		return vScrollValue;
	}
	
	public JScrollBar getSbHorizontal() {
		return sbHorizontal;
	}
	
	public JScrollBar getSbVertical() {
		return sbVertical;
	}

	// podrska za rad sa StateManagerom
	// ----------------------------------------------

	public void startCircleState() {
		stateManager.setCircleState();
	}

	public void startSelectState() {
		stateManager.setSelectState();
	}

	public void startLinkState() {
		stateManager.setLinkState();
	}

	public void startRectangleState() {
		stateManager.setRectangleState();
	}

	public void startTriangleState() {
		stateManager.setTriangleState();
	}

	public void startStarState() {
		stateManager.setStarState();
	}

	public void startLassoState() {
		stateManager.setLassoState();
	}
	
	public void startResizeState(){
		stateManager.setResizeState();
	}
	
	public void startMoveState(){
		stateManager.setMoveState();
	}
	
	public void startLassoZoomState(){
		stateManager.setLassoZoomState();
	}

	public StateManager getStateManager() {
		return stateManager;
	}
	
	public JPanel getFramework() {
		return framework;
	}

	public static void setOpenFrameCount(int openFrameCount) {
		DiagramView.openFrameCount = openFrameCount;
	}

	private class MouseController extends MouseAdapter implements
			MouseMotionListener {

		public void mousePressed(MouseEvent e) {

			lastPosition = e.getPoint();
			transformToUserSpace(lastPosition);
			stateManager.getCurrentState().mousePressed(e);
		}

		public void mouseReleased(MouseEvent e) {
			stateManager.getCurrentState().mouseReleased(e);
		}

		public void mouseDragged(MouseEvent e) {
			AppCore.getInstance()
					.getStatusBar()
					.setPosition(
							"x : " + e.getPoint().x + " - y : "
									+ e.getPoint().y);
			stateManager.getCurrentState().mouseDragged(e);
		}

		public void mouseMoved(MouseEvent e) {
			AppCore.getInstance()
					.getStatusBar()
					.setPosition(
							"x : " + e.getPoint().x + " - y : "
									+ e.getPoint().y);
			stateManager.getCurrentState().mouseMoved(e);
		}

		/*public void mouseWheelMoved(MouseWheelEvent e) {
			if((e.getModifiers()&MouseWheelEvent.CTRL_MASK) != 0){ // Ako pritisnut Ctrl
				// Radimo zoom u tački (diskretni zoom)
				// Prvo je potrebno da odredimo novo skaliranje 
				double newScaling = scaling;
				if(e.getWheelRotation()>0)
					newScaling *= (double)e.getWheelRotation()*scalingFactor;
				else
					newScaling /= -(double)e.getWheelRotation()*scalingFactor;
				// Zatim je potrebno da skaliranje održimo u intervalu [0.2, 5]
				if(newScaling < 0.2)
					newScaling = 0.2;
				if(newScaling > 5)
					newScaling = 5;
				
				/* newScaling je novi parametar skaliranja (članovi m00 i m11 transformacione matrice)
				 * Prilikom skaliranja dolazi do pomeranja userspace koordinata na kojima se nalazi pokazivač miša.
				 * Da bi dobili ispravan Point zoom moramo izvršiti translaciju tako da poništimo "smicanje" usled skaliranja 
				 * tj. moramo postići da se userspace koordinate miša ne promene.
				 *
				
				Point2D oldPosition = e.getPoint();
				transformToUserSpace(oldPosition);
				
				scaling = newScaling;
				setupTransformation();
				
				Point2D newPosition = e.getPoint();
				transformToUserSpace(newPosition);
				
				translateX +=  newPosition.getX() - oldPosition.getX();
				translateY += newPosition.getY() - oldPosition.getY();
				
				sbVertical.setVisibleAmount((int) (20/scaling));
				sbHorizontal.setVisibleAmount((int) (20/scaling));
				
				setupTransformation();

			}else if((e.getModifiers()&MouseWheelEvent.SHIFT_MASK) != 0){  // Ako je pritisnut Shift
					translateX += (double)e.getWheelRotation() * translateFactor/scaling;
			}else{ // u ostalim slučajevima vršimo skrolovanje po Y osi
				translateY += (double)e.getWheelRotation() * translateFactor/scaling;
			}
			
			setupTransformation();
			repaint();
		}*/
		
		public void mouseWheelMoved(MouseWheelEvent e) {
			if((e.getModifiers()&MouseWheelEvent.CTRL_MASK) != 0){ // Ako pritisnut Ctrl
				// Radimo zoom u tački (diskretni zoom)
				// Prvo je potrebno da odredimo novo skaliranje 
				double newScaling = scaling;
				if(e.getWheelRotation()>0)
					newScaling *= (double)e.getWheelRotation()*scalingFactor;
				else
					newScaling /= -(double)e.getWheelRotation()*scalingFactor;
				// Zatim je potrebno da skaliranje održimo u intervalu [0.2, 5]
				if(newScaling < 0.2)
					newScaling = 0.2;
				if(newScaling > 5)
					newScaling = 5;
				
				/* newScaling je novi parametar skaliranja (članovi m00 i m11 transformacione matrice)
				 * Prilikom skaliranja dolazi do pomeranja userspace koordinata na kojima se nalazi pokazivač miša.
				 * Da bi dobili ispravan Point zoom moramo izvršiti translaciju tako da poništimo "smicanje" usled skaliranja 
				 * tj. moramo postići da se userspace koordinate miša ne promene.
				 */
				
				Point2D oldPosition = e.getPoint();
				transformToUserSpace(oldPosition);
				
				scaling = newScaling;
				setupTransformation();
				
				Point2D newPosition = e.getPoint();
				transformToUserSpace(newPosition);
				
				translateX +=  newPosition.getX() - oldPosition.getX();
				translateY += newPosition.getY() - oldPosition.getY();
				
				sbVertical.setVisibleAmount((int) (20/scaling));
				sbHorizontal.setVisibleAmount((int) (20/scaling));
				
				setupTransformation();
				
			}else if(e.isShiftDown()){  // Ako je pritisnut Shift
				int valueScroll=((DiagramView) AppCore.getInstance().getDesktop().getSelectedFrame()).getSbHorizontal().getValue();
				if(((valueScroll >= -280  ) && (e.getWheelRotation()>0)) || ((e.getWheelRotation()<0)&& (valueScroll<=280))){										
					((DiagramView) AppCore.getInstance().getDesktop().getSelectedFrame()).getSbHorizontal().setValue(
							((DiagramView) AppCore.getInstance().getDesktop().getSelectedFrame()).getSbHorizontal().getValue()+
							((DiagramView) AppCore.getInstance().getDesktop().getSelectedFrame()).getSbHorizontal().getUnitIncrement() * 
							e.getWheelRotation());
					valueScroll=((DiagramView) AppCore.getInstance().getDesktop().getSelectedFrame()).getSbHorizontal().getValue();
					hScrollValue=valueScroll;
				}
			}
			else{ // u ostalim slucajevima vrsimo skrolovanje po Y osi
				int valueScroll=((DiagramView) AppCore.getInstance().getDesktop().getSelectedFrame()).getSbVertical().getValue();
				if(((valueScroll >= -280  ) && (e.getWheelRotation()>0)) || ((e.getWheelRotation()<0)&& (valueScroll<=280))){										
					((DiagramView) AppCore.getInstance().getDesktop().getSelectedFrame()).getSbVertical().setValue(
							((DiagramView) AppCore.getInstance().getDesktop().getSelectedFrame()).getSbVertical().getValue()+
							((DiagramView) AppCore.getInstance().getDesktop().getSelectedFrame()).getSbVertical().getUnitIncrement() * 
							e.getWheelRotation());
					valueScroll=((DiagramView) AppCore.getInstance().getDesktop().getSelectedFrame()).getSbVertical().getValue();
					vScrollValue=valueScroll;
				}
			}
			
			repaint();
		}

	}

	public void transformToUserSpace(Point2D deviceSpace) {
		try {
			transformation.inverseTransform(deviceSpace, deviceSpace);
		} catch (NoninvertibleTransformException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public double transformLineToUserSpaceX(double deviceSpace) {
		return deviceSpace * transformation.getScaleX()
				+ transformation.getTranslateX();
	}

	public double transformLineToUserSpaceY(double deviceSpace) {
		return deviceSpace * transformation.getScaleY()
				+ transformation.getTranslateY();
	}

	public void updatePerformed(UpdateEvent e) {

		/*
		 * if (e == null || e.getR() == null) { framework.repaint(); } else {
		 * 
		 * //transformToUserSpace(e.getR().getLocation()); Rectangle r =
		 * e.getR();
		 * 
		 * //System.out.println("asdwadwadwadw" + r.getX() + " - " + r.getY());
		 * //transformToUserSpace(framework.getLocation());
		 * framework.repaint(r); }
		 */
		repaint();
	}

	/**
	 * Podešava parametre transformacione matrice
	 */
	private void setupTransformation() {

		transformation.setToIdentity();
		// Zumiranje

		transformation.scale(scaling, scaling);
		// Skrolovanje
		transformation.translate(translateX, translateY);

	}

	public class Framework extends JPanel {
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

			Graphics2D g2 = (Graphics2D) g;
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
					0.8f));

			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);

			g2.transform(transformation);

			Iterator<DiagramElement> it = diagram.getDiagramModel()
					.getDeviceIterator();
			while (it.hasNext()) {
				DiagramElement d = (DiagramElement) it.next();
				ElementPainter paint = (ElementPainter) d.getPainter();
				paint.paint(g2, d);
			}

			paintSelectionHandles(g2);

			// iscrtavanje pravougaonika za lasso
			g2.setColor(Color.BLACK);
			g2.setStroke(new BasicStroke((float) 1, BasicStroke.CAP_SQUARE,
					BasicStroke.JOIN_BEVEL, 1f, new float[] { (float) 3,
							(float) 6 }, 0));
			g2.draw(selectionRectangle);
		}
		
		public void stop(){
			if(DiagramView.this.getStateManager().getCurrentState().getThread() != null)
				DiagramView.this.getStateManager().getCurrentState().getThread().setScroll(false);;
		}
		
		public void start(){
			
		}

	}

	@Override
	public int compareTo(DiagramView o) {
		if (this.getDiagram().getId() < o.getDiagram().getId())
			return -1;
		if (this.getDiagram().getId() > o.getDiagram().getId())
			return 1;
		return 0;
	}
	
	public double sizeToUserSpace(double deviceSize){
		return deviceSize/transformation.getScaleX();
	}

	/**
	 * Iscrtavanje selekcionih hendlova oko selektovanog elementa
	 */
	private void paintSelectionHandles(Graphics2D g2) {

		Iterator<DiagramElement> it = diagram.getSelectionModel()
				.getSelectionListIterator();
		while (it.hasNext()) {
			DiagramElement element = it.next();
			if (element instanceof DiagramDevice) {
				DiagramDevice device = (DiagramDevice) element;
				// Iscrtavanje pravougaonika sa isprekidanom linijom
				
				if(device.getRotation()==0 || device.getRotation() == Math.PI || device.getRotation()==-Math.PI){
					g2.setStroke(new BasicStroke((float)sizeToUserSpace(1), BasicStroke.CAP_SQUARE, 
								BasicStroke.JOIN_BEVEL, 1f, new float[]{(float)sizeToUserSpace(3), (float)sizeToUserSpace(6)}, 0 ));
					g2.setPaint(Color.BLACK);
					g2.drawRect((int)device.getPosition().getX(), (int)device.getPosition().getY(),
							(int)device.getSize().getWidth(), (int)device.getSize().getHeight());
										
				}
				else{
					g2.setStroke(new BasicStroke((float)sizeToUserSpace(1), BasicStroke.CAP_SQUARE, 
							BasicStroke.JOIN_BEVEL, 1f, new float[]{(float)sizeToUserSpace(3), (float)sizeToUserSpace(6)}, 0 ));
					g2.setPaint(Color.BLACK);
					double razlika=(device.getSize().getWidth()-device.getSize().getHeight())/2;
					g2.drawRect((int)(device.getPosition().getX()+razlika),
								(int)(device.getPosition().getY()-razlika),
								(int)device.getSize().getHeight(), (int)device.getSize().getWidth());					
				}
				for (Handle e : Handle.values()) {
					paintSelectionHandle(
							g2,
							getHandlePoint(device.getPosition(),
									device.getSize(), e, device));
				}

			} else {
				// isrtavanje handlova za link
				LinkElement link = (LinkElement) element;

				Point2D bp = null;
				bp = link.getOutput().getPosition();
				g2.setPaint(Color.BLACK);
				g2.setStroke(new BasicStroke((float) 2, BasicStroke.CAP_SQUARE,
						BasicStroke.JOIN_BEVEL));

				g2.drawRect((int) bp.getX() - handleSize / 2, (int) bp.getY()
						- handleSize / 2, handleSize, handleSize);

				Iterator<Point2D> itp = link.getPointsIterator();
				while (itp.hasNext()) {
					bp = (Point2D) itp.next();
					g2.drawRect((int) bp.getX() - handleSize / 2,
							(int) bp.getY() - handleSize / 2, handleSize,
							handleSize);

				}
				bp = link.getInput().getPosition();
				g2.drawRect((int) bp.getX() - handleSize / 2, (int) bp.getY()
						- handleSize / 2, handleSize, handleSize);
			}

		}
	}

	private void paintSelectionHandle(Graphics2D g2, Point2D position) {
		double size = handleSize;
		g2.fill(new Rectangle2D.Double(position.getX() - size / 2, position
				.getY() - size / 2, size, size));
	}

	private Point2D getHandlePoint(Point2D topLeft, Dimension2D size,Handle handlePosition, DiagramDevice device) {
		double x=0, y=0;
		double razlika=(device.getSize().getWidth()-device.getSize().getHeight())/2;
		if(device.getRotation() == 0 || device.getRotation()==Math.PI || device.getRotation()==-Math.PI){
			// Ako su gornji hendlovi
			if(handlePosition == Handle.NorthWest || handlePosition == Handle.NorthEast){
				y = topLeft.getY();
			}
			//Ako su donji
			if(handlePosition == Handle.SouthWest || handlePosition == Handle.SouthEast){
				y = topLeft.getY() + size.getHeight();
			}
			// OdreÄ‘ivanje x koordinate		
			// Ako su levi
			if(handlePosition == Handle.NorthWest || handlePosition == Handle.SouthWest){
				x = topLeft.getX();
			}		
			// ako su desni
			if(handlePosition == Handle.NorthEast || handlePosition == Handle.SouthEast){
				x = topLeft.getX() + size.getWidth();
			}	
		}	
		else{
			if(handlePosition == Handle.NorthWest || handlePosition == Handle.NorthEast){
				y = topLeft.getY()-razlika;
			}
			//Ako su donji
			if(handlePosition == Handle.SouthWest || handlePosition == Handle.SouthEast){
				y = topLeft.getY() + size.getWidth()-razlika;
			}
			// Ako su levi
			if(handlePosition == Handle.NorthWest || handlePosition == Handle.SouthWest){
				x = topLeft.getX()+razlika;
			}		
			// ako su desni
			if(handlePosition == Handle.NorthEast || handlePosition == Handle.SouthEast){
				x = topLeft.getX() + size.getHeight()+razlika;
			}	
		}
		return new Point2D.Double(x,y);		
	}

	/**
	 * Na osnovu hendla iznad koga se nalazi postavlja kursor
	 */
	public void setMouseCursor(Point2D point) {

		Handle handle = getDeviceAndHandleForPoint(point);

		if (handle != null) {
			Cursor cursor = null;

			switch (handle) {
			case SouthEast:
				cursor = Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR);
				break;
			case NorthWest:
				cursor = Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR);
				break;
			case SouthWest:
				cursor = Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR);
				break;
			case NorthEast:
				cursor = Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR);
				break;
			}
			framework.setCursor(cursor);
		} else
			framework.setCursor(Cursor.getDefaultCursor());
	}

	/**
	 * Određuje handl i uređaj koji se nalazi na zadatoj lokaciji
	 * 
	 * @param point
	 *            Ulazni parametar koji određuje lokaciju
	 * @return Hendl za zadatu poziciju. Ukoliko je null tada je device
	 *         nedefinisan.
	 */
	public Handle getDeviceAndHandleForPoint(Point2D point) {
		DiagramElement element;

		Iterator<DiagramElement> it = diagram.getSelectionModel()
				.getSelectionListIterator();
		while (it.hasNext()) {
			element = it.next();
			if(getHandleForPoint(element, point) == null) continue;
			return getHandleForPoint(element, point);
		}
		return null;
	}
	
	public DiagramDevice getDeviceForHandlePoint(Point2D point) {
		DiagramElement element;
		DiagramDevice device;

		Iterator<DiagramElement> it = diagram.getSelectionModel()
				.getSelectionListIterator();
		while (it.hasNext()) {
			element = it.next();
			if(element instanceof DiagramDevice){
				device = (DiagramDevice)element;
				if(getHandleForPoint(element, point)!=null)
					return device;
			}
		}
		return null;
	}

	/**
	 * Za zadatu tačku i uređaj vraća hendl.
	 * 
	 * @param device
	 * @param point
	 * @return Hendl ukoliko je "pogođen", u suprotnom vraća null
	 */
	private Handle getHandleForPoint(DiagramElement element, Point2D point) {
		for (Handle h : Handle.values()) {
			if (isPointInHandle(element, point, h)) {
				return h;
			}
		}
		return null;
	}

	/**
	 * Za zadati uređaj, tačku i hendl određuje da li je tačka unutar hendla
	 * 
	 * @param device
	 * @param point
	 * @param handle
	 * @return
	 */
	private boolean isPointInHandle(DiagramElement element, Point2D point,
			Handle handle) {
		if (element instanceof DiagramDevice) {
			DiagramDevice device = (DiagramDevice) element;
			Point2D handleCenter = getHandlePoint(device.getPosition(),device.getSize(), handle, device);
			return ((Math.abs(point.getX() - handleCenter.getX()) <= (double) handleSize ) && (Math
					.abs(point.getY() - handleCenter.getY()) <= (double) handleSize));
		} else
			return false;
	}

	public Point2D getLastPosition() {
		return lastPosition;
	}

	public void setLastPosition(Point2D lastPosition) {
		this.lastPosition = lastPosition;
	}

	public Rectangle2D getSelectionRectangle() {
		return selectionRectangle;
	}

	public void setSelectionRectangle(Rectangle2D selectionRectangle) {
		this.selectionRectangle = selectionRectangle;
	}

	public TreePath getPath(DiagramElement de) {
		DefaultTreeModel dtm = AppCore.getInstance().getWorkspaceModel();
		TreePath res = new TreePath(dtm.getRoot());
		Workspace root = (Workspace) dtm.getRoot();
		for (int i = 0; i < root.getChildCount(); i++) {
			if (((Project) root.getChildAt(i)).getIndex(diagram) != -1)
				res = res.pathByAddingChild(((Project) root.getChildAt(i)));
		}
		res = res.pathByAddingChild(diagram);
		for (int i = 0; i < diagram.getChildCount(); i++) {
			TreeNode node = diagram.getChildAt(i);
			for (int j = 0; j < node.getChildCount(); j++) {
				if (node.getChildAt(j) == de) {
					res = res.pathByAddingChild(node);
					res = res.pathByAddingChild(de);
					break;
				}
			}
		}
		return res;
	}

	public void adjustmentValueChanged(AdjustmentEvent e) {
		// TODO Auto-generated method stub
		
		//Nakon određivanja vrednosti translateX i translateY koje 
		//zavise potrebno je setovati novu transformaciju
		//uzeti u obzir koji je skrol bar pomeran
		//i u zavisnosti od prethodne pozicije datog skrol bara
		//i trenutnog skaliranja izvrsiti transformaciju translacije
		//nove 
		if(e.getAdjustable().getOrientation()==Adjustable.HORIZONTAL){
			if(hScrollValue<e.getValue()){
				translateX+=(double)((e.getValue()-hScrollValue)*(-translateFactor))/transformation.getScaleX();
				//transformation.translate((double)((e.getValue()-hScrollValue)*(-translateFactor))/transformation.getScaleX(), 0);

			}
			else{
				translateX+=(double)((e.getValue()-hScrollValue)*(-translateFactor))/transformation.getScaleX();
				//translateX+=(double)((hScrollValue-e.getValue())*(-translateFactor))/transformation.getScaleX();
				//transformation.translate((double)((hScrollValue-e.getValue())*(translateFactor))/transformation.getScaleX(), 0);			
			}
			hScrollValue=e.getValue();
			
		}
		else{
			if(vScrollValue<e.getValue()){			
				translateY+=(double)((e.getValue()-vScrollValue)*(-translateFactor))/transformation.getScaleX();
				//transformation.translate(0,(double)((e.getValue()-vScrollValue)*(-translateFactor))/transformation.getScaleX());				
			}
			else{
				translateY+=(double)((e.getValue()-vScrollValue)*(-translateFactor))/transformation.getScaleX();
				//translateY+=(double)((vScrollValue-e.getValue())*(-translateFactor))/transformation.getScaleX();
				//transformation.translate(0,(double)((vScrollValue-e.getValue())*(translateFactor))/transformation.getScaleX());			
			}
			vScrollValue=e.getValue();
		}
		setupTransformation();
		repaint();

	}

	public void zoomIn() {
		// Prvo je potrebno da odredimo novo skaliranje
		double newScaling = scaling;
		// Zatim je potrebno da skaliranje održimo u intervalu [0.2, 5]
		newScaling *= scalingFactor;

		if (newScaling < 0.2) {
			newScaling = 0.2;

		} else if (newScaling > 5) {
			newScaling = 5;
		}

		/*
		 * newScaling je novi parametar skaliranja (članovi m00 i m11
		 * transformacione matrice) Prilikom skaliranja dolazi do pomeranja
		 * userspace koordinata na kojima se nalazi pokazivač miša. Da bi dobili
		 * ispravan Point zoom moramo izvršiti translaciju tako da poništimo
		 * "smicanje" usled skaliranja tj. moramo postići da se userspace
		 * koordinate miša ne promene.
		 */

		Point2D oldPosition = new Point2D.Double(getWidth() / 2,
				getHeight() / 2);
		transformToUserSpace(oldPosition);

		scaling = newScaling;
		setupTransformation();

		Point2D newPosition = new Point2D.Double(getWidth() / 2,
				getHeight() / 2);
		transformToUserSpace(newPosition);

		translateX += newPosition.getX() - oldPosition.getX();
		translateY += newPosition.getY() - oldPosition.getY();

		sbVertical.setVisibleAmount((int) (20 / scaling));
		sbHorizontal.setVisibleAmount((int) (20 / scaling));

		setupTransformation();

	}

	public void zoomOut() {
		// Prvo je potrebno da odredimo novo skaliranje
		double newScaling = scaling;
		// Zatim je potrebno da skaliranje održimo u intervalu [0.2, 5]
		newScaling /= scalingFactor;

		if (newScaling < 0.2) {
			newScaling = 0.2;

		} else if (newScaling > 5) {
			newScaling = 5;
		}

		/*
		 * newScaling je novi parametar skaliranja (članovi m00 i m11
		 * transformacione matrice) Prilikom skaliranja dolazi do pomeranja
		 * userspace koordinata na kojima se nalazi pokazivač miša. Da bi dobili
		 * ispravan Point zoom moramo izvršiti translaciju tako da poništimo
		 * "smicanje" usled skaliranja tj. moramo postići da se userspace
		 * koordinate miša ne promene.
		 */

		Point2D oldPosition = new Point2D.Double(getWidth() / 2,
				getHeight() / 2);
		transformToUserSpace(oldPosition);

		scaling = newScaling;
		setupTransformation();

		Point2D newPosition = new Point2D.Double(getWidth() / 2,
				getHeight() / 2);
		transformToUserSpace(newPosition);

		translateX += newPosition.getX() - oldPosition.getX();
		translateY += newPosition.getY() - oldPosition.getY();
		sbVertical.setVisibleAmount((int) (20 / scaling));
		sbHorizontal.setVisibleAmount((int) (20 / scaling));

		setupTransformation();

	}

	public void bestFitZoom() {
		int margine = 10;
		double minX = 99999, minY = 999999, maxX = 0, maxY = 0;
		Iterator<DiagramElement> it = getDiagram().getDiagramModel()
				.getDeviceIterator();
		if (!it.hasNext())
			return;
		while (it.hasNext()) {
			DiagramElement element = (DiagramElement) it.next();

			if (element instanceof DiagramDevice) {
				DiagramDevice device = (DiagramDevice) element;
				if (device.getPosition().getX() < minX)
					minX = device.getPosition().getX();
				if (device.getPosition().getY() < minY)
					minY = device.getPosition().getY();
				if (device.getPosition().getX() + device.getSize().width > maxX)
					maxX = device.getPosition().getX() + device.getSize().width;
				if (device.getPosition().getY() + device.getSize().height > maxY)
					maxY = device.getPosition().getY()
							+ device.getSize().height;
			} else if (element instanceof LinkElement) {
				LinkElement link = (LinkElement) element;
				if (link.getMaxX() > maxX)
					maxX = link.getMaxX();
				if (link.getMaxY() > maxY)
					maxY = link.getMaxY();
				if (link.getMinX() < minX)
					minX = link.getMinX();
				if (link.getMinY() < minY)
					minY = link.getMinY();
			}

		}
		minX -= margine;
		minY -= margine;
		maxX += margine;
		maxY += margine;

		areaZoom(minX, maxX, minY, maxY);
	}

	/**
	 * Metoda radi zoom nad oznacenim regionom
	 * 
	 * @param minX
	 *            - leva granica
	 * @param maxX
	 *            - desna granica
	 * @param minY
	 *            - gornja granica
	 * @param maxY
	 *            - donja granica
	 */
	public void areaZoom(double minX, double maxX, double minY, double maxY) {
		double maxXX = transformLineToUserSpaceX(maxX);
		double minXX = transformLineToUserSpaceX(minX);
		double maxYY = transformLineToUserSpaceY(maxY);
		double minYY = transformLineToUserSpaceY(minY);

		double regionWidth = maxXX - minXX;
		double regionHeight = maxYY - minYY;

		int panelWidth = this.framework.getWidth();
		int panelHeight = this.framework.getHeight();

		double newScaling = this.transformation.getScaleX();

		if ((regionWidth != 0.0) && (regionHeight != 0.0)) {

			double scaleX = panelWidth / regionWidth;
			double scaleY = panelHeight / regionHeight;

			if (scaleX < scaleY)
				newScaling *= scaleX;
			else
				newScaling *= scaleY;

			if (newScaling < 0.2)
				newScaling = 0.2;
			if (newScaling > 5.0)
				newScaling = 5.0;

			this.transformation.setToScale(newScaling, newScaling);
		}

		maxXX = transformLineToUserSpaceX(maxX);
		minXX = transformLineToUserSpaceX(minX);
		maxYY = transformLineToUserSpaceY(maxY);
		minYY = transformLineToUserSpaceY(minY);

		regionWidth = maxXX - minXX;
		regionHeight = maxYY - minYY;

		transformation.translate(
				(-minXX + (panelWidth - regionWidth) / 2.0)
						/ transformation.getScaleX(),
				(-minYY + (panelHeight - regionHeight) / 2.0)
						/ transformation.getScaleY());
		scaling = newScaling;
		translateX = ((-minXX + (panelWidth - regionWidth) / 2.0) / transformation
				.getScaleX());
		translateY = ((-minYY + (panelHeight - regionHeight) / 2.0) / transformation
				.getScaleY());
		sbVertical.setVisibleAmount((int) (20 / scaling));
		sbHorizontal.setVisibleAmount((int) (20 / scaling));
		repaint();
	}

	public void paste() {
		getCommandManager().addCommand(new PasteCommand(getDiagram()));
	}
	
	public void autoScroll(Direction direction) {

		DiagramView diagramView = this;
		if ((diagramView.getStateManager().getCurrentState() instanceof MoveState)) {
			MoveState moveState = (MoveState) diagramView.getStateManager().getCurrentState();
			moveState.mouseDragged(moveState.getE());
		}
		switch (direction) {
		case Left: {
			int valueScroll = sbHorizontal.getValue();
			if (valueScroll >= sbHorizontal.getMinimum()) {
				sbHorizontal.setValue(sbHorizontal.getValue() - 2);
				valueScroll = sbHorizontal.getValue();
				hScrollValue = valueScroll;
			}
			break;
		}
		case Up: {
			int valueScroll = sbVertical.getValue();
			if (valueScroll <= sbVertical.getMaximum()) {
				sbVertical.setValue(sbVertical.getValue() + 2);
				valueScroll = sbVertical.getValue();
				vScrollValue = valueScroll;
			}
			break;
		}
		case Right: {
			int valueScroll = sbHorizontal.getValue();
			if (valueScroll <= sbHorizontal.getMaximum()) {
				sbHorizontal.setValue(sbHorizontal.getValue() + 2);
				valueScroll = sbHorizontal.getValue();
				hScrollValue = valueScroll;
			}
			break;
		}
		case Down: {
			int valueScroll = sbVertical.getValue();
			if (valueScroll >= sbVertical.getMinimum()) {
				sbVertical.setValue(sbVertical.getValue() - 2);
				valueScroll = sbVertical.getValue();
				vScrollValue = valueScroll;
			}
			break;

		}

		}
	}

}
