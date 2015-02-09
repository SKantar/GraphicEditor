package grapheditor.model.elements;

import grapheditor.view.painters.LinkPainter;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;

@SuppressWarnings("serial")
public class LinkElement extends DiagramElement {
	
	private int minX,minY,maxX,maxY;
	private int minCurX, minCurY, maxCurX, maxCurY;

	protected DiagramDevice startDevice;
	protected InputOutputElement output;
	protected int outM;
	
	
	protected DiagramDevice endDevice;
	protected InputOutputElement input;
	protected int inM;
	private boolean finished;
	
	
	protected ArrayList<Point2D> points=new ArrayList<Point2D>();
	
	public LinkElement(DiagramDevice startDevice, InputOutputElement input,
			Stroke stroke, Paint paint, Color strokeColor) {
		super(stroke, paint, strokeColor);
		finished = false;
		this.startDevice = startDevice;
		this.output = input;
		this.outM = this.startDevice.outM(input);

		minX = (int)output.getPosition().getX();
		maxX = (int)output.getPosition().getX();
		minY = (int)output.getPosition().getY();
		maxY = (int)output.getPosition().getY();
		minCurX = (int)output.getPosition().getX();
		maxCurX = (int)output.getPosition().getX();
		minCurY = (int)output.getPosition().getY();
		maxCurY = (int)output.getPosition().getY();
		
		addPoint(new Point2D.Double(output.getPosition().getX()+5, output
				.getPosition().getY()));
		addPoint(new Point2D.Double(output.getPosition().getX()+5, output
				.getPosition().getY()));
		addPoint(new Point2D.Double(output.getPosition().getX()+5, output
				.getPosition().getY()));
		
		
		elementPainter = new LinkPainter(this);

	}
	
	//@SuppressWarnings("unchecked")
	public LinkElement(LinkElement link) {
		super(link);
		//this.points=(ArrayList<Point2D>) link.points.clone();
		this.points = new ArrayList<>();
		for (int i = 0; i < link.points.size(); i++) {
			this.points.add((Point2D) link.points.get(i).clone());
		}
		this.finished = link.finished;
		this.startDevice = (DiagramDevice) link.startDevice.clone();
		this.endDevice = (DiagramDevice) link.endDevice.clone();
		this.inM = link.endDevice.inM((InputOutputElement) link.input);
		this.outM = link.startDevice.outM((InputOutputElement)link.output);

		this.output = (InputOutputElement) link.output.clone();
		this.input = (InputOutputElement) link.input.clone();
		//elementPainter = new LinkPainter(this);
	}
	
	public static DiagramElement createDefault(DiagramDevice startDevice,
			                                   InputOutputElement input, 
			                                   int elemNo){
        Paint fill = Color.WHITE;
        LinkElement or=new LinkElement(startDevice, 
        		                       input,
        		                       new BasicStroke((float)(2), BasicStroke.CAP_SQUARE,BasicStroke.JOIN_BEVEL ),
        		                       fill,
        		                       Color.BLUE);
        or.setName("Link " + elemNo);
		//System.out.println(startDevice.getName() + input.getName());
		return or;
	}	
	
   public void addPoint(Point2D p){
	   points.add(p);
	   solveMinMaxFin(p);
   }
	public Iterator<Point2D> getPointsIterator(){
		return points.iterator();
	}
	
	public void setPainter(DiagramElement link){
		elementPainter=new LinkPainter(link);
		
	}

	public InputOutputElement getInput() {
		return input;
	}

	public void setStartDevice(DiagramDevice startDevice) {
		this.startDevice = startDevice;
	}
	public DiagramDevice getStartDevice() {
		return startDevice;
	}

	public void setEndDevice(DiagramDevice endDevice) {
		this.endDevice = endDevice;
	}

	public DiagramDevice getEndDevice() {
		return endDevice;
	}
	
	public void setOutput(InputOutputElement output) {
		this.output = output;
		//this.outM = this.startDevice.outM(output);
		
	}

	public void setInput(InputOutputElement input) {
		this.input = input;
		//this.inM = this.endDevice.inM(input);
	}

	public InputOutputElement getOutput() {
		return output;
	}
	
	public Point2D getLastPoint() {
		return points.get(points.size() - 1);
	}
	
