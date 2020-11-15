package Try.system;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;


public class Mainfrm {
	private JFrame frm;
	private JButton logbtn, register, canbtn;
	
	Mainfrm(){
		prepareGUI();
	}
	
	private void prepareGUI() {
		frm = new JFrame("Welcome to System");
		frm.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frm.setLocation(new Point(100, 50));
		frm.setSize(new Dimension(500, 300));
		frm.setLayout(null);
		frm.setBackground(Color.gray);
		frm.setResizable(false);
		frm.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				int option = JOptionPane.showConfirmDialog(frm,"Do you want to close this?", "Exit?", JOptionPane.YES_NO_OPTION);
				
				if(option == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
				
			}
		}
	);
		
		
		
		logbtn = new JButton("Login");
		logbtn.setLocation(new Point(150, 50));
		logbtn.setSize(new Dimension(200, 30));
		logbtn.setForeground(Color.white);
		logbtn.setFocusable(false);
		logbtn.setBackground(Color.blue);
		
		//Open Login Form
		logbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frm.setVisible(false);
				new Loginfrm();
			}
			
		});
		
		register = new JButton("Register");
		register.setLocation(new Point(150, 100));
		register.setSize(new Dimension(200, 30));
		register.setForeground(Color.white);
		register.setBackground(Color.green);
		register.setFocusable(false);
		
		//Open Registration form
		register.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					File file = new File("./Database");
					if(!file.exists()) {
						BufferedWriter write = new BufferedWriter(new FileWriter("./Database",true));
						write.close();
					}
				}catch(Exception ex) {
					ex.printStackTrace();
				}
				frm.setVisible(false);
				new Registerfrm();
				
			}
			
		});
		
		canbtn = new JButton("Cancel");
		canbtn.setLocation(new Point(150, 150));
		canbtn.setSize(new Dimension(200, 30));
		canbtn.setBackground(Color.yellow);
		canbtn.setForeground(Color.red);
		canbtn.setFocusable(false);
		
		//Closing Mainfrm()
		canbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int option = JOptionPane.showConfirmDialog(frm, "Do you want to cancel?", "Ask?", JOptionPane.YES_NO_OPTION);
				
				if(option == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
				
			}
			
		});
		

		frm.add(logbtn);
		frm.add(canbtn);
		frm.add(register);
		frm.setVisible(true);
		
		
	}
	
	
}
