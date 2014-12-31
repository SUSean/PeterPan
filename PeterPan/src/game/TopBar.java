package game;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import processing.core.PApplet;

public class TopBar extends JPanel implements TopBarDelegate{
	
	private JLabel label_level;
	private JLabel label_money;
	//private int level;
	//private int money;
	private String level;
	private String money;
	private BufferedImage image;
	
	public TopBar(Rectangle bounds){
		this.setLayout(new FlowLayout(FlowLayout.LEADING));
		this.setBounds(bounds);
		this.setBackground(new Color(63,90,108));
		this.initComponents();
		this.setVisible(true);
		this.requestFocus();
	}
	
	/**
	 * A method for initializing what top bar contains.
	 */
	private void initComponents(){

		this.label_money = new JLabel(money);
		this.label_money.setFont(new Font("Arial", 0, 30));
		this.label_money.setForeground(new Color(222,217,214));
		this.add(label_money);
		this.label_level = new JLabel(level);
		this.label_level.setFont(new Font("Arial", 0, 30));
		this.label_level.setForeground(new Color(222,217,214));
		this.add(label_level);
	}
	@Override
	
	public void setLevel(int level){
		this.level = "Level: "+level;
		this.label_level.setText(this.level);
		
	}
	public void setMoney(int money){
		this.money = "Money: "+money;
		this.label_money.setText(this.money);
		
	}
}
