package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.json.JSONException;

import server.Client;


public class GameLogIn extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userName;
	private String password;
	private JPanel mainPanel;
	private JLabel gameNameLabel, nameLabel, passwordLabel, createNewAccountLabel, background;
	private JTextField nameTextField, passwordTextField;
	private JButton logInButton;
	private int ifClicked; 
	private Client client;
	private Game game;
	
	public GameLogIn(Client client){
		this.client=client;
		setBounds(0, 0, 500, 700);
		setTitle("Game Log In");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		//background
		background = new JLabel(new ImageIcon(this.getClass().getResource("/res/Background/logInBackground.jpg").getPath()));
		background.setVisible(true);
		background.setBounds(0, 0, 500, 700);
		//main panel
		mainPanel = new JPanel();
		mainPanel.setBounds(new Rectangle(500, 700));
		
		// the Game name "~Peter Pan~" label
		gameNameLabel = new JLabel("~Peter Pan~");
		gameNameLabel.setFont(new Font("JSLDataGothicC_NC", Font.BOLD, 32));
		gameNameLabel.setForeground(Color.YELLOW);
		gameNameLabel.setOpaque(false);
		gameNameLabel.setBounds(155, 20, 360, 50);
		
		//the "User Name" label
		nameLabel = new JLabel("User Name:");
		nameLabel.setFont(new Font("璅扑嚙�?", Font.BOLD, 20));
		nameLabel.setForeground(Color.yellow);
		nameLabel.setOpaque(false);
		nameLabel.setBounds(75, 80, 150, 50);
		
		//the "Password" label
		passwordLabel = new JLabel("Password:");
		passwordLabel.setFont(new Font("璅扑嚙�?", Font.BOLD, 20));
		passwordLabel.setForeground(Color.yellow);
		passwordLabel.setOpaque(false);
		passwordLabel.setBounds(75, 130, 150, 50);
		
		//the User Name textField
		nameTextField = new JTextField();
		nameTextField.setBounds(205, 85, 180, 40);
		
		//the Password textField
		passwordTextField = new JTextField();
		passwordTextField.setBounds(205, 135, 180, 40);
		
		//the logIn button
		logInButton = new JButton("Log In");
		logInButton.setBounds(200, 200, 100, 40);
		logInButton.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e){
				
				if (e.getSource() == logInButton){
					userName = nameTextField.getText();
					password = passwordTextField.getText();
					if((ifClicked % 2)==0){
						try {
							client.sendUser(userName,password);
						} catch (JSONException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					else if((ifClicked % 2)==1&&userName.length()!=0&&password.length()!=0){
						try {
							client.sendRegister(userName,password);
						} catch (JSONException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
		});
		
		//create new account label
		createNewAccountLabel = new JLabel("Create A New Account");
		createNewAccountLabel.setBounds(180, 240, 140, 50);
		createNewAccountLabel.setForeground(Color.BLUE);
		createNewAccountLabel.addMouseListener(new MouseListener(){
			
			@Override
			public void mouseClicked(MouseEvent e){
				
				//here to add create new account function
				if ((ifClicked % 2) == 0){
					logInButton.setBounds(175, 200, 150, 40);
					logInButton.setText("New Account");
					setTitle("Create New Account");
					createNewAccountLabel.setBounds(205, 240, 140, 50);
					createNewAccountLabel.setText("Back To Log In");
				}
				else {//here to switch to log in mode
					logInButton.setBounds(200, 200, 100, 40);
					logInButton.setText("Log In");
					setTitle("Game Log In");
					createNewAccountLabel.setBounds(185, 240, 140, 50);
					createNewAccountLabel.setText("Create A New Account");
				}
				ifClicked++;
				ifClicked %= 2;
			}
			@Override
			public void mouseEntered(MouseEvent e){
				createNewAccountLabel.setForeground(Color.RED);
			}
			@Override
			public void mousePressed(MouseEvent e){
			}
			@Override
			public void mouseReleased(MouseEvent e){
			}
			@Override
			public void mouseExited(MouseEvent e) {
				createNewAccountLabel.setForeground(Color.BLUE);
			}
			
		});
		
		this.add(createNewAccountLabel);
		this.add(logInButton);
		this.add(passwordTextField);
		this.add(nameTextField);
		this.add(gameNameLabel);
		this.add(nameLabel);
		this.add(passwordLabel);
		this.add(background);
		this.add(mainPanel);
		this.setVisible(true);
	}
	
	public String getUserName(){
		return userName;
	}
	public String getPassword(){
		return password;
	}
	public boolean isLogInMode(){
		if (ifClicked == 1) return false;
		else return true;
	}
	public void gameStart(){
		this.setVisible(false);
		game = new Game(this.client);
	}
	
	public void error(String error) {
		String[] option = {"OK"};
		JOptionPane.showOptionDialog(this,error,"warning",
									JOptionPane.OK_CANCEL_OPTION,
									JOptionPane.QUESTION_MESSAGE,
									null,option, option[0]);
	}
}
