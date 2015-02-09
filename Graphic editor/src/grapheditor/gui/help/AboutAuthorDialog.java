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

/**Klasa AboutAuthorView nasledjuje JFrame predstavlaj prozor
 * u kome su prikazani podaci o autoru
 * @author Kantar
 */

@SuppressWarnings("serial")
public class AboutAuthorDialog extends JFrame {
	
		private JButton button;
		public AboutAuthorDialog(){
		setTitle("About Author");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
		setSize(300, 200);
		setLocation((int)(AppCore.getInstance().getLocation().getX() + AppCore.getInstance().getWidth()/2-150), (int)(AppCore.getInstance().getLocation().getY()+AppCore.getInstance().getHeight()/2-100));
		setLayout(new GridLayout(1,2));
		JLabel left=new JLabel(new ImageIcon(getClass().getResource("../../view/images/Sladjan.png")));
		add(left,BorderLayout.WEST);
		//background.setLayout(new FlowLayout());
		JLabel right = new JLabel();
		JLabel first_name = new JLabel("   Kantar Sladjan");
		JLabel number = new JLabel("   RN 05 / 12");
		JLabel faculty = new JLabel("   Racunarski fakultet");
		JLabel email = new JLabel("   skantar12@raf.edu.rs");
		JLabel buttonlbl = new JLabel();
		buttonlbl.setLayout(new FlowLayout());
		
		right.setLayout(new GridLayout(5,1));
		add(right,BorderLayout.EAST);
		button=new JButton("OK");
		//button.setBounds(100, 100, 100, 10);
		button.addActionListener( new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
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
