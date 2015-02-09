package grapheditor.gui;

import grapheditor.app.AppCore;
import grapheditor.model.elements.DiagramDevice;
import grapheditor.model.elements.DiagramElement;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;



@SuppressWarnings("serial")
public class ElementDialog extends JDialog{
	private final JPanel contentPanelTab1 = new JPanel();
	private final JPanel contentPanelTab2 = new JPanel();
	
	private ButtonGroup inputs = new ButtonGroup();
	private ButtonGroup outputs = new ButtonGroup();

	private JColorChooser colorChooser;
	private JTextField nameField;
	private JTextArea descriptionArea;
	private String name,description;
	private int inputNo, outputNo;
	private Color color;
	private JTabbedPane pane = new JTabbedPane();
	private boolean ok = true;
	private JRadioButton output1,output3,output4,output8;
	private JRadioButton input1,input3,input4,input8;
	private JScrollPane scroll;
	
	public ElementDialog(final DiagramElement element) {
		
		super();
		super.setTitle("Properties");
		setBounds((int)(AppCore.getInstance().getLocation().getX() + AppCore.getInstance().getWidth()/2-360), (int)(AppCore.getInstance().getLocation().getY()+AppCore.getInstance().getHeight()/2-280), 720, 560);
		getContentPane().setLayout(new BorderLayout());
		contentPanelTab1.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanelTab2.setBorder(new EmptyBorder(5, 5, 5, 5));
		pane.addTab("Name", contentPanelTab1);
		pane.addTab("Color", contentPanelTab2);
		getContentPane().add(pane, BorderLayout.CENTER);
		contentPanelTab1.setLayout(null);
		contentPanelTab2.setLayout(null);
		
		JLabel lblColor = new JLabel("Color:");
		
		contentPanelTab2.add(lblColor);
		
		if (element.getFillColor()!=null) colorChooser=new JColorChooser(element.getFillColor());
		else colorChooser=new JColorChooser();
		//colorChooser.setPreferredSize(new Dimension(100,100));
		colorChooser.setBounds(25, 27, 650, 400);
		contentPanelTab2.add(colorChooser);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(25, 29, 46, 14);
		contentPanelTab1.add(lblName);
		
		nameField = new JTextField(element.getName());
		nameField.setBounds(128, 29, 219, 41);
		contentPanelTab1.add(nameField);
		
		JLabel lblDescription = new JLabel("Description:");
		lblDescription.setBounds(25, 84, 71, 14);
		contentPanelTab1.add(lblDescription);
		
		descriptionArea = new JTextArea(element.getDescription());
		scroll = new JScrollPane(descriptionArea);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setBounds(128, 99, 218, 229);
		contentPanelTab1.add(scroll);
		
		
		if(element instanceof DiagramDevice){
		JLabel lblInputs = new JLabel("Inputs:");
		lblInputs.setBounds(396, 61, 46, 14);
		contentPanelTab1.add(lblInputs);
		

	
		input1 = new JRadioButton("1");
		input1.setBounds(454, 59, 115, 18);
		contentPanelTab1.add(input1);
		
		input3 = new JRadioButton("3");
		input3.setBounds(454, 80, 109, 23);
		contentPanelTab1.add(input3);
		
		
		input4 = new JRadioButton("4");
		input4.setBounds(454, 102, 109, 23);
		contentPanelTab1.add(input4);
		
		input8 = new JRadioButton("8");
		input8.setBounds(454, 125, 109, 23);
		contentPanelTab1.add(input8);

		inputs.add(input1);
		inputs.add(input3);
		inputs.add(input4);
		inputs.add(input8);
		

		DiagramDevice device = (DiagramDevice) element;

		if(device.getInputCount() == 1) input1.setSelected(true);
		if(device.getInputCount() == 3) input3.setSelected(true);
		if(device.getInputCount() == 4) input4.setSelected(true);
		if(device.getInputCount() == 8) input8.setSelected(true);
		
		
		JLabel lblOutputs = new JLabel("Outputs:");
		lblOutputs.setBounds(396, 201, 46, 14);
		contentPanelTab1.add(lblOutputs);
		
		output1 = new JRadioButton("1");
		output1.setBounds(454, 199, 115, 18);
		contentPanelTab1.add(output1);
		
		output3 = new JRadioButton("3");
		output3.setBounds(454, 218, 109, 23);
		contentPanelTab1.add(output3);
		
		output4 = new JRadioButton("4");
		output4.setBounds(454, 239, 109, 23);
		contentPanelTab1.add(output4);
		
		output8 = new JRadioButton("8");
		output8.setBounds(454, 261, 109, 23);
		contentPanelTab1.add(output8);
		
		outputs.add(output1);
		outputs.add(output3);
		outputs.add(output4);
		outputs.add(output8);
		
		if(device.getOutputCount() == 1) output1.setSelected(true);
		if(device.getOutputCount() == 3) output3.setSelected(true);
		if(device.getOutputCount() == 4) output4.setSelected(true);
		if(device.getOutputCount() == 8) output8.setSelected(true);
		}
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener(){

					public void actionPerformed(ActionEvent arg0) {
						name=nameField.getText();
						description=descriptionArea.getText();
						color=colorChooser.getColor();
						if(element instanceof DiagramDevice){
							if(input1.isSelected()) inputNo = 1;
							if(input3.isSelected()) inputNo = 3;
							if(input4.isSelected()) inputNo = 4;
							if(input8.isSelected()) inputNo = 8;
						
							if(output1.isSelected()) outputNo = 1;
							if(output3.isSelected()) outputNo = 3;
							if(output4.isSelected()) outputNo = 4;
							if(output8.isSelected()) outputNo = 8;
						}
						
						ok = true;
						dispose();
					}
					
				});
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				ok = false;
				dispose();
			}
			
		});
		buttonPane.add(cancelButton);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				ok = false;
				// TODO Auto-generated method stub
				super.windowClosing(arg0);
			}
		});
	
	}
	
	public String getNewName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public Color getColor() {
		return color;
	}
	
	public boolean isOk() {
		return ok;
	}
	
	public int getInputNo() {
		return inputNo;
	}
	
	public int getOutputNo() {
		return outputNo;
	}
	
}
