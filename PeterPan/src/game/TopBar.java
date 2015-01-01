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
	//private int level;
	//private int money;
	private String level;
	private String score;
	private BufferedImage image;
	private JLabel label_image;
	
	public TopBar(Rectangle bounds){
//		this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));
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
		BufferedImage bi = null;
//		this.label_image = new JLabel(new ImageIcon(this.getClass().getResource("/res/Shop/money_icon.png").getPath()));
		ImageIcon ii = new ImageIcon(this.getClass().getResource("/res/Shop/money_icon.png").getPath());
		bi = new BufferedImage(50, 50, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g2d = (Graphics2D) bi.createGraphics();
		g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
		g2d.drawImage(ii.getImage(), 0,  0, 50, 50, null);
//		this.label_image.setVisible(true);
//		this.add(label_image);
		this.label_score = new JLabel(score);
		this.label_score.setFont(new Font("Arial", 0, 30));
		this.label_score.setForeground(new Color(222,217,214));
		this.add(label_score);
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
	public void setScore(int score){
		this.score = "Score: "+score;
		this.label_score.setText(this.score);
		
	}
}
