package game;

import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JLabel;

import processing.core.PApplet;
import processing.core.PImage;

public class GameTopBar extends PApplet implements TopBarDelegate{
	
	private int level;
	private int score;
	private PImage moneyIcon;
	private PImage levelFlag;
	private Rectangle bounds;
	
	public GameTopBar(Rectangle bounds){
		//load money_icon
		moneyIcon = new PImage();
		this.moneyIcon = loadImage(this.getClass().getResource("/res/Shop/money_icon.png").getPath());
		
		//load redFlag_icon
		levelFlag = new PImage();
		this.levelFlag = loadImage(this.getClass().getResource("/res/Shop/redFlag_icon.png").getPath());
		this.bounds=bounds;
	}
	public void setup(){
		size(bounds.width,bounds.height);
		frameRate(24);
	}
	public void draw(){
		background(63,90,180);
		//set the images
		image(this.moneyIcon, 10, 0, 50, 50);
		image(this.levelFlag, 290, 0, 50, 50);
		
		//set text
		fill(200, 20, 0);
		textSize(30);
		text("Score: " + this.score, 70, 40);
		text("Level: " + this.level, 350, 40);
	}
	public void setLevel(int level){
		this.level = level;
	}
	public void setScore(int money){
		this.score = money;
	}
}
