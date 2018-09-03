package com.mystudy.swing01;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;

public class KimProject {

	private JFrame frame;
	private JTextField idField;
	private JPasswordField pwField;
	static Member_CRUD crud;
	private JTextField idFiled_join;
	private JPasswordField pwFiled_join;
	private JPasswordField pwcfFiled_join;
	private JTextField nameFiled_join;
	private JTextField addressFiled_join;
	private JTextField phoneField_join;
	private JTable table;
	private JTextField tableNameField_admin;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					crud = new Member_CRUD();
					KimProject window = new KimProject();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public KimProject() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(0, 0, 1280, 860);
		frame.setLocationRelativeTo(null);
		
		ImagePanel welcomePanel = new ImagePanel(new ImageIcon("C:/MyStudy/10_JAVA/KingJDProject2_BitCamp1/src/image/welcome.jpg").getImage());
		welcomePanel.setSize(1280, 870);
		
		ImagePanel secondPanel = new ImagePanel(new ImageIcon("C:/MyStudy/10_JAVA/KingJDProject2_BitCamp1/src/image/secondPic.jpg").getImage());
		frame.getContentPane().add(secondPanel);
		
		String[] columnList = crud.getColumns();
		String[][] dataList = crud.getData();

		final DefaultTableModel dataModel;
		dataModel = new DefaultTableModel(dataList, columnList);
		table = new JTable(dataModel);
		table.setBounds(339, 92, 568, 367);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(311, 61, 781, 483);
		
//		table.setFillsViewportHeight(true);
		secondPanel.add(scrollPane);
		
	/*	JTable table = new JTable(data,headings);
		table.setPreferredScrollableViewportSize(new Dimension(700,600));
		//�씪�떒 �뀒�씠釉� �겕湲곕�� �겕寃� �꽕�젙
		table.setFillsViewportHeight(true); //setVisible怨� 鍮꾩듂�븳 湲곕뒫 蹂댁씠寃� �빐以섏빞吏�!!
		
		secondPanel.add(new JScrollPane(table)); //�뒪�겕濡ㅼ쓣 �궗�슜�븷 �닔 �엳寃� �빐以�
*/		
		JButton selectBtn_admin = new JButton("Select");
		selectBtn_admin.setBounds(55, 86, 105, 27);
		secondPanel.add(selectBtn_admin);
		
