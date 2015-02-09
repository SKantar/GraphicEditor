package grapheditor.model.elements;

import grapheditor.serialization.SerializableStrokeAdapter;
import grapheditor.view.painters.ElementPainter;

import java.awt.Color;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.io.Serializable;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Apstraktna klasa koja opisuje bilo koji element dijagrama Opisuje i linkove
 * dijagrama i elemente dijagrama
 * 
 * @author Igor Z.
 * 
 */
@SuppressWarnings("serial")
public abstract class DiagramElement extends DefaultMutableTreeNode implements Serializable{
	protected Paint paint;
	protected SerializableStrokeAdapter stroke;
	protected Color strokeColor;

	protected String name;
	protected String description;
	protected Color fillColor;
	/**
	 * Instanciranje ElementPainter-a obavljaju konkretni elementi dijagrama
	 * prilikom svoje konstrukcije
	 */
	protected ElementPainter elementPainter;
	
	public void setDescription(String description) {
		this.description = description;
	}

	public Color getFillColor() {
		return fillColor;
	}

	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}
	
	public DiagramElement(DiagramElement element){
		this.stroke = element.stroke;
		this.paint = element.paint;
		this.strokeColor = element.strokeColor;
		this.name = element.name;
		this.description = element.description;
		this.elementPainter = element.elementPainter;
	}

	public DiagramElement(Stroke stroke, Paint paint, Color strokeColor) {
		setStroke(stroke);
		this.paint = paint;
		this.strokeColor = strokeColor;
	}
	
	abstract public DiagramElement clone();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ElementPainter getPainter() {
		return elementPainter;
	}

	public Paint getPaint() {
		return paint;
	}

	public void setPaint(Paint paint) {
		this.paint = paint;
	}
	
	public void setElementPainter(ElementPainter elementPainter) {
		this.elementPainter = elementPainter;
	}
	
	public ElementPainter getElementPainter() {
		return elementPainter;
	}

	public Stroke getStroke() {
		return stroke;
	}

	public void setStroke(Stroke stroke) {
		this.stroke = new SerializableStrokeAdapter(stroke);
	}

	public String toString() {
		return name;
	}

	public Color getStrokeColor() {
		return strokeColor;
	}

	public void setStrokeColor(Color strokeColor) {
		this.strokeColor = strokeColor;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public boolean isLeaf() {
		// TODO Auto-generated method stub
		return true;
	}
	
	public Rectangle getRepaintBounds(){
		return null;	
	}
}
