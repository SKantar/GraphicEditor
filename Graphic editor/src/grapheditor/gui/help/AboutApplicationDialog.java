package grapheditor.gui.help;

import grapheditor.app.AppCore;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**Klasa AboutApplicationView nasledjuje JFrame predstavlaj prozor
 * u kome su prikazani podaci o aplikaciji
 * @author Kantar
 */

@SuppressWarnings("serial")
public class AboutApplicationDialog extends JFrame {

	private JButton button;

	public AboutApplicationDialog() {
		setTitle("About GED");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
		setSize(300, 200);
		setLocation((int) (AppCore.getInstance().getLocation().getX()
				+ AppCore.getInstance().getWidth() / 2 - 150), (int) (AppCore
				.getInstance().getLocation().getY()
				+ AppCore.getInstance().getHeight() / 2 - 100));
		setLayout(new GridLayout(1, 2));
		JLabel left = new JLabel(new ImageIcon(getClass().getResource("../../view/images/icon.png")));
		add(left, BorderLayout.WEST);
		// background.setLayout(new FlowLayout());
		JLabel right = new JLabel();
		JLabel first_name = new JLabel("   Graphic editor");
		JLabel number = new JLabel("   by Slađan Kantar");
		JLabel faculty = new JLabel("  Version 2.0 ");
		JLabel email = new JLabel("   ");
		JLabel buttonlbl = new JLabel();
		buttonlbl.setLayout(new FlowLayout());

		right.setLayout(new GridLayout(5, 1));
		add(right, BorderLayout.EAST);
		button = new JButton("OK");
		// button.setBounds(100, 100, 100, 10);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		right.add(first_name);
		right.add(number);
		right.add(faculty);
		right.add(email);
		buttonlbl.add(button);
		right.add(buttonlbl);
	}
}
