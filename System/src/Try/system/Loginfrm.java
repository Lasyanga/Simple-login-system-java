package Try.system;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Loginfrm {
	private JFrame logfrm;
	private JButton logbtn, backbtn;
	private JPasswordField passtxt;
	private JTextField usertxt;
	private JLabel usrlbl, passlbl, erruserlbl, errpasslbl;

	public Loginfrm() {
		prepareLoginfrm();
	}
	
	private void prepareLoginfrm() {
		logfrm = new JFrame("Login Form");
		logfrm.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		logfrm.setLocation(new Point(100, 50));
		logfrm.setSize(new Dimension(500, 300));
		logfrm.setLayout(null);
		logfrm.setBackground(Color.gray);
		logfrm.setResizable(false);
		
		//this responsible in closing the window
		logfrm.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				int option = JOptionPane.showConfirmDialog(logfrm, "Do you want to close this Login form?", 
						"Closing Login Form", JOptionPane.YES_NO_OPTION);
				
				if (option == JOptionPane.YES_OPTION) {
					logfrm.setVisible(false);
					new Mainfrm();
				}
			}
		});
		
		usrlbl = new JLabel("Username:",JLabel.CENTER);
		usrlbl.setLocation(new Point(50, 60));
		usrlbl.setSize(new Dimension(100, 30));
		usrlbl.setBackground(Color.black);
		usrlbl.setForeground(Color.blue);
		
		passlbl = new JLabel("Password", JLabel.CENTER);
		passlbl.setLocation(new Point(50, 110));
		passlbl.setSize(new Dimension(100, 30));
		passlbl.setForeground(Color.blue);
		
		erruserlbl = new JLabel("X");
		erruserlbl.setLocation(new Point(360, 60));
		erruserlbl.setSize(new Dimension(90, 30));
		erruserlbl.setForeground(Color.red);
		erruserlbl.setVisible(false);
		
		errpasslbl = new JLabel("X");
		errpasslbl.setLocation(new Point(360, 110));
		errpasslbl.setSize(new Dimension(90, 30));;
		errpasslbl.setForeground(Color.red);
		errpasslbl.setVisible(false);
		
		usertxt = new JTextField();
		usertxt.setLocation(new Point(150, 60));
		usertxt.setSize(new Dimension(200, 30));
		usertxt.addKeyListener(new usertxtKeyListener());
		
		passtxt = new JPasswordField();
		passtxt.setLocation(new Point(150,110));
		passtxt.setSize(new Dimension(200, 30));
		passtxt.addKeyListener(new passtxtKeyListener());
		
		backbtn = new JButton("<<Back");
		backbtn.setLocation(new Point(40, 190));
		backbtn.setSize(new Dimension(150, 30));
		backbtn.setBackground(Color.green);
		backbtn.setForeground(Color.white);
		
		backbtn.addActionListener(new ActionListener() {//Back button function returning in Mainfrm()

			@Override
			public void actionPerformed(ActionEvent e) {
				logfrm.setVisible(false);
				new Mainfrm();
			}
			
		});
		
		logbtn = new JButton("Login");
		logbtn.setLocation(new Point(280, 190));
		logbtn.setSize(new Dimension(150, 30));
		logbtn.setBackground(Color.blue);
		logbtn.setForeground(Color.white);
		logbtn.addActionListener(new logbtnActionListener());
		
		//frame adding components
		logfrm.add(usrlbl);
		logfrm.add(passlbl);
		logfrm.add(usertxt);
		logfrm.add(passtxt);
		logfrm.add(backbtn);
		logfrm.add(logbtn);
		logfrm.add(erruserlbl);
		logfrm.add(errpasslbl);
		logfrm.setVisible(true);
	}
	
	//validation of Text fields
	private void ProcessChk() {
		String user = usertxt.getText();
		String pass = String.valueOf(passtxt.getPassword());
		
		if(user.isBlank()){
			erruserlbl.setVisible(true);
		}else if(!user.isEmpty()){
			erruserlbl.setVisible(false);
			
			if(pass.isBlank()) {
				errpasslbl.setVisible(true);
			}else if(!pass.isEmpty()) {
				errpasslbl.setVisible(false);
				fileReader();
				
			}
		}
	}
	
	//Login button function
	private class logbtnActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			try {
				ProcessChk();
			}catch(Exception ioe) {
				ioe.printStackTrace();
			}
		}
	}
	
	//Username text field KeyListener 
	private class usertxtKeyListener implements KeyListener{

		@Override
		public void keyTyped(KeyEvent e) {}

		@Override
		//it activate when you hit the Enter
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				try {
					ProcessChk();
				}catch(Exception ioe) {
					ioe.printStackTrace();
				}
			}
			
		}

		@Override
		//hide "X" when field is not Empty
		public void keyReleased(KeyEvent e) {
			String user = usertxt.getText();
			
			if(!user.isEmpty()) {
				erruserlbl.setVisible(false);
			}
			
		}
		
	}
	
	//Password text field KeyListener
	private class passtxtKeyListener implements KeyListener{

		@Override
		public void keyTyped(KeyEvent e) {
			}

		@Override
		//it activate when you hit the Enter
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				try {
					ProcessChk();
				}catch(Exception ioe) {
					ioe.printStackTrace();
				}
			}
			
		}

		@Override
		//hide "X" when field is not Empty
		public void keyReleased(KeyEvent e) {
			String pass = usertxt.getText();
			
			if(!pass.isEmpty()) {
				erruserlbl.setVisible(false);
			}
			
		}
		
	}
	
	//File Reader 
	private void fileReader() {
		try {
			String user = usertxt.getText();
			String pass = String.valueOf(passtxt.getPassword());
			
			String find = user+"\t"+pass;
			
			BufferedReader read = new BufferedReader(new FileReader("./Database"));//read the ./Database.file
			
			String str;
			boolean flag = false;
			
			//Confirming if data input is Match in ./Database.file
			while((str = read.readLine()) != null) {
				if(str.matches(find)) {
					System.out.println(str);
					JOptionPane.showMessageDialog(logfrm, "match");
					flag = true;
					logfrm.setVisible(false);
					new Dashboard();
					break;
				}
			}
			
			if(flag == false) {
				JOptionPane.showMessageDialog(logfrm, "Username/Password is wrong!\nPlease check.");
			}
			
			read.close();
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