	public Point2D getSecondLastPoint(){
		return points.get(points.size() - 2);
	}

	public Point2D getThirdLastPoint(){
		return points.get(points.size() - 3);
	}
	
	public void removeLastPoint(){
		points.remove(points.size()-1);
	}
	
	public void insertAt(int index, Point2D element){
		points.set(index, element);
	}
	
	public Point2D getPointAt(int index) {
		return points.get(index);
	}
	
	public boolean isFinished() {
		return finished;
	}
	
	public void setFinished(boolean finished) {
		this.finished = finished;
	}
	
	public void setFree() {
		input.setFree(true);
		output.setFree(true);
	}

	public int getPointsSize(){
		return points.size();
	}

	public void removePointAt(int index){
		points.remove(index);
	}
	
	
	@Override
	public Rectangle getRepaintBounds() {
		int minx, miny,maxx, maxy;
		if(!finished){
			if(minCurX < minX ) minx = minCurX;
			else minx = minX;
			if(minCurY < minY ) miny = minCurY;
			else miny = minY;
			if(maxCurX > maxX) maxx = maxCurX;
			else maxx = maxX;
			if(maxCurY > maxY) maxy = maxCurY;
			else maxy = maxY;

			//System.out.println("ahahha");
		} else{
			maxx = maxX;
			maxy = maxY;
			minx = minX;
			miny = minY;
			if(output.getPosition().getX() < minx) minx = (int) output.getPosition().getX();
			if(output.getPosition().getY() < miny) miny = (int) output.getPosition().getY();
			if(output.getPosition().getX() > maxx) maxx = (int) output.getPosition().getX();
			if(output.getPosition().getY() > maxy) maxy = (int) output.getPosition().getY();
			
			if(input.getPosition().getX() < minx) minx = (int) input.getPosition().getX();
			if(input.getPosition().getY() < miny) miny = (int) input.getPosition().getY();
			if(input.getPosition().getX() > maxx) maxx = (int) input.getPosition().getX();
			if(input.getPosition().getY() > maxy) maxy = (int) input.getPosition().getY();
		}
		
		return new Rectangle(minx-20, miny-20, maxx - minx + 40, maxy - miny + 40);
	}
	
	public void solveMinMaxCurr(Point2D p){
		   if(p.getX() < minCurX) minCurX = (int)p.getX();
		   if(p.getY() < minCurY) minCurY = (int)p.getY();
		   if(p.getX() > maxCurX) maxCurX = (int)p.getX();
		   if(p.getY() > maxCurY) maxCurY = (int)p.getY();
	}
	
	public void solveMinMaxFin(Point2D p){
		   if(p.getX() < minX) minX = (int)p.getX();
		   if(p.getY() < minY) minY = (int)p.getY();
		   if(p.getX() > maxX) maxX = (int)p.getX();
		   if(p.getY() > maxY) maxY = (int)p.getY();
	}
	
	public void solveMinMaxTranslate(){
		minX = 999999;
		maxX = 0;
		minY = 999999;
		maxY = 0;
		
		for(Point2D p : points){
			   if(p.getX() < minX) minX = (int)p.getX();
			   if(p.getY() < minY) minY = (int)p.getY();
			   if(p.getX() > maxX) maxX = (int)p.getX();
			   if(p.getY() > maxY) maxY = (int)p.getY();
		}
	}
	
	public void setMaxX(int maxX) {
		this.maxX = maxX;
	}
	
	public void setMaxY(int maxY) {
		this.maxY = maxY;
	}
	
	public void setMinX(int minX) {
		this.minX = minX;
	}
	
	public void setMinY(int minY) {
		this.minY = minY;
	}

	public int getMinX() {
		return minX;
	}

	public int getMinY() {
		return minY;
	}

	public int getMaxX() {
		return maxX;
	}

	public int getMaxY() {
		return maxY;
	}
	
	public ArrayList<Point2D> getPoints() {
		return points;
	}

	public int getInM() {
		return inM;
	}
	
	public int getOutM() {
		return outM;
	}
	@Override
	public DiagramElement clone() {
		// TODO Auto-generated method stub
		return new LinkElement(this);
	}
	
	
	
	
   
}
