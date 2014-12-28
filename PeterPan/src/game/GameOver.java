package game;

import processing.core.PApplet;
import processing.core.PImage;

public class GameOver extends PApplet{
	PImage background;
	PImage textGameOver;
	String[] topTenName;
	int[] topTenScore;
	
	public GameOver(){
		//load background
		background = new PImage();
		this.background = loadImage(this.getClass().getResource("/res/Background/background_1.jpg").getPath());
	
		//load "Game Over" text
		textGameOver = new PImage();
		this.textGameOver = loadImage(this.getClass().getResource("/res/Shop/textGameOver.png").getPath());
		
		//set topTenName 
		topTenName = new String[11];
		//unfinished
		topTenName[1] = "Peter";
		topTenName[2] = "Pan";
		
		//set topTenScore
		topTenScore = new int[11];
		//unfinished
		topTenScore[1] = 100;
		topTenScore[2] = 95;
	}
	public void setup(){
		size(500, 700);
	}
	public void draw(){
		
		//set background and "Game Over" text images
		image(this.background, 0, 0);
		image(this.textGameOver, 40, 20);
		
		//list top ten score
		for (int i=1; i < 11; i++){
			text("Rank" + i + ":" + topTenName[i] + "  Score: " + topTenScore[i], 20, 160 + 45*(i-1));
			textSize(35);
		}
	}
}
