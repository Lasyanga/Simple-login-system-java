package Try.system;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Dashboard {
	private JFrame dashbrd;
	private JTextArea txtarea;
	private JButton savebtn, editbtn, delbtn;
	private JLabel  logout_lbl, showlbl, searchlbl, closeserlbl, toEditlbl, closelbl;
	private JPanel panel, panel2;
	private JTextField searchtxt, edittxt;
	private String toEdit="";
	private LinkedList<String> list;
	private String perform;

	public Dashboard() {
		prepareGui();
	}
	
	private void prepareGui() {
		dashbrd = new JFrame("Dashboard");
		dashbrd.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		dashbrd.setLocation(new Point(100,50));
		dashbrd.setSize(new Dimension(500, 300));
		dashbrd.setLayout(new BorderLayout());
		dashbrd.setBackground(Color.black);
		dashbrd.setResizable(false);
		
		dashbrd.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				Logout();			
			}
		});
		
		//=========== Button ============== 
		savebtn = new JButton("Save"); 
		savebtn.setLocation(new Point(40, 70));
		savebtn.setSize(new Dimension(90, 30));
		savebtn.setBackground(Color.blue);
		savebtn.setForeground(Color.white);
		savebtn.setEnabled(false);	
		savebtn.addActionListener(new Savebtn());
		
		editbtn = new JButton("Add");
		editbtn.setLocation(new Point(140, 70));
		editbtn.setSize(new Dimension(90, 30));
		editbtn.setBackground(Color.green);
		editbtn.setForeground(Color.white);
		editbtn.addActionListener(new ToEditAdd());
		
		delbtn = new JButton("Delete"); 
		delbtn.setLocation(new Point(240, 70));
		delbtn.setSize(new Dimension(90, 30));
		delbtn.setBackground(Color.yellow);
		delbtn.setForeground(Color.red);
		delbtn.setEnabled(false);
		delbtn.addActionListener(new Delbtn());
		
		//========= JLabel ==============
		showlbl = new JLabel("Show database");
		showlbl.setLocation(new Point(10, 5));
		showlbl.setSize(new Dimension(100, 30));
		showlbl.setForeground(Color.blue);
		showlbl.addMouseListener(new showData());
		showlbl.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		logout_lbl = new JLabel("Logout");
		logout_lbl.setLocation(new Point(420, 95));
		logout_lbl.setSize(new Dimension(90, 30));
		logout_lbl.setForeground(Color.red);
		logout_lbl.addMouseListener(new Logoutlbl());
		logout_lbl.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		searchlbl = new JLabel("Search");
		searchlbl.setLocation(new Point(10, 30));
		searchlbl.setSize(new Dimension(100, 30));
		searchlbl.setForeground(Color.blue);
		searchlbl.setCursor(new Cursor(Cursor.HAND_CURSOR));
		searchlbl.addMouseListener(new showSearchtxt());
		
		closeserlbl = new JLabel();
		closeserlbl.setLocation(new Point(245, 30));
		closeserlbl.setSize(new Dimension(30, 30));
		closeserlbl.setForeground(Color.red);
		closeserlbl.setVisible(false);
		closeserlbl.setCursor(new Cursor(Cursor.HAND_CURSOR));
		closeserlbl.addMouseListener(new searchClose());
		
		toEditlbl = new JLabel();
		toEditlbl.setLocation(new Point(200, 5));
		toEditlbl.setSize(new Dimension(250, 30));
		toEditlbl.setForeground(Color.blue);
		toEditlbl.setVisible(false);
		
		closelbl = new JLabel("X");
		closelbl.setLocation(new Point(450, 5));
		closelbl.setSize(new Dimension(30, 30));
		closelbl.setForeground(Color.red);
		closelbl.setCursor(new Cursor(Cursor.HAND_CURSOR));
		closelbl.setVisible(false);
		closelbl.addMouseListener(new closeAdd_Edit());
		
		//=========== JTextArea ==========
		txtarea = new JTextArea();
		txtarea.setLocation(new Point(20,30));
		txtarea.setSize(new Dimension(440, 100));
		txtarea.setEnabled(true);
		txtarea.setForeground(Color.BLACK);
				
		//========== JTextField =========
		searchtxt = new JTextField();
		searchtxt.setLocation(new Point(80, 30));
		searchtxt.setSize(new Dimension(160, 30));
		searchtxt.setForeground(Color.blue);
		searchtxt.setVisible(false);
		searchtxt.addKeyListener(new searchData());
		
		edittxt = new JTextField();
		edittxt.setLocation(new Point(200, 25));
		edittxt.setSize(new Dimension(250, 30));
		edittxt.setVisible(false);
				
		//========== JPanel ============
		panel = new JPanel();
		panel.setBackground(Color.gray);
		panel.setLayout(new GridLayout(2,1));
		
		panel2 = new JPanel();
		panel2.setLayout(null);
		panel2.setBackground(Color.gray);
		
		//panel2 adding components		
		panel2.add(logout_lbl);
		panel2.add(showlbl);
		panel2.add(savebtn);
		panel2.add(editbtn);
		panel2.add(delbtn);
		panel2.add(searchtxt);
		panel2.add(searchlbl);
		panel2.add(closeserlbl);
		panel2.add(toEditlbl);
		panel2.add(edittxt);
		panel2.add(closelbl);
		
		//panel adding components
		panel.add(txtarea);
		panel.add(new JScrollPane(txtarea));
		panel.add(panel2);
		
		//frame components
		dashbrd.add(panel,BorderLayout.CENTER);
		dashbrd.setVisible(true);
	}
	
	//store data to linked list
	private void copytoList() {
		try {
			list = new LinkedList<String>();
			String str;
			
			BufferedReader read = new BufferedReader(new FileReader("./Database"));
			while((str = read.readLine()) != null) {
				list.add(str.replace("\t", "\\t"));
			}
			read.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//delete
	private void del() {
		try {
			int count = 0;
			String toRemove = edittxt.getText();
			list.remove(toRemove);
			System.out.print(list.size());
			
			BufferedWriter write = new BufferedWriter(new FileWriter("./Database"));
			
			while(count != list.size()) {
				write.write(list.get(count).toString().replace("\\t", "\t")+"\n");
				count++;
			}
			write.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// Logout process	
	private void Logout() {
		int option = JOptionPane.showConfirmDialog(dashbrd, 
				"Do you want to logout", "Ask?", JOptionPane.YES_NO_OPTION);
		if(option == JOptionPane.YES_OPTION) {
			dashbrd.setVisible(false);
			new Mainfrm();
		}
	}
	
	// Show Database file in TextArea
	private void showDat() {
		try {
			txtarea.setText(null);
			String str;
			BufferedReader read = new BufferedReader(new FileReader("./Database"));
			while((str = read.readLine()) != null) {
				txtarea.append(str +"\n");
			}
			read.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//Search Process
	private void Search() {
		try {
			String find = searchtxt.getText();
			String data;
			boolean flag = false;
			
			BufferedReader read = new BufferedReader(new FileReader("./Database"));
			
			if(!find.isEmpty()) {
				while((data = read.readLine()) != null) {
					if(data.contains(find)) {
						txtarea.setText("");
						txtarea.append("Found Match:\n"+data);
						toEdit = data;
						flag = true;
						editbtn.setText("Edit");
						editbtn.setEnabled(true);
					}
				}
			}
			
			if(flag == false) {
				txtarea.setText("");
				txtarea.append("Found Match:\nNo Data Found...");
			}
			
			read.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//Close JLabel process
	private void Closelbl() {
		toEdit="";
		toEditlbl.setVisible(false);
		edittxt.setVisible(false);
		closelbl.setVisible(false);
		searchtxt.setText("");
		searchtxt.setVisible(false);
		closeserlbl.setVisible(false);
		editbtn.setEnabled(true);
		editbtn.setText("Add");
		savebtn.setEnabled(false);
		delbtn.setEnabled(false);
	}
	
	//Edit/Add JButton process
	private void editAdd() {
		perform = editbtn.getText();
		toEditlbl.setText("To "+perform+" ex: Username\\tPassword");
		toEditlbl.setVisible(true);
		closelbl.setVisible(true);
		edittxt.setVisible(true);
		edittxt.setText(toEdit.replace("\t", "\\t"));
		searchtxt.setText("");
		searchtxt.setVisible(false);
		closeserlbl.setVisible(false);
		editbtn.setEnabled(false);
		savebtn.setEnabled(true);
		
		if(perform.equalsIgnoreCase("edit")) {
			delbtn.setEnabled(true);
		}
		
	}
	
	//Delete button
	private class Delbtn implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			del();
			txtarea.setText("");
			showDat();
			Closelbl();
			}
		}
	
	//Saving data
	private class Savebtn implements ActionListener{
		public void actionPerformed(ActionEvent actionEvent) {
			copytoList();
			String toSave = edittxt.getText();
			int count = 0;
			perform = editbtn.getText();
			
			try {
				if(toSave.contains("\\t")) {
					if(perform.equalsIgnoreCase("add")) {
						BufferedWriter write = new BufferedWriter(new FileWriter("./Database",true));
						write.write(toSave.replace("\\t", "\t")+"\n");
						write.close();
						txtarea.setText("");
						showDat();
						Closelbl();
						write.close();
					}
					
					if(perform.equalsIgnoreCase("edit")) {
						
						try {
							while(count != list.size()) {
								if(list.get(count).toString().equals(toEdit.replace("\t", "\\t"))) {
									list.remove(count);
									list.add(toSave);
									}
								count++;
								}
							
							count = 0;
							
							BufferedWriter write = new BufferedWriter(new FileWriter("./Database"));
							
							while(count != list.size()) {
								write.write(list.get(count).toString().replace("\\t", "\t")+"\n");
								count++;
								}
							write.close();
							
							showDat();
							Closelbl();
							
						}catch(Exception ex) {
							ex.printStackTrace();
						}
					}
						
					}else {
						JOptionPane.showMessageDialog(dashbrd,"Please follow the Format.");
						}
				}catch(Exception e) {
					e.printStackTrace();
					}
			}
		}
	
	//JTextField Search text function
	private class searchData implements KeyListener{

		@Override
		public void keyTyped(KeyEvent e) {}

		@Override
		public void keyPressed(KeyEvent e) {}

		@Override
		public void keyReleased(KeyEvent e) {
			Search();
			
		}
		
	}
	
	//JLabel Close edit/add
	private class closeAdd_Edit implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			Closelbl();			
		}

		@Override
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseReleased(MouseEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}
		
	}
	
	//JLabel Close Search
	private class searchClose implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			Closelbl();
		}

		@Override
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseReleased(MouseEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {
			closeserlbl.setText("X");
		}

		@Override
		public void mouseExited(MouseEvent e) {
			closeserlbl.setText("");
		}
		
	}
	
	//To Edit ActionPerform
	private class ToEditAdd implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			perform = editbtn.getText();
			
			if(perform.equalsIgnoreCase("edit")) {
				copytoList();
				editAdd();
			}
			
			if(perform.equalsIgnoreCase("add")) {
				editAdd();
			}
		}
	}
	
	//JLabel Show data function
	private class showData implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			txtarea.setText("");
			showDat();
			
		}

		@Override
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseReleased(MouseEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}
		
	}
	
	//JLabel Search function
	private class showSearchtxt implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			searchtxt.setVisible(true);
			closeserlbl.setVisible(true);
			toEditlbl.setVisible(false);
			edittxt.setVisible(false);
			closelbl.setVisible(false);
			
		}

		@Override
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseReleased(MouseEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}
		
	}
	
	//JLabel Logout
 	private class Logoutlbl implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			Logout();
			
		}

		@Override
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseReleased(MouseEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}
		
	}

}
