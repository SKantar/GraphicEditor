package grapheditor.gui;

import grapheditor.app.AppCore;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

@SuppressWarnings("serial")
public class Palette extends JToolBar {

	private JToggleButton handCursor, rectangle, circle, star, triangle, link;
	private JRadioButton threeIn, fourIn, eightIn;
	private ButtonGroup group, radiogroupIn ;
	private JComboBox<Integer> inputCountBox = new JComboBox<Integer>();
	private JLabel input;

	public Palette() {
		super(JToolBar.VERTICAL);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		group = new ButtonGroup();

		handCursor = new JToggleButton(AppCore.getInstance().getActionManager().getpHandCursorAction());
		group.add(handCursor);
		add(handCursor);
		
		addSeparator();

		rectangle = new JToggleButton(AppCore.getInstance().getActionManager().getpRectangleAction());
		group.add(rectangle);
		add(rectangle);
		
		triangle = new JToggleButton(AppCore.getInstance().getActionManager().getpTriangleAction());
		group.add(triangle);
		add(triangle);

		circle = new JToggleButton(AppCore.getInstance().getActionManager().getpCircleAction());
		group.add(circle);
		add(circle);

		star = new JToggleButton(AppCore.getInstance().getActionManager().getpStarAction());
		group.add(star);
		add(star);
		addSeparator();
		
		link = new JToggleButton(AppCore.getInstance().getActionManager().getpLinkAction());
		group.add(link);
		add(link);
		
		addSeparator();
		
		setInputCountBox();
	}

	
	private void setInputCountBox(){
		radiogroupIn = new ButtonGroup();
		
		input = new JLabel("In :");
		threeIn = new JRadioButton("3");
		fourIn = new JRadioButton("4");
		eightIn = new JRadioButton("8");
		radiogroupIn.add(threeIn);
		radiogroupIn.add(fourIn);
		radiogroupIn.add(eightIn);
		
		
		this.add(input);
		this.add(threeIn);
		this.add(fourIn);
		this.add(eightIn);
		
		threeIn.setVisible(false);
		fourIn.setVisible(false);
		eightIn.setVisible(false);
		input.setVisible(false);
		
	}

	public int getNumberOfInputs() {
		if(threeIn.isSelected()) return 3;
		else if(fourIn.isSelected()) return 4;
		else return 8;
		
	}

	public void showInputCountBox() {
		input.setVisible(true);
		threeIn.setVisible(true);
		threeIn.setSelected(true);
		fourIn.setVisible(true);
		eightIn.setVisible(true);
	}
	
	public void hideInputCountBox() {
		input.setVisible(false);
		threeIn.setVisible(false);
		fourIn.setVisible(false);
		eightIn.setVisible(false);
	}

	public JComboBox<Integer> getInputCountBox() {
		return inputCountBox;
	}


	public JToggleButton getHandCursor() {
		return handCursor;
	}


	public JToggleButton getRectangle() {
		return rectangle;
	}


	public JToggleButton getCircle() {
		return circle;
	}


	public JToggleButton getStar() {
		return star;
	}


	public JToggleButton getLink() {
		return link;
	}
	
	public JToggleButton getTriangle() {
		return triangle;
	}
	
}