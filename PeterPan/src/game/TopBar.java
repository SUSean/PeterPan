package game;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import processing.core.PApplet;

public class TopBar extends JPanel implements TopBarDelegate{
	
	private JLabel label_level;
	private JLabel label_score;
	private JLabel label_highest;
	private String level;
	private String score;
	private String highestScore;
	private JLabel labelMoneyImage;
	private JLabel labelFlagImage;
	private JLabel labelHighestScoreImage;
	
	public TopBar(Rectangle bounds){
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
		
		this.labelMoneyImage = new JLabel(new ImageIcon(this.getClass().getResource("/res/starGold.png").getPath()));
		this.labelMoneyImage.setVisible(true);
		this.add(labelMoneyImage);
		this.label_score = new JLabel(score);
		this.label_score.setFont(new Font("Arial", 0, 30));
		this.label_score.setForeground(new Color(222,217,214));
		this.add(label_score);
		this.labelFlagImage = new JLabel(new ImageIcon(this.getClass().getResource("/res/Shop/redflag.png").getPath()));
		this.labelFlagImage.setVisible(true);
		this.add(labelFlagImage);
		this.label_level = new JLabel(level);
		this.label_level.setFont(new Font("Arial", 0, 30));
		this.label_level.setForeground(new Color(222,217,214));
		this.add(label_level);
		this.labelHighestScoreImage = new JLabel(new ImageIcon(this.getClass().getResource("/res/Shop/HighestScore.png").getPath()));
		this.labelHighestScoreImage.setVisible(true);
		this.add(labelHighestScoreImage);
		this.label_highest = new JLabel(highestScore);
		this.label_highest.setFont(new Font("Arial", 0, 30));
		this.label_highest.setForeground(new Color(222, 217, 214));
		this.add(label_highest);
	}
	@Override
	
	public void setLevel(int level){
		this.level = "Level: "+level;
		this.label_level.setText(this.level);
		
	}
	public void setScore(int score){
		this.score = "Score: "+score;
		this.label_score.setText(this.score);
	}
	public void setHighestScore(int highestScore){
		this.highestScore = ""+highestScore;
		this.label_highest.setText(this.highestScore);
	}
}
