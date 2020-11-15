package Try.system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class Registerfrm {
	private JFrame regfrm;
	private JButton regbtn, backbtn;
	private JPasswordField passtxt, cpasstxt;
	private JTextField usertxt;
	private JLabel usrlbl, passlbl, confrmlbl, erruserlbl, errpasslbl, errconlbl,  msgconlbl2;


	public Registerfrm() {
		prepareRegistrationfrm();
	}
	
	private void prepareRegistrationfrm() {
		regfrm = new JFrame("Registration Form");
		regfrm.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		regfrm.setLocation(new Point(100,50));
		regfrm.setSize(new Dimension(500, 300));
		regfrm.setLayout(null);
		regfrm.setBackground(Color.gray);
		regfrm.setResizable(false);
		
		//Confirmation in closing the Form
		regfrm.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				int option = JOptionPane.showConfirmDialog(regfrm, "Do you want to cancel your registration?",
						"Closing Registration Form", JOptionPane.YES_NO_OPTION);
				
				if(option == JOptionPane.YES_OPTION) {
					regfrm.setVisible(false);
					new Mainfrm();
				}
			}
		});
		
		
		
		usrlbl = new JLabel("Username:",JLabel.CENTER);
		usrlbl.setLocation(new Point(40, 50));
		usrlbl.setSize(new Dimension(100, 30));
		usrlbl.setBackground(Color.black);
		usrlbl.setForeground(Color.blue);
		
		passlbl = new JLabel("Password:", JLabel.CENTER);
		passlbl.setLocation(new Point(40, 100));
		passlbl.setSize(new Dimension(100, 30));
		passlbl.setForeground(Color.blue);
		
		confrmlbl = new JLabel("Comfirm Password:");
		confrmlbl.setLocation(new Point(40, 150));
		confrmlbl.setSize(new Dimension(150,30));
		confrmlbl.setForeground(Color.blue);
		
		usertxt = new JTextField();
		usertxt.setLocation(new Point(160, 50));
		usertxt.setSize(new Dimension(200, 30));
		usertxt.addKeyListener(new usertxtKeyListener());
		
		
		erruserlbl = new JLabel("X");
		erruserlbl.setLocation(new Point(360, 50));
		erruserlbl.setSize(new Dimension(90, 30));
		erruserlbl.setForeground(Color.red);
		erruserlbl.setVisible(false);
		
		
		passtxt = new JPasswordField();
		passtxt.setLocation(new Point(160,100));
		passtxt.setSize(new Dimension(200, 30));
		passtxt.addKeyListener(new passtxtKeyListener());
		
		errpasslbl = new JLabel("X");
		errpasslbl.setLocation(new Point(360, 100));
		errpasslbl.setSize(new Dimension(90, 30));
		errpasslbl.setForeground(Color.red);
		errpasslbl.setVisible(false);
		
		cpasstxt = new JPasswordField();
		cpasstxt.setLocation(new Point(160, 150));
		cpasstxt.setSize(new Dimension(200, 30));
		cpasstxt.addKeyListener(new confirmKeyListener());
		
		errconlbl = new JLabel("Not Match!");
		errconlbl.setLocation(new Point(360, 150));
		errconlbl.setSize(new Dimension(90, 30));
		errconlbl.setForeground(Color.red);
		errconlbl.setVisible(false);
		
		msgconlbl2 = new JLabel("Match");
		msgconlbl2.setLocation(new Point(360, 150));
		msgconlbl2.setSize(new Dimension(90, 30));
		msgconlbl2.setForeground(Color.green);
		msgconlbl2.setVisible(false);
				
		
		backbtn = new JButton("<<Back");
		backbtn.setLocation(new Point(40,200));
		backbtn.setSize(new Dimension(150, 30));
		backbtn.setForeground(Color.white);
		backbtn.setBackground(Color.green);
		backbtn.addActionListener(new Backbtn());
		
		regbtn = new JButton("Register");
		regbtn.setLocation(new Point(280, 200));
		regbtn.setSize(new Dimension(150, 30));
		regbtn.setForeground(Color.white);
		regbtn.setBackground(Color.blue);
		regbtn.addActionListener(new Registerbtn());

		//Adding JFrame components
		regfrm.add(usrlbl);
		regfrm.add(passlbl);
		regfrm.add(confrmlbl);
		
		regfrm.add(usertxt);
		regfrm.add(passtxt);
		regfrm.add(cpasstxt);
		
		regfrm.add(backbtn);
		regfrm.add(regbtn);
		
		regfrm.add(erruserlbl);
		regfrm.add(errpasslbl);
		regfrm.add(errconlbl);
		regfrm.add(msgconlbl2);
		
		regfrm.setVisible(true);
		
		
	}
	//Validation Process
	private void Validation_checker() {
		String user = usertxt.getText();
		String pass = String.valueOf(passtxt.getPassword());
		String cpass = String.valueOf(cpasstxt.getPassword());
		
		if(user.isBlank()) {
			erruserlbl.setVisible(true);
		}else if(!user.isEmpty()){
			erruserlbl.setVisible(false);
			
			if(pass.isBlank()) {
				errpasslbl.setVisible(true);
			}else if(!pass.isEmpty()) {
				errpasslbl.setVisible(false);
				
				if(cpass.isBlank()) {
					errconlbl.setVisible(true);
				}else if(!cpass.isEmpty()) {
					if(pass.equals(cpass)) {
						msgconlbl2.setVisible(true);
						errconlbl.setVisible(false);
						
						Write();							
						
					}else {
						msgconlbl2.setVisible(false);
						errconlbl.setVisible(true);
					}
				}
			}
		}
	}
	
	//Returning into Mainfrm()
	private class Backbtn implements ActionListener {

	@Override
		public void actionPerformed(ActionEvent e) {
			regfrm.setVisible(false);
			new Mainfrm();
		
		}
	}
	
	private class Registerbtn implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			Validation_checker();
		}
	}
	
	private class confirmKeyListener implements KeyListener{

		@Override
		public void keyTyped(KeyEvent e) {
		}

		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				Validation_checker();
			}
			
		}

		@Override
		//validate keyreleased to Confirmed the password is match or not
		public void keyReleased(KeyEvent e) {
			String pass = String.valueOf(passtxt.getPassword());
			String cpass = String.valueOf(cpasstxt.getPassword());
			
			
			if (cpass.equals(pass)) {
				errconlbl.setVisible(false);
				msgconlbl2.setVisible(true);
			}else {
				errconlbl.setVisible(true);
				msgconlbl2.setVisible(false);
			}
		}
	}
	
	private class usertxtKeyListener implements KeyListener{

		@Override
		public void keyTyped(KeyEvent e) {}

		@Override
		//Validate when hit enter
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				Validation_checker();
			}
			
		}


		@Override
		public void keyReleased(KeyEvent e) {
			String user = usertxt.getText();
			
			if(!user.isEmpty()) {
				erruserlbl.setVisible(false);
			}
			
		}
		
	}
	
	private class passtxtKeyListener implements KeyListener{

		@Override
		public void keyTyped(KeyEvent e) {
			}

		@Override
		//Validate when hit enter
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				Validation_checker();
			}
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			String pass = String.valueOf(passtxt.getPassword());
			
			if(!pass.isEmpty()) {
				errpasslbl.setVisible(false);
			}
			
		}
		
	}
	
	//Creating ./Database.file and write Username\tPassword
	private void Write() {
		try {
			String user = usertxt.getText();
			String pass = String.valueOf(passtxt.getPassword());
			String find = user+"\t"+pass;
			
			BufferedReader read = new BufferedReader(new FileReader("./Database"));
			
			String str;
			boolean flag = false;
			//Validating if the Username and Password existed or not
			while((str = read.readLine()) != null) {
				if(str.matches(find)) {
					flag = true;
					break;
				}
			}
			read.close();
			
			if(flag == false) {//if Username and Password is existed
				 BufferedWriter out = new BufferedWriter(new FileWriter("Database",true));
				 out.write(user+"\t"+pass+"\n");
				 out.close();				
				 JOptionPane.showMessageDialog(regfrm, "Your account is successfully created...");
				 regfrm.setVisible(false);
				 new Loginfrm();
			}else {
				JOptionPane.showMessageDialog(regfrm, "Your account is already existed.");
			}
			
	        
	      }
	      catch (IOException e) {
	    	  e.printStackTrace();
	      }
	}
	
	
}