		selectBtn_admin.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent e) {
				String tableName = tableNameField_admin.getText();
				String[] columns = crud.getColumns(tableName);
				String[][] data = crud.getData(tableName);
//				
//				dataModel.addRow(data);
				dataModel.setDataVector(data, columns);
				
			}
		});
		
		JButton insertBtn_admin = new JButton("insert");
		insertBtn_admin.setBounds(55, 125, 105, 27);
		secondPanel.add(insertBtn_admin);
		
		JButton updateBtn_admin = new JButton("update");
		updateBtn_admin.setBounds(55, 164, 105, 27);
		secondPanel.add(updateBtn_admin);
		
		JButton deleteBtn_admin = new JButton("delete");
		deleteBtn_admin.setBounds(55, 203, 105, 27);
		secondPanel.add(deleteBtn_admin);
		
		JLabel tableNameLabel_admin = new JLabel("TableName : ");
		tableNameLabel_admin.setForeground(Color.PINK);
		tableNameLabel_admin.setFont(new Font("援대┝", Font.PLAIN, 20));
		tableNameLabel_admin.setBounds(66, 23, 146, 27);
		secondPanel.add(tableNameLabel_admin);
		
		tableNameField_admin = new JTextField();
		tableNameField_admin.setToolTipText("Enter Table Name");
		tableNameField_admin.setText("Enter Table Name");
		tableNameField_admin.setBounds(180, 22, 146, 32);
		secondPanel.add(tableNameField_admin);
		tableNameField_admin.setColumns(10);
		
		JButton clearBtn_admin = new JButton("Clear");
		clearBtn_admin.setBounds(364, 22, 105, 27);
		secondPanel.add(clearBtn_admin);
		
		JButton backBtn_admin = new JButton("back");
		backBtn_admin.setBounds(1130, 25, 105, 27);
		secondPanel.add(backBtn_admin);
		
		clearBtn_admin.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				dataModel.setRowCount(0);
			}
		});
		
	
		
		
		ImagePanel firstPanel = new ImagePanel(new ImageIcon("C:/MyStudy/10_JAVA/KingJDProject2_BitCamp1/src/image/background1.jpg").getImage());
		firstPanel.setSize(1280, 870);
		frame.getContentPane().add(firstPanel);
		firstPanel.setSize(1280, 870);
		
		JButton selectBtn_first = new JButton("Select");
		selectBtn_first.setBounds(61, 54, 105, 27);
		firstPanel.add(selectBtn_first);
		
		JButton insertBtn_first = new JButton("insert");
		insertBtn_first.setBounds(61, 108, 105, 27);
		firstPanel.add(insertBtn_first);
		
		JButton updateBtn_first = new JButton("update");
		updateBtn_first.setBounds(61, 166, 105, 27);
		firstPanel.add(updateBtn_first);
		
		JButton deleteBtn_first = new JButton("delete");
		deleteBtn_first.setBounds(61, 233, 105, 27);
		firstPanel.add(deleteBtn_first);
		
		JTextArea sqlArea_first = new JTextArea();
		sqlArea_first.setToolTipText("Query");
		sqlArea_first.setBounds(309, 12, 791, 527);
		firstPanel.add(sqlArea_first);
		
		JButton pathBtn_first = new JButton("Path");
		pathBtn_first.setBounds(1132, 45, 105, 27);
		firstPanel.add(pathBtn_first);
		
		JButton printBtn_first = new JButton("Print");
		printBtn_first.setBounds(1132, 84, 105, 27);
		firstPanel.add(printBtn_first);
		
		
	
		
		
		pathBtn_first.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				sqlArea_first.setText("");
				String str = Function.path();
				sqlArea_first.append(str);
			}
		});
		
		printBtn_first.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				String str = sqlArea_first.getText();
				if(Function.print(str)) {
					JOptionPane.showMessageDialog(null, "Print Completion");
				}
			}
		});
		
		
		selectBtn_first.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				sqlArea_first.setText("");
				String str = crud.select();
				sqlArea_first.append(str);
			}
		});
		
		
		
		
		ImagePanel joinPanel = new ImagePanel(new ImageIcon("C:/MyStudy/10_JAVA/KingJDProject2_BitCamp1/src/image/test2.jpg").getImage());
		joinPanel.setSize(1280,870); //1980x1080 �겕湲� �씠�긽 �궗吏�
		
		
		//*******************Join
		frame.getContentPane().add(joinPanel);
		
		JLabel idLabel_join = new JLabel("ID:");
		idLabel_join.setFont(new Font("援대┝", Font.PLAIN, 25));
		idLabel_join.setBounds(725, 130, 54, 47);
		joinPanel.add(idLabel_join);
		
		idFiled_join = new JTextField();
		idFiled_join.setToolTipText("Enter ID");
		idFiled_join.setText("Enter ID");
		idFiled_join.setBounds(766, 130, 177, 37);
		joinPanel.add(idFiled_join);
		idFiled_join.setColumns(10);
		
		JLabel pwLabel_join = new JLabel("pw:");
		pwLabel_join.setFont(new Font("援대┝", Font.PLAIN, 25));
		pwLabel_join.setBounds(712, 180, 54, 47);
		joinPanel.add(pwLabel_join);
		
		pwFiled_join = new JPasswordField();
		pwFiled_join.setBounds(766, 189, 177, 30);
		joinPanel.add(pwFiled_join);
		
		JLabel pwcfLabel_join = new JLabel("confirm pw:");
		pwcfLabel_join.setFont(new Font("援대┝", Font.PLAIN, 25));
		pwcfLabel_join.setBounds(634, 234, 132, 47);
		joinPanel.add(pwcfLabel_join);
		
		pwcfFiled_join = new JPasswordField();
		pwcfFiled_join.setBounds(766, 239, 177, 30);
		joinPanel.add(pwcfFiled_join);
		
		JLabel nameLabel_field = new JLabel("Name :");
		nameLabel_field.setFont(new Font("援대┝", Font.PLAIN, 25));
		nameLabel_field.setBounds(684, 281, 132, 47);
		joinPanel.add(nameLabel_field);
		
		nameFiled_join = new JTextField();
		nameFiled_join.setToolTipText("Enter Name");
		nameFiled_join.setText("Enter Name");
		nameFiled_join.setColumns(10);
		nameFiled_join.setBounds(766, 293, 177, 37);
		joinPanel.add(nameFiled_join);
		
		JLabel addressLabel_join = new JLabel("Address :");
		addressLabel_join.setFont(new Font("援대┝", Font.PLAIN, 25));
		addressLabel_join.setBounds(647, 340, 132, 47);
		joinPanel.add(addressLabel_join);
		
		addressFiled_join = new JTextField();
		addressFiled_join.setToolTipText("Enter Address");
		addressFiled_join.setText("Enter Address");
		addressFiled_join.setColumns(10);
		addressFiled_join.setBounds(766, 355, 243, 37);
		joinPanel.add(addressFiled_join);
		
		JButton joinBtn_join = new JButton("Join");
		joinBtn_join.setBounds(756, 484, 105, 27);
		joinPanel.add(joinBtn_join);
		
		JButton resetBtn_join = new JButton("Reset");
		resetBtn_join.setBounds(875, 484, 105, 27);
		joinPanel.add(resetBtn_join);
		
		JLabel phoneLabel_join = new JLabel("Phone :");
		phoneLabel_join.setFont(new Font("援대┝", Font.PLAIN, 25));
		phoneLabel_join.setBounds(668, 408, 132, 47);
		joinPanel.add(phoneLabel_join);
		
		phoneField_join = new JTextField();
		phoneField_join.setToolTipText("Enter Address");
		phoneField_join.setText("Enter Phone Number");
		phoneField_join.setColumns(10);
		phoneField_join.setBounds(766, 417, 243, 37);
		joinPanel.add(phoneField_join);
		frame.getContentPane().add(welcomePanel);
		
		joinBtn_join.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = idFiled_join.getText();
				String pw = pwFiled_join.getText();
				String pwcf = pwcfFiled_join.getText();
				String name = nameFiled_join.getText();
				String phone = phoneField_join.getText();
				String address = addressFiled_join.getText();
				if(pw.equals(pwcf)) {
					crud.insert(id, pw, name, phone, address);
					firstPanel.setVisible(false);
					joinPanel.setVisible(false);
					welcomePanel.setVisible(true);
				}else {
					JOptionPane.showMessageDialog(null, "check your password consistency");
				}
			}
		});
		resetBtn_join.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				idFiled_join.setText("");
				pwFiled_join.setText("");
				pwcfFiled_join.setText("");
				nameFiled_join.setText("");
				addressFiled_join.setText("");
				phoneField_join.setText("");
			}
		});
		backBtn_admin.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				welcomePanel.setVisible(true);
				firstPanel.setVisible(false);
				secondPanel.setVisible(false);
				joinPanel.setVisible(false);
			}
		});
		
		
		//*************************************
		joinPanel.setVisible(false);
		welcomePanel.setVisible(true);
		firstPanel.setVisible(false);
		secondPanel.setVisible(false);
		//*************************************
		JLabel idLabel_welcome = new JLabel("ID :");
		idLabel_welcome.setFont(new Font("援대┝", Font.BOLD, 30));
		idLabel_welcome.setBounds(829, 219, 76, 61);
		welcomePanel.add(idLabel_welcome);
		
		JLabel pwLabel_welcome = new JLabel("PW :");
		pwLabel_welcome.setFont(new Font("援대┝", Font.BOLD, 30));
		pwLabel_welcome.setBounds(813, 271, 76, 54);
		welcomePanel.add(pwLabel_welcome);
		
		idField = new JTextField();
		idField.setFont(new Font("援대┝", Font.PLAIN, 20));
		idField.setText("Enter ID\r\n");
		idField.setBounds(902, 233, 202, 36);
		welcomePanel.add(idField);
		idField.setColumns(10);
		
		pwField = new JPasswordField();
		pwField.setBounds(903, 276, 202, 36);
		welcomePanel.add(pwField);
		
		JButton logInBtn = new JButton("Log In");
		logInBtn.setFont(new Font("援대┝", Font.ITALIC, 20));
		logInBtn.setBounds(829, 342, 131, 36);
		welcomePanel.add(logInBtn);
		
		JButton joinBtn = new JButton("Join");
		joinBtn.setFont(new Font("援대┝", Font.ITALIC, 20));
		joinBtn.setBounds(974, 342, 131, 36);
		welcomePanel.add(joinBtn);
		
		logInBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int choice = crud.select(idField.getText(), pwField.getText());
				if(choice==2) {
					JOptionPane.showMessageDialog(null, "you have logged in sucessfully");
					firstPanel.setVisible(true);
					joinPanel.setVisible(false);
					welcomePanel.setVisible(false);
					secondPanel.setVisible(false);
				}else if(choice ==1) {
					JOptionPane.showMessageDialog(null, "you have logged in sucessfully");
					firstPanel.setVisible(false);
					joinPanel.setVisible(false);
					welcomePanel.setVisible(false);
					secondPanel.setVisible(true);
				}
				else {
					JOptionPane.showMessageDialog(null, "you have failed to log in");
				}
			}
		});
		
		joinBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				firstPanel.setVisible(false);
				joinPanel.setVisible(true);
				welcomePanel.setVisible(false);
			}
		});
		
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
	}
}
